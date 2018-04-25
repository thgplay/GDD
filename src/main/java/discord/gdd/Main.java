package discord.gdd;

import discord.gdd.Utils.Reflection;
import discord.gdd.Utils.RunnableAPI;
import discord.gdd.Utils.Vault;
import discord.gdd.customentity.MobUtils;
import discord.gdd.forge.ForgeAPI;
import discord.gdd.pentest.NetworkWatcher;
import discord.gdd.tab.Tab;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "GDD-API", version = "1.0")
@Description(desc = "Uma api feita pelos membros do grupo do discord Grupo de Desenvolvendores(Quem quiser melhorar so falar)")
@Author(name = "Wiljafor1")
@Author(name = "MrD4rkBr")
@LogPrefix(prefix = "GDD")

/*
GDD API
*/

public class Main extends JavaPlugin{
    @Getter static Main instance;
    @Getter static RunnableAPI runnable;
    @Getter static NetworkWatcher watcher;
    @Getter static MobUtils mobUtils;
    @Getter static Vault vault;
    @Getter static ForgeAPI forge;
    @Getter static Tab tab;

    public void onEnable(){
        setup();
    }

    public void setup(){
        instance = this;
        runnable = new RunnableAPI().getInstance();
        watcher = new NetworkWatcher();
        mobUtils = new MobUtils();
        vault = new Vault();
        mobUtils.registrarTodos();
        forge = new ForgeAPI();
        Reflection.getPackages(getFile(), getDescription().getMain().replace(".Main", "").replace(".", "-").split("-")[0])
                .forEach(c -> {
                    if (Listener.class.isAssignableFrom(c)) {
                        try {
                            getServer().getPluginManager().registerEvents((Listener) c.newInstance(), this);
                        } catch (Exception e) {
                            getServer().getConsoleSender().sendMessage("(Evento):" + e.getMessage());
                        }
                    }
                    if (Command.class.isAssignableFrom(c)) {
                        try {
                            Reflection.createCommand((Command) c.newInstance());
                        } catch (Exception e) {
                            getServer().getConsoleSender().sendMessage("(Comando):" + e.getMessage());
                        }
                    }
                });
        ;
    }

    //Aqui Inicia o modulo de network watcher
    public void startWatcherModule(){
        watcher.register();
    }

    //Aqui Para o modulo de network watcher
    public void stopWatcherModule(){
        watcher.unregister();
    }


}
