package discord.gdd.config.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Type;
import java.util.List;

public class JsonConfiguration extends JsonConfigurationFile {

    public JsonConfiguration(JavaPlugin plugin) {
        this(plugin, "config");
    }

    public JsonConfiguration(JavaPlugin plugin, String configName) {
        super(plugin, configName);
    }

    public Integer getInteger(String path) {
        return getInteger(path, null);
    }

    public Integer getInteger(String path, Integer defaultValue) {
        return get(path, defaultValue);
    }

    public Double getDouble(String path) {
        return getDouble(path, null);
    }

    public Double getDouble(String path, Double defaultValue) {
        return get(path, defaultValue);
    }

    public String getString(String path) {
        return getString(path, null);
    }

    public String getString(String path, String defaultValue) {
        return get(path, defaultValue);
    }

    public Long getLong(String path) {
        return getLong(path, null);
    }

    public Long getLong(String path, Long defaultValue) {
        return get(path, defaultValue);
    }

    public List<String> getStringList(String path) {
        return getStringList(path, null);
    }

    public List<String> getStringList(String path, List<String> defaultValue) {
        return get(path, defaultValue);
    }

    private <T> T get(String path, T defaultValue) {
        try {
            T valor = gson.fromJson(get(path), (Type) defaultValue.getClass());
            return valor == null ? defaultValue : valor;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public void addDefault(String path, Object value){
        if (!contains(path)) set(path, value);
    }

    public boolean set(String path, Object value) {
        try {
            if (path.contains(".")) {
                String[] subPaths = path.split("\\.");
                Bukkit.broadcastMessage("subPaths: " + subPaths.length);
                JsonObject[] jObjts = new JsonObject[subPaths.length - 1];

                for (int i = 0; i < subPaths.length; i++) {
                    String p = subPaths[i];
                    // Verificar se ja ta na ultima section
                    if (i == subPaths.length - 1) {
                        return set(jObjts[i - 1], p, value);
                    }

                    // Verificar se é a primeira section para definir os defaults
                    if (i == 0) {
                        if (!contains(p)) {
                            JsonObject first = new JsonObject();
                            config.add(p, first);
                            jObjts[i] = first;
                            continue;
                        }
                        jObjts[i] = config.getAsJsonObject(p);
                        continue;
                    }
                    // Resto da gambiarra :D
                    JsonObject last = jObjts[i - 1];

                    if (!last.has(p)) {
                        JsonObject f = new JsonObject();
                        last.add(p, f);
                        jObjts[i] = f;
                        continue;
                    }
                    jObjts[i] = last.getAsJsonObject(p);
                }
                return true;
            }
            return set(config, path, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean set(JsonObject jsonObject, String path, Object value) {
        jsonObject.add(path, gson.toJsonTree(value));
        save();
        return true;
    }

    public boolean contains(String path) {
        try {
            if (path.contains(".")) {
                String[] subPaths = path.split("\\.");
                JsonElement subPath = null;
                for (String p : subPaths) {
                    // pegar o primeiro elemento
                    if (subPath == null) {
                        if (!contains(p)) return false;
                        subPath = get(p);
                        continue;
                    }

                    JsonObject jObject = subPath.getAsJsonObject();
                    if (!jObject.has(p)) return false;

                    subPath = jObject.get(p);
                }
                return true;
            }
            return config.has(path);
        } catch (Exception e) {
            return false;
        }
    }

    private JsonElement get(String path) {
        if (path.contains(".")) {
            String[] subPaths = path.split("\\.");
            JsonElement subPath = null;
            for (String p : subPaths) {
                // pegar o primeiro elemento
                if (subPath == null) {
                    if (!contains(p)) return null;
                    subPath = get(p);
                    continue;
                }

                JsonObject jObject = subPath.getAsJsonObject();
                if (!jObject.has(p)) return null;

                subPath = jObject.get(p);
            }
            return subPath;
        }

        return config.get(path);
    }
}
