package discord.gdd.tab.tablist;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import discord.gdd.tab.Tab;
import discord.gdd.tab.item.PlayerTabItem;
import discord.gdd.tab.item.TabItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Uma implementa��o do SimpleTabList que e igual do Minecraft.
 */
public final class VanillaTabList extends SimpleTabList implements Listener {
    private Map<Player,String> names = new HashMap<>();

    private int taskId;

    public VanillaTabList(Tab tabbed, Player player, int maxItems) {
        super(tabbed, player, maxItems, -1, -1);
    }

    @Override
    public VanillaTabList enable() {
        super.enable();
        this.tabbed.GetPlugin().getServer().getPluginManager().registerEvents(this, this.tabbed.GetPlugin());

        for (Player target : Bukkit.getOnlinePlayers())
            addPlayer(target);

        // Because there is no PlayerListNameUpdateEvent in Bukkit
        this.taskId = this.tabbed.GetPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(this.tabbed.GetPlugin(), new Runnable() {
            @Override
            public void run() {
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (!names.containsKey(target))
                        continue;

                    String prevName = names.get(target);
                    String currName = target.getPlayerListName();

                    if (prevName.equals(currName))
                        continue;

                    int index = getTabItemIndex(target);
                    update(index);
                    names.put(target, currName);
                }
            }
        }, 0, 5);

        return this;
    }

    @Override
    public VanillaTabList disable() {
        super.disable();
        HandlerList.unregisterAll(this);
        this.tabbed.GetPlugin().getServer().getScheduler().cancelTask(this.taskId);
        return this;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        remove(getTabItemIndex(event.getPlayer()));
    }

    private void addPlayer(Player player) {
        add(getInsertLocation(player), new PlayerTabItem(player));
        this.names.put(player, player.getPlayerListName());
    }

    private int getTabItemIndex(Player player) {
        for (Entry<Integer,TabItem> item : this.items.entrySet()) {
            // items will always be players in this case, cast is safe
            PlayerTabItem tabItem = (PlayerTabItem) item.getValue();
            if (tabItem.getPlayer().equals(player))
                return item.getKey();
        }
        return -1;
    }

    private int getInsertLocation(Player player) {
        for (Entry<Integer,TabItem> item : this.items.entrySet()) {
            // items will always be players in this case, cast is safe
            PlayerTabItem tabItem = (PlayerTabItem) item.getValue();

            if (player.getName().compareTo(tabItem.getPlayer().getName()) < 0)
                return item.getKey();
        }
        return getNextIndex();
    }
}
