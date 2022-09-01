package me.frxq15.frxqkits.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KitManager {
    public void selectKit(Player p, String kit) {
        p.getOpenInventory().close();
        Bukkit.dispatchCommand(p, "kit "+kit);
    }
}
