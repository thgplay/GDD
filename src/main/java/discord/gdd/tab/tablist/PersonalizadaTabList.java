package discord.gdd.tab.tablist;

import discord.gdd.tab.item.TabItem;

public interface PersonalizadaTabList extends TabList {
    int getMaxItems();
    void update();
    void update(int index);
    TabItem get(int index);
    boolean contains(int index);
    void add(TabItem item);
    void add(int index, TabItem item);
    TabItem set(int index, TabItem item);
    TabItem remove(int index);
    <T extends TabItem> T remove(T item);
    int getNextIndex();
}

