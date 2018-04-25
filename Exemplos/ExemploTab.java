import discord.gdd.Main;
import discord.gdd.tab.Tab;
import discord.gdd.tab.item.PlayerTabItem;
import discord.gdd.tab.item.TextTabItem;
import discord.gdd.tab.tablist.PersonalizadaTabList;
import discord.gdd.tab.tablist.TableTabList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/*
Exemplo feito por Wiljafor1
*/

public class ExemploTab {
    public static ExemploTab getInstance() {
        return new ExemploTab();
    }

    public void tab(Player p) {//Criar uma tab Vazia ao um player
        Tab tabb = Main.getTab();
        PersonalizadaTabList tab = tabb.newTableTabList(p, 4);
        for(int y = 0; y < 20 ; y++) {
            ((TableTabList) tab).set(0, y, new TextTabItem("§e ", 0));
            ((TableTabList) tab).set(1, y, new TextTabItem("§e ", 0));
            ((TableTabList) tab).set(2, y, new TextTabItem("§e ", 0));
            ((TableTabList) tab).set(3, y, new TextTabItem("§e ", 0));
        }
    }
    public void update(Player p) {//Aqui ele atualiza os valores do tab para os players
        Tab tabb = Main.getTab();
        if(tabb.getTabList(p) != null) {
            PersonalizadaTabList tab = (PersonalizadaTabList) tabb.getTabList(p);
            int index = 1;
            ((TableTabList) tab).set(0, 0, new TextTabItem(" §e§lFriends  ", 0));
            ((TableTabList) tab).set(1, 0, new TextTabItem(" §f§lGlobal  " , 0));
            ((TableTabList) tab).set(2, 0, new TextTabItem(" §a§lParty  " , 0));
            ((TableTabList) tab).set(3, 0, new TextTabItem(" §e§lGuild  ", 0));
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (index == 20) {
                    int Total = (Bukkit.getOnlinePlayers().size() - 20);
                    ((TableTabList) tab).set(1, 0,
                            new TextTabItem(" §f§lGlobal " + ChatColor.WHITE + "[" + Total + "] "));
                    break;
                }
                ((TableTabList) tab).set(1, index, new PlayerTabItem(player));
                for(int y = 1+Bukkit.getOnlinePlayers().size(); y < 20 ; y++) {
                    ((TableTabList) tab).set(1, y, new TextTabItem("§e§l ", 0));
                }
                index++;
            }
            tab.update();
        }
    }

    public void simpleupdate() {//:D
        Bukkit.getOnlinePlayers().forEach(this::update);
    }
}
