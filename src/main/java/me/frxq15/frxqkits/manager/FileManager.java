package me.frxq15.frxqkits.manager;

import me.frxq15.frxqkits.FrxqKits;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private final FrxqKits plugin;
    public File KitsFile;
    public FileConfiguration KitsConfig;
    public File CooldownFile;
    public FileConfiguration CooldownConfig;
    public FileManager(FrxqKits plugin) {
        this.plugin = plugin;
    }

    public void createKitsFile() {
        KitsFile = new File(plugin.getInstance().getDataFolder(), "kits.yml");
        if (!KitsFile.exists()) {
            KitsFile.getParentFile().mkdirs();
            plugin.log("kits.yml was created successfully");
            plugin.getInstance().saveResource("kits.yml", false);
        }

        KitsConfig = new YamlConfiguration();
        try {
            KitsConfig.load(KitsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public void reloadKitsFile() { KitsConfig = YamlConfiguration.loadConfiguration(KitsFile); }
    public void saveKitsFile() {
        try {
            KitsConfig.save(KitsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getKitsFile() { return KitsConfig; }
    public void createCooldownFile() {
        CooldownFile = new File(plugin.getInstance().getDataFolder(), "cooldowns.yml");
        if (!CooldownFile.exists()) {
            CooldownFile.getParentFile().mkdirs();
            plugin.log("cooldowns.yml was created successfully");
            plugin.getInstance().saveResource("cooldowns.yml", false);
        }

        CooldownConfig = new YamlConfiguration();
        try {
            CooldownConfig.load(CooldownFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public void reloadCooldownFile() { CooldownConfig = YamlConfiguration.loadConfiguration(CooldownFile); }
    public void saveCooldownFile() {
        try {
            CooldownConfig.save(CooldownFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getCooldownFile() { return CooldownConfig; }
}
