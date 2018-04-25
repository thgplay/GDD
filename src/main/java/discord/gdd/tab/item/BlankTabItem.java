package discord.gdd.tab.item;


import discord.gdd.tab.util.Skin;
import discord.gdd.tab.util.Skins;

public class BlankTabItem extends TextTabItem {
    public BlankTabItem(Skin skin) {
        super("", 1000, skin);
    }

    public BlankTabItem() {
        this(Skins.DEFAULT_SKIN);
    }
}
