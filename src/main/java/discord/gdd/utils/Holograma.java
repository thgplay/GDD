package discord.gdd.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 Autor: Braayy
 
 Exemplo:
 	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Holograma holograma = new Holograma(e.getPlayer().getLocation(), "§akk eae men", "kk eae men2");
		holograma.spawn();
		
		new BukkitRunnable() {
			@Override
			public void run() {
				holograma.getLines().add("§9HU3§aHU3§eHU3");
				holograma.update();
			}
		}.runTaskTimer(this, 0, 20L * 2);
	}
 */
public class Holograma {
	
	private static Class<?> ENTITY_ARMOR_STAND_CLASS;
	private static Class<?> WORLD_CLASS;
	private static Class<?> CRAFT_WORLD_CLASS;
	private static Class<?> CRAFT_PLAYER_CLASS;
	private static Class<?> ENTITY_PLAYER_CLASS;
	private static Class<?> PLAYER_CONNECTION_CLASS;
	private static Class<?> PACKET_CLASS;
	private static Class<?> PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CLASS;
	private static Class<?> ENTITY_LIVING_CLASS;
	private static Class<?> PACKET_PLAY_OUT_ENTITY_DESTROY_CLASS;
	
	private static Constructor<?> ENTITY_ARMOR_STAND_CONSTRUCTOR;
	private static Constructor<?> PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CONSTRUCTOR;
	private static Constructor<?> PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR;
	
	private static Field PLAYER_CONNECTION_FIELD;
	
	private static Method CRAFT_WORLD_GET_HANDLE_METHOD;
	private static Method SET_CUSTOM_NAME_METHOD;
	private static Method SET_CUSTOM_NAME_VISIBLE_METHOD;
	private static Method SET_GRAVITY_METHOD;
	private static Method SET_INVISIBLE_METHOD;
	private static Method CRAFT_PLAYER_GET_HANDLE_METHOD;
	private static Method SEND_PACKET_METHOD;
	private static Method GET_ID_METHOD;
	
