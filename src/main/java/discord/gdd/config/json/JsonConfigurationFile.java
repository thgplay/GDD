package discord.gdd.config.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;

class JsonConfigurationFile {
    private final File configFile;
    private final JavaPlugin plugin;
    final Gson gson;
    JsonObject config;

    JsonConfigurationFile(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        gson = new GsonBuilder().setPrettyPrinting().create();

        if (!name.toLowerCase().endsWith(".json")) name += ".json";

        configFile = new File(plugin.getDataFolder(), name);

        try {
            loadFile();
            loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        try {
            loadFile();
            loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(){
        try (Writer writer = new FileWriter(configFile)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFile() throws IOException {
        if (!configFile.exists()) {
            log(Level.WARNING, "A config \"" + configFile.getName() + "\" não foi encontrada, a criar uma nova...");
            if (configFile.createNewFile())
                log("A config foi criada com sucesso!");
            else
                log(Level.SEVERE, "Ocurreu um erro ao criar a config!");
        }
    }

    private void loadConfig() throws Exception {
        log("Loading config");
        config = gson.fromJson(new JsonReader(new FileReader(configFile)), JsonObject.class);
        if (config == null)
            config = new Gson().fromJson("{}", JsonObject.class);
        log("Loaded "  + (config != null));
    }

    private void log(String msg) {
        log(Level.INFO, msg);
    }

    private void log(Level logLevel, String msg) {
        plugin.getLogger().log(logLevel, "[JsonConfiguration] " + msg);
    }
}
