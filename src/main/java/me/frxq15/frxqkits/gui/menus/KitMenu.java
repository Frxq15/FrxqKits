package me.frxq15.frxqkits.gui.menus;

import me.frxq15.frxqkits.FrxqKits;
import me.frxq15.frxqkits.gui.GUITemplate;
import org.bukkit.entity.Player;

public class KitMenu extends GUITemplate {
    private final FrxqKits plugin;
    private final Player p;
    public KitMenu(FrxqKits plugin, Player p) {
        super(plugin, plugin.getConfig().getInt("GUIS.KIT_GUI.ROWS"),
                FrxqKits.colourize(plugin.getConfig().getString("GUIS.KIT_GUI.TITLE")));
        this.plugin = plugin;
        this.p = p;
        initialize();
    }
    public void initialize() {
        plugin.getConfig().getConfigurationSection("GUIS.KIT_GUI.ITEMS").getKeys(false).forEach(item -> {
            int slot = plugin.getConfig().getInt("GUIS.KIT_GUI.ITEMS."+item+".SLOT");
        });
    }
}