	static {
		try {
			ENTITY_ARMOR_STAND_CLASS = getNMSClass("EntityArmorStand");
			WORLD_CLASS = getNMSClass("World");
			CRAFT_WORLD_CLASS = getOBCClass("CraftWorld");
			CRAFT_PLAYER_CLASS = getOBCClass("entity.CraftPlayer");
			ENTITY_PLAYER_CLASS = getNMSClass("EntityPlayer");
			PLAYER_CONNECTION_CLASS = getNMSClass("PlayerConnection");
			PACKET_CLASS = getNMSClass("Packet");
			PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CLASS = getNMSClass("PacketPlayOutSpawnEntityLiving");
			ENTITY_LIVING_CLASS = getNMSClass("EntityLiving");
			PACKET_PLAY_OUT_ENTITY_DESTROY_CLASS = getNMSClass("PacketPlayOutEntityDestroy");
			
			ENTITY_ARMOR_STAND_CONSTRUCTOR = ENTITY_ARMOR_STAND_CLASS.getConstructor(WORLD_CLASS, double.class, double.class, double.class);
			PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CONSTRUCTOR = PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CLASS.getConstructor(ENTITY_LIVING_CLASS);
			PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR = PACKET_PLAY_OUT_ENTITY_DESTROY_CLASS.getConstructor(int[].class);
			
			PLAYER_CONNECTION_FIELD = ENTITY_PLAYER_CLASS.getDeclaredField("playerConnection");
			
			CRAFT_WORLD_GET_HANDLE_METHOD = CRAFT_WORLD_CLASS.getMethod("getHandle");
			CRAFT_PLAYER_GET_HANDLE_METHOD = CRAFT_PLAYER_CLASS.getMethod("getHandle");
			SET_CUSTOM_NAME_METHOD = ENTITY_ARMOR_STAND_CLASS.getMethod("setCustomName", String.class);
			SET_CUSTOM_NAME_VISIBLE_METHOD = ENTITY_ARMOR_STAND_CLASS.getMethod("setCustomNameVisible", boolean.class);
			SET_GRAVITY_METHOD = ENTITY_ARMOR_STAND_CLASS.getMethod("setGravity", boolean.class);
			SET_INVISIBLE_METHOD = ENTITY_ARMOR_STAND_CLASS.getMethod("setInvisible", boolean.class);
			SEND_PACKET_METHOD = PLAYER_CONNECTION_CLASS.getMethod("sendPacket", PACKET_CLASS);
			GET_ID_METHOD = ENTITY_ARMOR_STAND_CLASS.getMethod("getId");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Object[] stands;
	private List<String> lines;
	private Location location;
	
	public Holograma(Location location, String... lines) {
		this(location, asList(lines));
	}
	
	public Holograma(Location location, List<String> lines) {
		this.location = location;
		this.lines = lines;
	}
	
	public Holograma(List<String> lines) {
		this(null, lines);
	}
	
	public Holograma(String... lines) {
		this(null, lines);
	}
	
	public Holograma spawn(Location l) {
		if (l != null) this.location = l;
		
		if (location == null) throw new NullPointerException("location cannot be null");
		
		stands = new Object[lines.size()];
		
		try {
			for (int i = 0; i < stands.length; i++) {
				Object world = CRAFT_WORLD_GET_HANDLE_METHOD.invoke(location.getWorld());
				Object stand = ENTITY_ARMOR_STAND_CONSTRUCTOR.newInstance(world, location.getX(), location.getY() - i * 0.2f, location.getZ());
				
				SET_CUSTOM_NAME_METHOD.invoke(stand, lines.get(i));
				SET_CUSTOM_NAME_VISIBLE_METHOD.invoke(stand, true);
				SET_GRAVITY_METHOD.invoke(stand, false);
				SET_INVISIBLE_METHOD.invoke(stand, true);
				
				stands[i] = stand;
			}
			
			for (Player o : Bukkit.getOnlinePlayers()) {
				Object handle = CRAFT_PLAYER_GET_HANDLE_METHOD.invoke(o);
				Object playerConnection = PLAYER_CONNECTION_FIELD.get(handle);
				
				for (int i = 0; i < stands.length; i++) {
					SEND_PACKET_METHOD.invoke(playerConnection, PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CONSTRUCTOR.newInstance(stands[i]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Holograma spawn() {
		return spawn(null);
	}
	
	public Holograma update(Location l) {
		if (l != null) this.location = l;
		
		if (location == null) throw new NullPointerException("location cannot be null");
		
		try {
			for (Player o : Bukkit.getOnlinePlayers()) {
				Object handle = CRAFT_PLAYER_GET_HANDLE_METHOD.invoke(o);
				Object playerConnection = PLAYER_CONNECTION_FIELD.get(handle);

				for (int i = 0; i < stands.length; i++) {
					int id = (int) GET_ID_METHOD.invoke(stands[i]);
					SEND_PACKET_METHOD.invoke(playerConnection, PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR.newInstance(new int[] { id }));
					
					stands[i] = null;
				}
			}
			
			stands = new Object[lines.size()];
			
			for (int i = 0; i < stands.length; i++) {
				Object world = CRAFT_WORLD_GET_HANDLE_METHOD.invoke(location.getWorld());
				Object stand = ENTITY_ARMOR_STAND_CONSTRUCTOR.newInstance(world, location.getX(), location.getY() - i * 0.2f, location.getZ());
				
				SET_CUSTOM_NAME_METHOD.invoke(stand, lines.get(i));
				SET_CUSTOM_NAME_VISIBLE_METHOD.invoke(stand, true);
				SET_GRAVITY_METHOD.invoke(stand, false);
				SET_INVISIBLE_METHOD.invoke(stand, true);
				
				stands[i] = stand;
			}
			
			for (Player o : Bukkit.getOnlinePlayers()) {
				Object handle = CRAFT_PLAYER_GET_HANDLE_METHOD.invoke(o);
				Object playerConnection = PLAYER_CONNECTION_FIELD.get(handle);
				
				for (int i = 0; i < stands.length; i++) {
					SEND_PACKET_METHOD.invoke(playerConnection, PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CONSTRUCTOR.newInstance(stands[i]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Holograma remove() {
		if (stands == null) return this;
		
		try {
			for (Player o : Bukkit.getOnlinePlayers()) {
				Object handle = CRAFT_PLAYER_GET_HANDLE_METHOD.invoke(o);
				Object playerConnection = PLAYER_CONNECTION_FIELD.get(handle);

				for (int i = 0; i < stands.length; i++) {
					int id = (int) GET_ID_METHOD.invoke(stands[i]);
					SEND_PACKET_METHOD.invoke(playerConnection, PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR.newInstance(new int[] { id }));
					
					stands[i] = null;
				}
			}
			
			stands = null;
			lines.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Holograma update() {
		return update(null);
	}
	
	public List<String> getLines() {
		return lines;
	}
	
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	
	public void setLines(String... lines) {
		setLines(Arrays.asList(lines));
	}
	
	@SuppressWarnings("unchecked")
	private static <T> List<T> asList(T... array) {
		List<T> list = new ArrayList<>();
		for (T t : array) list.add(t);
		
		return list;
	}
	
	private static Class<?> getNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (Exception e) {
			Bukkit.getLogger().severe("Nao foi possivel encontrar a Classe \'net.minecraft.server." + version + "." + name + "\'");
			
			return null;
		}
	}
	
	private static Class<?> getOBCClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
		} catch (Exception e) {
			Bukkit.getLogger().severe("Nao foi possivel encontrar a Classe \'org.bukkit.craftbukkit." + version + "." + name + "\'");
			
			return null;
		}
	}
	
}