package discord.gdd.tab.item;

import discord.gdd.tab.util.Skin;

public interface TabItem {
    String getText();

    int getPing();

    Skin getSkin();

    boolean updateText();

    boolean updatePing();

    boolean updateSkin();

    boolean equals(Object object);
}
