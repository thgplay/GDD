package discord.gdd.Utils;
/*
Criado por MrD4rkBr
*/

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    public Vault(){
        setupEconomy();
    }

    public Economy economy = null;

    private boolean setupEconomy() {

        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();

        }

        return economy != null;

    }

    public double getMoney(OfflinePlayer p) {
        return economy.getBalance(p);
    }

    public void setMoney(OfflinePlayer p, Double valor) {
        economy.bankWithdraw(p.getName(), getMoney(p));
        economy.bankDeposit(p.getName(), valor);
    }

    public void removeMoney(OfflinePlayer p, Double valor) {
        economy.bankWithdraw(p.getName(), valor);
    }

    public void addMoney(OfflinePlayer p, Double valor) {
        economy.bankDeposit(p.getName(), valor);
    }

    public boolean hasMoney(OfflinePlayer p, Double valor) {
        if (getMoney(p) < valor) {
            return true;
        } else {
            return false;
        }
    }

    public void sendmoney(OfflinePlayer enviou, OfflinePlayer recebeu, Double valor) {
        removeMoney(enviou, valor);
        addMoney(recebeu, valor);
    }
}

