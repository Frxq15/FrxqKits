package me.frxq15.frxqkits;

import me.frxq15.frxqkits.api.APIManager;
import me.frxq15.frxqkits.command.createKitCommand;
import me.frxq15.frxqkits.command.kitCommand;
import me.frxq15.frxqkits.command.previewKitCommand;
import me.frxq15.frxqkits.gui.GUIListeners;
import me.frxq15.frxqkits.manager.CooldownManager;
import me.frxq15.frxqkits.manager.FileManager;
import me.frxq15.frxqkits.manager.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class FrxqKits extends JavaPlugin {
    private static FrxqKits instance;
    public FileManager fileManager;
    public CooldownManager cooldownManager;
    public KitManager kitManager;
    public APIManager apiManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        initialize();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        log("Plugin disabled.");
        // Plugin shutdown logic
    }
    public void initialize() {
        fileManager = new FileManager(this);
        cooldownManager = new CooldownManager();
        kitManager = new KitManager();
        apiManager = new APIManager();
        getFileManager().createKitsFile();
        getFileManager().createCooldownFile();
        getApiManager().runSetup();
        getCommand("createkit").setExecutor(new createKitCommand());
        getCommand("kit").setExecutor(new kitCommand());
        getCommand("kit").setTabCompleter(new kitCommand());
        getCommand("previewkit").setExecutor(new previewKitCommand());
        getCommand("previewkit").setTabCompleter(new previewKitCommand());
        Bukkit.getPluginManager().registerEvents(new GUIListeners(), this);
        log("Plugin enabled successfully.");
    }
    public static FrxqKits getInstance() { return instance; }
    public static String colourize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
    public static List<String> colourize(List<String> input) {
        List<String> newList = new ArrayList<>();
        for(String entry : input) {
            newList.add(colourize(entry));
        }
        return newList;
    }
    public static void log(String str) { Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE+"[FrxqKits] "+str); }
    public static String formatMsg(String input) {  return ChatColor.translateAlternateColorCodes('&', getInstance().getConfig().getString(input)); }

    public FileManager getFileManager() {
        return fileManager;
    }
    public CooldownManager getCooldownManager() { return cooldownManager; }
    public KitManager getKitManager() { return kitManager; }
    public APIManager getApiManager() { return apiManager; }

}
