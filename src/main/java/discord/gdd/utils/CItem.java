package discord.gdd.utils;
/*
Criado por Willian(Wiljafor1) em 25.04.18
ConstructItem = CItem
*/

import com.google.common.collect.Maps;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CItem implements Listener
{
    private ItemStack item;
    public Map<Player, List<ItemAction>> actiondata = Maps.newHashMap();

    public Map<ItemStack, InventoryAction> inventorydata = Maps.newHashMap();

    public CItem(Material material)
    {
        this.item = new ItemStack(material, 1);
    }

    public CItem(int materialId)
    {
        this.item = new ItemStack(Material.getMaterial(materialId), 1);
    }

    public CItem(ItemStack itemStack) {
        this.item = itemStack.clone();
    }

    public CItem setarNome(String name) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        this.item.setItemMeta(meta);
        return this;
    }

    public CItem setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public int getAmount() {
        return this.item.getAmount();
    }

    public CItem setDurability(int durability) {
        this.item.setDurability((short)durability);
        return this;
    }

    public int getDurability() {
        return this.item.getDurability();
    }

    public CItem setData(int data)
    {
        this.item.setData(new MaterialData(this.item.getType(), (byte)data));
        return this;
    }

    public int getData()
    {
        return this.item.getData().getData();
    }

    public CItem setLore(String[] lore) {
        if ((lore == null) || (lore.length == 0)) {
            clearLore();
        }
        ArrayList lr = new ArrayList();
        for (int i = 0; i < lore.length; i++) {
            if (lore[i].contains("\n")) {
                for (String s : lore[i].split("\n"))
                    lr.add(s.replaceAll("&", "�"));
            }
            else {
                lr.add(lore[i].replaceAll("&", "�"));
            }
        }
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lr);
        item.setItemMeta(meta);
        return this;
    }

    public CItem addLore(String[] lore) {
        ItemMeta meta = this.item.getItemMeta();
        List original_lore = meta.getLore();
        if (original_lore == null)
            meta.setLore(new ArrayList());
        for (String s : lore)
            original_lore.add(s);
        meta.setLore(original_lore);
        this.item.setItemMeta(meta);
        return this;
    }

    public List<String> getLore() {
        return this.item.getItemMeta().getLore();
    }

    public CItem clearLore() {
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(new ArrayList());
        this.item.setItemMeta(meta);
        return this;
    }

    public CItem addEnchantment(Enchantment enchantment) {
        this.item.addUnsafeEnchantment(enchantment, 0);
        return this;
    }

    public CItem addEnchantment(Enchantment enchantment, int level) {
        this.item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public CItem addEnchantments(Enchantment[] enchantments, int[] levels) {
        for (int i = 0; i < enchantments.length; i++)
            this.item.addUnsafeEnchantment(enchantments[i], levels[i]);
        return this;
    }

    public CItem removeEnchantments() {
        Enchantment[] enchantments = getEnchantments();
        for (int i = 0; i < enchantments.length; i++)
            this.item.removeEnchantment(enchantments[i]);
        return this;
    }

    public Enchantment[] getEnchantments() {
        return (Enchantment[])this.item.getEnchantments().keySet().toArray(new Enchantment[this.item.getEnchantments().keySet().size()]);
    }

    public Map<Enchantment, Integer> getDetailedEnchantments() {
        return this.item.getEnchantments();
    }

    public CItem setType(Material material) {
        this.item.setType(material);
        return this;
    }

    public Material getType() {
        return this.item.getType();
    }

    public CItem setType(int materialId)
    {
        this.item.setTypeId(materialId);
        return this;
    }

    public CItem setArmorColor(Color color) {
        if ((this.item.getType().equals(Material.LEATHER_HELMET)) || (this.item.getType().equals(Material.LEATHER_CHESTPLATE)) ||
                (this.item.getType().equals(Material.LEATHER_LEGGINGS)) ||
                (this.item.getType().equals(Material.LEATHER_BOOTS))) {
            LeatherArmorMeta meta = (LeatherArmorMeta)this.item.getItemMeta();
            meta.setColor(color);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public CItem setSkullOwner(String playerName) {
        if (this.item.getType().equals(Material.SKULL_ITEM)) {
            setData(3);
            SkullMeta meta = (SkullMeta)this.item.getItemMeta();
            meta.setOwner(playerName);
            this.item.setItemMeta(meta);
        }
        return this;
    }


    public CItem clone() {
        return new CItem(this.item);
    }

    public String toString() {
        return this.item.toString();
    }

    public ItemStack build() {
        return this.item;
    }

    public Item spawn(Location l) {
        return l.getWorld().dropItem(l, this.item);
    }

    public static boolean equals(ItemStack item1, ItemStack item2) {
        return item1.isSimilar(item2);
    }

    public static ItemStack addLore(ItemStack itemStack, String[] toAdd) {
        ItemMeta meta = itemStack.getItemMeta();
        List lore = meta.hasLore() ? meta.getLore() : new ArrayList();
        for (String s : toAdd)
            lore.add(s.replaceAll("&", "�"));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static String getValueFromLore(ItemStack itemStack, String key) {
        return getValueFromLore(itemStack, key, ":", true);
    }

    public static String getValueFromLore(ItemStack itemStack, String key, String separator, boolean trim) {
        if (!itemStack.hasItemMeta())
            return null;
        if (!itemStack.getItemMeta().hasLore())
            return null;
        String s = null;
        List lore = new ArrayList(itemStack.getItemMeta().getLore());
        for (int i = 0; i < lore.size(); i++)
            if (((String)lore.get(i)).contains(key))
                s = (String)lore.get(i);
        if (s == null)
            return null;
        String[] args = s.split(separator);
        return trim ? args[1].trim() : args[1];
    }

    /*
    Adaptando api do SasukeMcHc com api do Wiljafor1
    */

    public enum Clique {
        DIREITO, ESQUERDO, DIREITO_NO_AR, ESQUERDO_NO_AR, DIREITO_NO_BLOCO, ESQUERDO_NO_BLOCO
    }

    public List<Action> getClick(Clique a) {
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

    public class InventoryAction {
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

    public class ItemAction {
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

    public ItemStack createInteractiveItem(Player p,  ItemAction itemaction) {
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

    public ItemStack createInventoryItem(InventoryAction itemaction) {
        inventorydata.put(this.item, itemaction);
        return item;
    }

}
