package com.pvpin.gyhhy.jsrunntime;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.Vault;
import org.bukkit.plugin.Plugin;
import org.bukkit.Server;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.OfflinePlayer;
public class VaultAPI {
    private static Economy economy;
    private static PVPIN plugin(){return PVPIN.plugin;}
    private static Vault vault = null;
    public static void onEnable(){
        Server server = plugin().getServer();
        Plugin v = server.getPluginManager().getPlugin("Vault");
        if (v instanceof Vault){vault = (Vault)v;} else{vault = null;}
        RegisteredServiceProvider<Economy> economyProvider = plugin().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        } else {economy = null;}
    }
    public PVPIN getPlugin(){
        return PVPIN.plugin;
    }
    private VaultAPI(){}
    public static EconomyResponse give(OfflinePlayer op,double count){
        if (economy!=null){
            EconomyResponse response = economy.depositPlayer(op, count);
            return response;
        }
        return null;
    }
    public static EconomyResponse take(OfflinePlayer op,double count){
        if (economy!=null){
            EconomyResponse response = economy.withdrawPlayer(op, count);
            return response;
        }
        return null;
    }
    public static Economy getEconmoy(){return economy;}
    public static double getBalance(OfflinePlayer op){
        if (economy!=null){
            return economy.getBalance(op);
        }
        return Double.NaN;
    }
}