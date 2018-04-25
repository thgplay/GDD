package discord.gdd.tab;

import com.avaje.ebeaninternal.server.core.RelationalQueryRequest;
import com.google.common.base.Preconditions;

import discord.gdd.tab.tablist.*;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/*
Api de tab usando protocol lib, OBS: codigo encontrado na internet e adaptado algumas coisas, nao encontrei o autor original.
Adaptado pelo Wiljafor1, e algumas modificacoes, caso econtrem o codigo original colocar os direitos aqui!
*/

public class Tab implements Listener {
    private static Map<Plugin,Tab> instances = new HashMap<>();
    private static Level logLevel = Level.WARNING;

    private final Plugin plugin;
    private final Map<Player,TabList> tabLists;

    public Tab(Plugin plugin) {
        this.plugin = plugin;
        this.tabLists = new HashMap<>();
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        instances.put(plugin, this);
    }

    public static void log(Level level, String message) {
        if (level.intValue() >= getLogLevel().intValue())
            System.out.println("[" + level.getName() + "] " + message);
    }

    public static Tab getTabbed(Plugin plugin) {
        return instances.get(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        destroyTabList(event.getPlayer());
    }

    public TabList getTabList(Player player) {
        return this.tabLists.get(player);
    }

    public TabList destroyTabList(Player player) {
        TabList tabList = getTabList(player);
        if (tabList == null)
            return null;
        this.tabLists.remove(player);
        return tabList.disable();
    }

    public TabList destroyTabList(TabList tabList) {
        return destroyTabList(tabList.getPlayer());
    }

    public ComTitleTabList newTitledTabList(Player player) {
        return put(player, new ComTitleTabList(player).enable());
    }

    public VanillaTabList newDefaultTabList(Player player) {
        return put(player, new VanillaTabList(this, player, -1).enable());
    }

    public SimpleTabList newSimpleTabList(Player player) {
        return newSimpleTabList(player, SimpleTabList.MAXIMUM_ITEMS);
    }

    public SimpleTabList newSimpleTabList(Player player, int maxItems) {
        return newSimpleTabList(player, maxItems, -1);
    }

    public SimpleTabList newSimpleTabList(Player player, int maxItems, int minColumnWidth) {
        return newSimpleTabList(player, maxItems, minColumnWidth, -1);
    }

    public SimpleTabList newSimpleTabList(Player player, int maxItems, int minColumnWidth, int maxColumnWidth) {
        return put(player, new SimpleTabList(this, player, maxItems, minColumnWidth, maxColumnWidth).enable());
    }

    public TableTabList newTableTabList(Player player) {
        return newTableTabList(player, 4);
    }

    public TableTabList newTableTabList(Player player, int columns) {
        return newTableTabList(player, columns, -1);
    }
    
    public TableTabList newTableTabList(Player player, int columns, int minColumnWidth) {
        return newTableTabList(player, columns, minColumnWidth, -1);
    }

    public TableTabList newTableTabList(Player player, int columns, int minColumnWidth, int maxColumnWidth) {
        return put(player, new TableTabList(this, player, columns, minColumnWidth, maxColumnWidth).enable());
    }

    private <T extends TabList> T put(Player player, T tabList) {
        Preconditions.checkArgument(!this.tabLists.containsKey(player), "player '" + player.getName() + "' j� tem um tablist");
        this.tabLists.put(player, tabList);
        return tabList;
    }

	public static Level getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(Level logLevel) {
		this.logLevel = logLevel;
	}
	
	public Plugin GetPlugin() {
		return plugin;
	}
}
