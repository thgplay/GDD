package discord.gdd.tag.item;

import discord.gdd.tag.util.Skin;

public interface TabItem {
    String getText();

    int getPing();

    Skin getSkin();

    boolean updateText();

    boolean updatePing();

    boolean updateSkin();

    boolean equals(Object object);
}
