package discord.gdd;

import discord.gdd.config.json.JsonConfiguration;
import discord.gdd.customentity.MobUtils;
import discord.gdd.forge.ForgeAPI;
import discord.gdd.pentest.NetworkWatcher;
import discord.gdd.tab.Tab;
import discord.gdd.utils.Reflection;
import discord.gdd.utils.RunnableAPI;
import discord.gdd.utils.Vault;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;


/*
GDD API
*/

public class Main extends JavaPlugin {
    @Getter static Main instance;
    @Getter static RunnableAPI runnable;
    @Getter static NetworkWatcher watcher;
    @Getter static MobUtils mobUtils;
    @Getter static Vault vault;
    @Getter static ForgeAPI forge;
    @Getter static Tab tab;
    @Getter static JsonConfiguration config;
    @Getter static JsonConfiguration configMensagens;

    public void onEnable() {
        setup();
    }

    public void setup() {
        instance = this;
        runnable = new RunnableAPI().getInstance();
        watcher = new NetworkWatcher();
        mobUtils = new MobUtils();
        vault = new Vault();
        mobUtils.registrarTodos();
        forge = new ForgeAPI();
        config = new JsonConfiguration(this);
        // colocar o .json no nome da config é opcional, se omitido a API ira colocar
        configMensagens = new JsonConfiguration(this, "mensagens.json");

        Set<Class<?>> packages = Reflection.getPackages(getFile(),
                getDescription().getMain()
                        .replace(".Main", "")
                        .replace(".", "-")
                        .split("-")[0]);
        packages.stream()
                .filter(Listener.class::isAssignableFrom)
                .forEach(Reflection::registarListener);
        packages.stream()
                .filter(Command.class::isAssignableFrom)
                .forEach(Reflection::registerCommand);
    }

    //Aqui Inicia o modulo de network watcher
    public void startWatcherModule() {
        watcher.register();
    }

    //Aqui Para o modulo de network watcher
    public void stopWatcherModule() {
        watcher.unregister();
    }

}
