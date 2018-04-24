package discord.gdd.Utils;

import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/*
Criado pelo Frach - R.I.P. Avicii#6234 - Membro da GDD
*/

public class ItemStackBuilder {
    private ItemStack item;
    private ItemMeta meta;
    private EnchantmentStorageMeta storage;
    private List<String> lore;
    private boolean glow;
    private net.minecraft.server.v1_8_R3.ItemStack item2;
    private SkullMeta skullMeta;

    public ItemStackBuilder(final ItemStack item) {
        this.glow = false;
        this.item = item;
        if (item.getType() == Material.ENCHANTED_BOOK) {
            this.storage = (EnchantmentStorageMeta) item.getItemMeta();
            this.lore = ((this.storage != null && this.storage.hasLore()) ? this.storage.getLore() : new ArrayList<String>());
        } else if (item.getType() == Material.SKULL_ITEM || item.getType() == Material.SKULL) {
            this.skullMeta = (SkullMeta) item.getItemMeta();
            this.lore = ((this.skullMeta != null && this.skullMeta.hasLore()) ? this.skullMeta.getLore() : new ArrayList<String>());
        } else {
            this.meta = item.getItemMeta();
            this.lore = ((this.meta != null && this.meta.hasLore()) ? this.meta.getLore() : new ArrayList<String>());
        }
    }

    public ItemStackBuilder(final Material material) {
        this(new ItemStack(material));
    }

    public ItemStackBuilder setType(final Material type) {
        this.item.setType(type);
        return this;
    }

    public ItemStackBuilder setOwner(final String owner) {
        if (this.item.getType() == Material.SKULL_ITEM || this.item.getType() == Material.SKULL) {
            this.skullMeta.setOwner(owner);
            return this;
        }
        return this;
    }

    public ItemStackBuilder setName(final String name) {
        if (this.item.getType() == Material.ENCHANTED_BOOK) {
            this.storage.setDisplayName(name);
            return this;
        }
        if (this.item.getType() == Material.SKULL_ITEM || this.item.getType() == Material.SKULL) {
            this.skullMeta.setDisplayName(name);
            return this;
        }
        this.meta.setDisplayName(name);
        return this;
    }

    public ItemStackBuilder addLore(final String... l) {
        for (final String x : l) {
            this.lore.add(x);
        }
        return this;
    }

    public ItemStackBuilder addLore(final List<String> l) {
        for (final String x : l) {
            this.lore.add(x);
        }
        return this;
    }

    public ItemStackBuilder addLoreList(final List<String> l) {
        for (final String s : l) {
            this.lore.add(s.replace("&", "�"));
        }
        return this;
    }

    public ItemStackBuilder addStoredEnchantment(final Enchantment e, final int level) {
        this.storage.addStoredEnchant(e, level, true);
        return this;
    }

    public ItemStackBuilder addEnchantment(final Enchantment e, final int level) {
        this.meta.addEnchant(e, level, true);
        return this;
    }

    public ItemStackBuilder setDurability(final int durability) {
        this.item.setDurability((short) durability);
        return this;
    }

    public ItemStackBuilder setAmount(final int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemStackBuilder replaceLore(final String oldLore, final String newLore) {
        for (int i = 0; i < this.lore.size(); ++i) {
            if (this.lore.get(i).contains(oldLore)) {
                this.lore.remove(i);
                this.lore.add(i, newLore);
                break;
            }
        }
        return this;
    }

    public ItemStack build() {
        if (this.item.getType() == Material.ENCHANTED_BOOK) {
            if (!this.lore.isEmpty()) {
                this.storage.setLore(this.lore);
                this.lore.clear();
            }
            this.item.setItemMeta((ItemMeta) this.storage);
            return this.item;
        }
        if (this.item.getType() == Material.SKULL || this.item.getType() == Material.SKULL_ITEM) {
            if (!this.lore.isEmpty()) {
                this.skullMeta.setLore(this.lore);
                this.lore.clear();
            }
            this.item.setItemMeta((ItemMeta) this.skullMeta);
            return this.item;
        }
        if (!this.lore.isEmpty()) {
            this.meta.setLore(this.lore);
            this.lore.clear();
        }
        this.item.setItemMeta(this.meta);
        if (this.glow) {
            this.addGlow();
            return (ItemStack) CraftItemStack.asCraftMirror(this.item2);
        }
        return this.item;
    }

    public ItemStackBuilder addGlow() {
        this.item2 = CraftItemStack.asNMSCopy(this.item);
        NBTTagCompound tag = null;
        if (!this.item2.hasTag()) {
            tag = new NBTTagCompound();
            this.item2.setTag(tag);
        }
        if (tag == null) {
            tag = this.item2.getTag();
        }
        final NBTTagList ench = new NBTTagList();
        tag.set("ench", (NBTBase) ench);
        this.item2.setTag(tag);
        this.glow = true;
        return this;
    }

}
