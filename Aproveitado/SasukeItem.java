package discord.gdd.utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Maps;

/*
Criado por SasukeMCHC
*/

public class SasukeItem implements Listener {

    public static enum Clique {
        DIREITO, ESQUERDO, DIREITO_NO_AR, ESQUERDO_NO_AR, DIREITO_NO_BLOCO, ESQUERDO_NO_BLOCO
    }

    public static List<Action> getClick(Clique a) {
        if (a == Clique.DIREITO) {
            return Arrays.asList(new Action[] { Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK });
        } else if (a == Clique.ESQUERDO) {
            return Arrays.asList(new Action[] { Action.LEFT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK });
        } else if (a == Clique.ESQUERDO_NO_AR) {
            return Arrays.asList(new Action[] { Action.LEFT_CLICK_AIR });
        } else if (a == Clique.ESQUERDO_NO_BLOCO) {
            return Arrays.asList(new Action[] { Action.LEFT_CLICK_BLOCK });
        } else if (a == Clique.DIREITO_NO_AR) {
            return Arrays.asList(new Action[] { Action.RIGHT_CLICK_AIR });
        } else if (a == Clique.DIREITO_NO_BLOCO) {
            return Arrays.asList(new Action[] { Action.RIGHT_CLICK_BLOCK });
        }

        return null;

    }

    public static Map<Player, List<ItemAction>> actiondata = Maps.newHashMap();

    public static Map<ItemStack, InventoryAction> inventorydata = Maps.newHashMap();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack i = event.getCurrentItem();
        Player p = (Player) event.getWhoClicked();
        if(i == null) return;
        if(inventorydata.get(i) != null) {
            if(inventorydata.get(i).getPlayer().equals(p)) {
                event.setCancelled(inventorydata.get(i).isCancelEvent());
                inventorydata.get(i).getInventoryAction().run();
            }
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack i = p.getItemInHand();
        if(actiondata.get(p)  != null) {
            List<ItemAction> data = actiondata.get(p);
            for(ItemAction ia : data){
                if(ia.getItem().isSimilar(i)){
                    if(ia.getPlayer().equals(p)){
                        event.setCancelled(ia.isCancelEvent());
                        ia.getRunnable().run();
                    }
                }

            }

        }

    }

    public static ItemStack createItem(Material material, String name, int amount, int data, String... lore) {
        ItemStack item = new ItemStack(material, amount, (short) data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack createInteractiveItem(Player p,  ItemAction itemaction) {
        if(actiondata.get(p) != null){
            List<ItemAction> data = actiondata.get(p);
            data.add(itemaction);
            actiondata.put(p , data);
            return itemaction.getItem();
        }
        else{
            List<ItemAction> data = new ArrayList<ItemAction>();
            data.add(itemaction);
            actiondata.put(p , data);
            return itemaction.getItem();
        }
    }

    public static ItemStack createInventoryItem(ItemStack item,  InventoryAction itemaction) {
        inventorydata.put(item, itemaction);
        return item;
    }

    public static class InventoryAction {
        private Inventory inv;
        private Runnable inventoryAction;
        private boolean cancelEvent;
        private Player player;

        public InventoryAction(Inventory inv, Runnable inventoryAction  , Player player , boolean cancelEvent) {
            setPlayer(player);
            setInv(inv);
            setInventoryAction(inventoryAction);
            setCancelEvent(cancelEvent);
        }

        public Inventory getInv() {
            return inv;
        }

        public void setInv(Inventory inv) {
            this.inv = inv;
        }

        public Runnable getInventoryAction() {
            return inventoryAction;
        }

        public void setInventoryAction(Runnable inventoryAction) {
            this.inventoryAction = inventoryAction;
        }

        public boolean isCancelEvent() {
            return cancelEvent;
        }

        public void setCancelEvent(boolean cancelEvent) {
            this.cancelEvent = cancelEvent;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
    }

    public static class ItemAction {
        private Clique actions;
        private Runnable runnables;
        private boolean cancelEvent;
        private Player player;
        private ItemStack Item;

        public ItemAction(Player player , Clique actions, Runnable runnables , boolean cancelEvent, ItemStack item) {
            setPlayer(player);
            setActions(actions);
            setRunnables(runnables);
            setCancelEvent(cancelEvent);
            setItem(item);
        }

        public Clique getActions() {
            return actions;
        }

        public void setActions(Clique actions) {
            this.actions = actions;
        }

        public Runnable getRunnable() {
            return runnables;
        }

        public void setRunnables(Runnable runnables) {
            this.runnables = runnables;
        }

        public boolean isCancelEvent() {
            return cancelEvent;
        }

        public void setCancelEvent(boolean cancelEvent) {
            this.cancelEvent = cancelEvent;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public ItemStack getItem() {
            return Item;
        }

        public void setItem(ItemStack item) {
            Item = item;
        }

    }

}
