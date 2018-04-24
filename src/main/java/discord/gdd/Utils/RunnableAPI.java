package discord.gdd.Utils;

import java.util.HashMap;
import java.util.Map.Entry;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Maps;

/*
* Criado pelo SasukeMCHC
* Adaptado pelo Wiljafor1(Muito pouco)
*/

public class RunnableAPI {
	JavaPlugin plugin;
	@Getter RunnableAPI instance;
	private HashMap<String, BukkitTask> RUNDATA = Maps.newHashMap();


	public RunnableAPI(){
	}

	public RunnableAPI(JavaPlugin plugin){
		this.plugin = plugin;
		this.instance = new RunnableAPI();
	}

	public enum Type {
		ASYNC, SYNC
	}

	public BukkitTask getTask(String name) {
		return RUNDATA.get(name);
	}
	
	public void cancel(String name) {
		if(getTask(name) != null) {
			getTask(name).cancel();
		}
	}
	
	public void cancelAll() {
		for( Entry<String, BukkitTask> set : RUNDATA.entrySet()) {
			String s = set.getKey();
			if(getTask(s) != null) {
				getTask(s).cancel();
			}
		}
	}
	
	public BukkitTask createTaskTimer(Type type, String name, Runnable r, long time, long delay) {
		BukkitTask t = null;
		if (type == Type.ASYNC) {
			t = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, r, time, delay);
		} else {
			t = Bukkit.getScheduler().runTaskTimer(plugin, r, time, delay);
		}
		RUNDATA.put(name, t);
		return t;
	}

	public BukkitTask createTaskLater(Type type, String name, Runnable r, long delay) {
		BukkitTask t = null;
		if (type == Type.ASYNC) {
			t = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, r, delay);
		} else {
			t = Bukkit.getScheduler().runTaskLater(plugin, r, delay);
		}
		RUNDATA.put(name, t);
		return t;
	}

}
