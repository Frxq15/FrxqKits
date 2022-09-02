package me.frxq15.frxqkits.api;

import me.frxq15.frxqkits.FrxqKits;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

public class APIManager {
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;


    public Economy getEconomy() {
        return econ;
    }
    public Permission getPermissions() {
        return perms;
    }
    public Chat getChat() {
        return chat;
    }
    private boolean setupEconomy() {
        if (FrxqKits.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = FrxqKits.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = FrxqKits.getInstance().getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = FrxqKits.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    public void runSetup() {
        if (!setupEconomy()) {
            FrxqKits.getInstance().log("Plugin disabled due to no Vault dependency found.");
            FrxqKits.getInstance().getServer().getPluginManager().disablePlugin(FrxqKits.getInstance());
            return;
        }
        setupPermissions();
        setupChat();
        FrxqKits.getInstance().log("Hooked into VaultAPI successfully");
    }
}
