package discord.gdd.tag.item;


import discord.gdd.tag.util.Skin;
import discord.gdd.tag.util.Skins;

public class BlankTabItem extends TextTabItem {
    public BlankTabItem(Skin skin) {
        super("", 1000, skin);
    }

    public BlankTabItem() {
        this(Skins.DEFAULT_SKIN);
    }
}
