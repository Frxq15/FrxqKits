package me.frxq15.frxqkits;

import me.frxq15.frxqkits.command.createKitCommand;
import me.frxq15.frxqkits.command.kitCommand;
import me.frxq15.frxqkits.command.previewKitCommand;
import me.frxq15.frxqkits.gui.GUIListeners;
import me.frxq15.frxqkits.manager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class FrxqKits extends JavaPlugin {
    private static FrxqKits instance;
    public FileManager fileManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        initialize();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void initialize() {
        fileManager = new FileManager(this);
        getFileManager().createKitsFile();
        getFileManager().createCooldownFile();
        getCommand("createkit").setExecutor(new createKitCommand());
        getCommand("kit").setExecutor(new kitCommand());
        getCommand("previewkit").setExecutor(new previewKitCommand());
        Bukkit.getPluginManager().registerEvents(new GUIListeners(), this);
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

}
