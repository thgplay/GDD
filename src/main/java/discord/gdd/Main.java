package discord.gdd;

import discord.gdd.config.json.JsonConfiguration;
import discord.gdd.customentity.MobUtils;
import discord.gdd.database.Redis;
import discord.gdd.forge.ForgeAPI;
import discord.gdd.forge.Mod;
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
    @Getter static JsonConfiguration configs;
    @Getter static JsonConfiguration configsMensagens;

    /* BETA */
    @Getter static Redis redis;

    public void onEnable() {
        setup();
    }

    public void setup() {
        instance = this;

        redis = new Redis("localhost", 6379, null);
        redis.start();
        redis.setCache("gdd_teste", "ola pessoal <3");
        redis.setCache("gdd_objeto", new Mod("teste", "irineu"));
        getServer().getConsoleSender().sendMessage("Redis ligado e setando data cache!");

        runnable = new RunnableAPI().getInstance();
        watcher = new NetworkWatcher();
        mobUtils = new MobUtils();
        vault = new Vault();
        vault.start();
        mobUtils.registrarTodos();
        forge = new ForgeAPI();
        configs = new JsonConfiguration(this);
        // colocar o .json no nome da config é opcional, se omitido a API ira colocar
        configsMensagens = new JsonConfiguration(this, "mensagens.json");

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
