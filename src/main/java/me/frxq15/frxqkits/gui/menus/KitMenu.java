package me.frxq15.frxqkits.gui.menus;

import me.frxq15.frxqkits.FrxqKits;
import me.frxq15.frxqkits.gui.GUITemplate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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
        for (String item : plugin.getConfig().getConfigurationSection("GUIS.KIT_GUI.ITEMS").getKeys(false)) {
            int slot = plugin.getConfig().getInt("GUIS.KIT_GUI.ITEMS." + item + ".SLOT");
            String kit = plugin.getConfig().getString("GUIS.KIT_GUI.ITEMS." + item + ".KIT_NAME").toLowerCase();
            String nameCapitalized = kit.substring(0, 1).toUpperCase() + kit.substring(1);
            if (p.hasPermission("frxqkits.kit." + kit)) {
                setItem(slot, createItem(item, nameCapitalized, "UNLOCKED"));
                setLeftActions(slot, l -> {
                    l.getOpenInventory().close();
                    FrxqKits.getInstance().getKitManager().selectKit(p, nameCapitalized);
                });
                setMiddleActions(slot, m -> {
                    m.getOpenInventory().close();
                    Bukkit.broadcastMessage("upgrade kit");
                });
                setRightActions(slot, r -> {
                    new PreviewKit(FrxqKits.getInstance(), r, nameCapitalized).open(p);
                });
            }
            if (!p.hasPermission("frxqkits.kit." + kit)) {
                setItem(slot, createItem(item, nameCapitalized, "LOCKED"));
                setMiddleActions(slot, m -> {
                    m.getOpenInventory().close();
                    Bukkit.broadcastMessage("purchase kit");
                });
                setRightActions(slot, r-> {
                    new PreviewKit(FrxqKits.getInstance(), r, nameCapitalized).open(p);
                });
            }
        }
    }
    public boolean hasGlow(String item, String status) {
        return plugin.getConfig().getBoolean("GUIS.KIT_GUI.ITEMS."+item+"."+status.toUpperCase()+".GLOW");
    }
    ItemStack createItem(String item, String kit, String status) {
        //normal item creation
        List<String> lore = new ArrayList<String>();
        String material = plugin.getConfig().getString("GUIS.KIT_GUI.ITEMS."+item+"."+status.toUpperCase()+".MATERIAL");
        Integer amount = plugin.getConfig().getInt("GUIS.KIT_GUI.ITEMS."+item+"."+status.toUpperCase()+".AMOUNT");
        final ItemStack i = new ItemStack(Material.valueOf(material), amount);
        String name = plugin.getConfig().getString("GUIS.KIT_GUI.ITEMS."+item+"."+status.toUpperCase()+".NAME");

        final ItemMeta meta = i.getItemMeta();
        for (String lines : plugin.getConfig().getStringList("GUIS.KIT_GUI.ITEMS."+item+"."+status.toUpperCase()+".LORE")) {
            lines = lines.replace("%kit%", kit);
            lore.add(FrxqKits.colourize(lines));
        }
        meta.setDisplayName(FrxqKits.colourize(name));
        if (hasGlow(item, status)) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.setLore(lore);
        i.setItemMeta(meta);
        return i;
    }
}
