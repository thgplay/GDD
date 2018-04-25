package discord.gdd.utils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;

/*
Api feito pelo @author Kristian, nao conseguir achar um local original
objetivo de ela estar aqui e bem simples, ela tem algumas coisas bem ulteis, vou traduzir ela e colocar mais coisas.
 */
public final class Reflection {
	public interface ConstructorInvoker {
		public Object invoke(Object... arguments);
	}

	public interface MethodInvoker {
		public Object invoke(Object target, Object... arguments);
	}

	public interface FieldAccessor<T> {
		public T get(Object target);

		public void set(Object target, Object value);

		public boolean hasField(Object target);
	}

	// Deduce the net.minecraft.server.v* package
	private static String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
	private static String NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
	private static String VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
	private static String BUNGEE = "net.md_5.bungee";

	private static Pattern MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");

	private Reflection() {
		// Seal class
	}
	public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType) {
		return getField(target, name, fieldType, 0);
	}

	public static <T> FieldAccessor<T> getField(String className, String name, Class<T> fieldType) {
		return getField(getClass(className), name, fieldType, 0);
	}

	public static <T> FieldAccessor<T> getField(Class<?> target, Class<T> fieldType, int index) {
		return getField(target, null, fieldType, index);
	}

	public static <T> FieldAccessor<T> getField(String className, Class<T> fieldType, int index) {
		return getField(getClass(className), fieldType, index);
	}

	private static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType, int index) {
		for (final Field field : target.getDeclaredFields()) {
			if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType())
					&& index-- <= 0) {
				field.setAccessible(true);

				// A function for retrieving a specific field value
				return new FieldAccessor<T>() {

					@Override
					@SuppressWarnings("unchecked")
					public T get(Object target) {
						try {
							return (T) field.get(target);
						} catch (IllegalAccessException e) {
							throw new RuntimeException("Cannot access reflection.", e);
						}
					}

					@Override
					public void set(Object target, Object value) {
						try {
							field.set(target, value);
						} catch (IllegalAccessException e) {
							throw new RuntimeException("Cannot access reflection.", e);
						}
					}

					@Override
					public boolean hasField(Object target) {
						return field.getDeclaringClass().isAssignableFrom(target.getClass());
					}
				};
			}
		}

		if (target.getSuperclass() != null)
			return getField(target.getSuperclass(), name, fieldType, index);

		throw new IllegalArgumentException("Cannot find field with type " + fieldType);
	}

	public static MethodInvoker getMethod(String className, String methodName, Class<?>... params) {
		return getTypedMethod(getClass(className), methodName, null, params);
	}

	public static MethodInvoker getMethod(Class<?> clazz, String methodName, Class<?>... params) {
		return getTypedMethod(clazz, methodName, null, params);
	}

	public static MethodInvoker getTypedMethod(Class<?> clazz, String methodName, Class<?> returnType,
			Class<?>... params) {
		for (final Method method : clazz.getDeclaredMethods()) {
			if ((methodName == null || method.getName().equals(methodName))
					&& (returnType == null || method.getReturnType().equals(returnType))
					&& Arrays.equals(method.getParameterTypes(), params)) {
				method.setAccessible(true);

				return new MethodInvoker() {

					@Override
					public Object invoke(Object target, Object... arguments) {
						try {
							return method.invoke(target, arguments);
						} catch (Exception e) {
							throw new RuntimeException("Cannot invoke method " + method, e);
						}
					}

				};
			}
		}

		if (clazz.getSuperclass() != null)
			return getMethod(clazz.getSuperclass(), methodName, params);

		throw new IllegalStateException(
				String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
	}

	public static ConstructorInvoker getConstructor(String className, Class<?>... params) {
		return getConstructor(getClass(className), params);
	}

	public static ConstructorInvoker getConstructor(Class<?> clazz, Class<?>... params) {
		for (final Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			if (Arrays.equals(constructor.getParameterTypes(), params)) {
				constructor.setAccessible(true);

				return new ConstructorInvoker() {

					@Override
					public Object invoke(Object... arguments) {
						try {
							return constructor.newInstance(arguments);
						} catch (Exception e) {
							throw new RuntimeException("Cannot invoke constructor " + constructor, e);
						}
					}

				};
			}
		}

		throw new IllegalStateException(
				String.format("Unable to find constructor for %s (%s).", clazz, Arrays.asList(params)));
	}

	public static Class<Object> getUntypedClass(String lookupName) {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Class<Object> clazz = (Class) getClass(lookupName);
		return clazz;
	}

	public static Class<?> getClass(String lookupName) {
		return getCanonicalClass(expandVariables(lookupName));
	}

	public static Class<?> getMinecraftClass(String name) {
		return getCanonicalClass(NMS_PREFIX + "." + name);
	}

	public static Class<?> getCraftBukkitClass(String name) {
		return getCanonicalClass(OBC_PREFIX + "." + name);
	}

	public static Class<?> getBungeeClass(String name) {
		return getCanonicalClass(name);
	}

	private static Class<?> getCanonicalClass(String canonicalName) {
		try {
			return Class.forName(canonicalName);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Cannot find " + canonicalName, e);
		}
	}
	
	public static Iterator list(ClassLoader CL)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class CL_class = CL.getClass();
		while (CL_class != ClassLoader.class) {
			CL_class = CL_class.getSuperclass();
		}
		Field ClassLoader_classes_field = CL_class.getDeclaredField("classes");
		ClassLoader_classes_field.setAccessible(true);
		Vector classes = (Vector) ClassLoader_classes_field.get(CL);
		return classes.iterator();
	}

	/*
	Reflection teste do Wiljafor1
	 */
	/*public static ArrayList<Class> getPluguinAllClass() {
		Iterator inter;
		ArrayList<Class> classes = new ArrayList<Class>();
		try {
			Plugin[] pl = Bukkit.getPluginManager().getPlugins();
			for(int size = 0; size < Bukkit.getPluginManager().getPlugins().length; size++) {
				Plugin p = pl[size];
				if(!p.getName().equals("ProtocolLib")) {
					inter = list(p.getClass().getClassLoader());
					while (inter.hasNext()) {
						String classe = "" + inter.next();
						if(classe.contains("class ")) {
							classe = classe.replaceAll("class ", "");
							classes.add(getCanonicalClass(classe) != null ? getCanonicalClass(classe) : null);
						}
					}
				}
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return classes;
	}*/
	/*
	public static ArrayList<Class> getAllClass() {
		Iterator inter;
		ArrayList<Class> classes = new ArrayList<Class>();
		try {
			inter = list(Bukkit.getServer().getClass().getClassLoader());
			while (inter.hasNext()) {
				String classe = "" + inter.next();
				if(classe.contains("class ")) {
					classe = classe.replaceAll("class ", "");
					classes.add(getCanonicalClass(classe) != null ? getCanonicalClass(classe) : null);
				}
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return classes;
	}*/

	private static String expandVariables(String name) {
		StringBuffer output = new StringBuffer();
		Matcher matcher = MATCH_VARIABLE.matcher(name);

		while (matcher.find()) {
			String variable = matcher.group(1);
			String replacement = "";

			// Expand all detected variables
			if ("nms".equalsIgnoreCase(variable))
				replacement = NMS_PREFIX;
			else if ("obc".equalsIgnoreCase(variable))
				replacement = OBC_PREFIX;
			else if ("version".equalsIgnoreCase(variable))
				replacement = VERSION;
			else
				throw new IllegalArgumentException("Unknown variable: " + variable);

			// Assume the expanded variables are all packages, and append a dot
			if (replacement.length() > 0 && matcher.end() < name.length() && name.charAt(matcher.end()) != '.')
				replacement += ".";
			matcher.appendReplacement(output, Matcher.quoteReplacement(replacement));
		}

		matcher.appendTail(output);
		return output.toString();
	}

	/*
	 * Parte inserida pelo Wiljafor1
	 */
	public static List<Class<?>> getListeners(File file, String packagee) {

		return getPackages(file, packagee).stream().filter(classe -> classe != null)
				.filter(classe -> Listener.class.isAssignableFrom(classe)).collect(Collectors.toList());
	}

	public static Set<Class<?>> getPackages(File file, String name) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		try {
			JarFile jar = new JarFile(file);
			for (Enumeration<JarEntry> entry = jar.entries(); entry.hasMoreElements();) {
				JarEntry jarEntry = (JarEntry) entry.nextElement();
				String named = jarEntry.getName().replace("/", ".");
				if ((named.startsWith(name)) && (named.endsWith(".class")) && (!named.contains("$"))) {
					classes.add(Class.forName(named.substring(0, named.length() - 6)));
				}
			}
			jar.close();
		} catch (Exception localException) {
		}
		return classes;
	}

	public static void createCommand(Command... cmds) {
		Class<?> CraftServers = Reflection.getCraftBukkitClass("CraftServer").getClass();
		try {
			Field f = Reflection.getCraftBukkitClass("CraftServer").getDeclaredField("commandMap");
			f.setAccessible(true);

			CommandMap map = (CommandMap) f.get(Bukkit.getServer());
			Command[] arrayOfCommand = cmds;
			int j = cmds.length;
			for (int i = 0; i < j; i++) {
				Command cmd = arrayOfCommand[i];
				map.register("comandos", cmd);
			}
		} catch (Exception localException) {
		}
	}
	
	public static CommandMap getCommandMap() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field f = Reflection.getCraftBukkitClass("CraftServer").getDeclaredField("commandMap");
		f.setAccessible(true);
		CommandMap map = (CommandMap) f.get(Bukkit.getServer());
		return map;
	}
	
}
