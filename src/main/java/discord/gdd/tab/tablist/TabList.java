package discord.gdd.tab.tablist;

import org.bukkit.entity.Player;

/**
 * O n�vel mais alto de uma lista de tabelas
 */
public interface TabList {
    Player getPlayer();

    /**
     * Ativa a lista de tab, inicia qualquer necessidade listeners/schedules.
     * @return A tab list.
     */
    TabList enable();

    /**
     * Desativa a tab list: p�ra de existir listeners/schedules.
     * @return A tab list.
     */
    TabList disable();
}
