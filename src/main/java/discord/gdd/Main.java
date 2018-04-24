package discord.gdd;

import discord.gdd.Utils.RunnableAPI;
import discord.gdd.customentity.MobUtils;
import discord.gdd.pentest.NetworkWatcher;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "GDD-API", version = "1.0")
@Description(desc = "Uma api feita pelos membros do grupo do discord Grupo de Desenvolvendores(Quem quiser melhorar so falar)")
@Author(name = "Wiljafor1")
@Author(name = "MrD4rkBr")
@LogPrefix(prefix = "GDD")

public class Main extends JavaPlugin{
    @Getter static Main instance;
    @Getter static RunnableAPI runnable;
    @Getter static NetworkWatcher watcher;
    @Getter static MobUtils mobUtils;

    public void onEnable(){
        setup();
    }

    public void setup(){
        instance = this;
        runnable = new RunnableAPI().getInstance();
        watcher = new NetworkWatcher();
        mobUtils = new MobUtils();
        mobUtils.registrarTodos();
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
