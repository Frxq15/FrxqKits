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

public class PurchaseMenu extends GUITemplate {
    private FrxqKits plugin;
    private Player p;
    private String kit;

    public PurchaseMenu(FrxqKits plugin, Player p, String kit) {
        super(plugin, plugin.getConfig().getInt("GUIS.PURCHASE_GUI.ROWS"),
                FrxqKits.colourize(plugin.getConfig().getString("GUIS.PURCHASE_GUI.TITLE").replace("%kit%", kit)));
        this.plugin = plugin;
        this.p = p;
        this.kit = kit;
        initialize();
    }
    public void initialize() {
        setItem(plugin.getConfig().getInt("GUIS.PURCHASE_GUI.ITEMS.KIT.SLOT"), createKitItem("KIT", kit));
        plugin.getConfig().getStringList("GUIS.PURCHASE_GUI.ITEMS.CONFIRM.SLOTS").forEach(slot -> {
            setItem(Integer.valueOf(slot), createPane("CONFIRM", kit, "CONFIRM"), player -> {
                player.getOpenInventory().close();
                player.sendMessage(FrxqKits.formatMsg("KIT_PURCHASED").replace("%kit%", kit)
                        .replace("%cost%", plugin.getConfig().getInt("GUIS.KIT_GUI.ITEMS."+kit.toUpperCase()+".PURCHASE_COST")+""));
                String command = plugin.getConfig().getString("ADD_KIT_COMMAND").replace("%kit%", kit).replace("%player%", p.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                plugin.getApiManager().getEconomy().bankWithdraw(p.getName(), plugin.getConfig().getInt("GUIS.KIT_GUI.ITEMS."+kit.toUpperCase()+".PURCHASE_COST"));
            });
        });
        plugin.getConfig().getStringList("GUIS.PURCHASE_GUI.ITEMS.CANCEL.SLOTS").forEach(slot -> {
            setItem(Integer.valueOf(slot), createPane("CANCEL", kit, "CANCEL"), player -> {
                player.getOpenInventory().close();
            });
        });
    }
    public boolean hasGlow(String item) {
        return plugin.getConfig().getBoolean("GUIS.PURCHASE_GUI.ITEMS."+item+".GLOW");
    }
    ItemStack createKitItem(String item, String kit) {
        //normal item creation
        List<String> lore = new ArrayList<String>();
        String material = plugin.getConfig().getString("GUIS.PURCHASE_GUI.ITEMS."+item+".MATERIAL");
        if(material.equals("%kititem%")) {
            material = plugin.getConfig().getString("GUIS.KIT_GUI.ITEMS."+kit.toUpperCase()+".LOCKED.MATERIAL");
        }
        String description = plugin.getConfig().getString("GUIS.KIT_GUI.ITEMS."+kit.toUpperCase()+".LOCKED"+".DESCRIPTION");
        int cost = plugin.getConfig().getInt("GUIS.KIT_GUI.ITEMS."+kit.toUpperCase()+".PURCHASE_COST");
        Integer amount = plugin.getConfig().getInt("GUIS.PURCHASE_GUI.ITEMS."+item+".AMOUNT");
        final ItemStack i = new ItemStack(Material.valueOf(material), amount);
        String name = plugin.getConfig().getString("GUIS.PURCHASE_GUI.ITEMS."+item+".NAME").replace("%kit%", kit)
                .replace("%kitname%", plugin.getConfig().getString("GUIS.KIT_GUI.ITEMS."+kit.toUpperCase()+".LOCKED"+".NAME"));

        final ItemMeta meta = i.getItemMeta();
        for (String lines : plugin.getConfig().getStringList("GUIS.PURCHASE_GUI.ITEMS."+item+".LORE")) {
            lines = lines.replace("%kit%", kit).replace("%description%", description).replace("%cost%", cost+"")
                    .replace("%kitname", plugin.getConfig().getString("GUIS.KIT_GUI.ITEMS."+kit.toUpperCase()+".LOCKED"+".NAME"));
            lore.add(FrxqKits.colourize(lines));
        }
        meta.setDisplayName(FrxqKits.colourize(name));
        if (hasGlow(item)) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.setLore(lore);
        i.setItemMeta(meta);
        return i;
    }
    ItemStack createPane(String item, String kit, String confirm) {
        //normal item creation
        List<String> lore = new ArrayList<String>();
        Integer value = new Integer(FrxqKits.getInstance().getConfig().getInt("GUIS.PURCHASE_GUI.ITEMS."+confirm+".DATA"));
        String material = plugin.getConfig().getString("GUIS.PURCHASE_GUI.ITEMS."+confirm+".MATERIAL");
        String description = plugin.getConfig().getString("GUIS.KIT_GUI.ITEMS."+kit.toUpperCase()+".LOCKED"+".DESCRIPTION");
        int cost = plugin.getConfig().getInt("GUIS.KIT_GUI.ITEMS."+kit.toUpperCase()+".PURCHASE_COST");
        Integer amount = plugin.getConfig().getInt("GUIS.PURCHASE_GUI.ITEMS."+confirm+".AMOUNT");
        final ItemStack i = new ItemStack(Material.valueOf(material), amount, value.shortValue());
        String name = plugin.getConfig().getString("GUIS.PURCHASE_GUI.ITEMS."+confirm+".NAME").replace("%kit%", kit)
                .replace("%kitname", plugin.getConfig().getString("GUIS.KIT_GUI.ITEMS."+kit.toUpperCase()+".LOCKED"+".NAME"));

        final ItemMeta meta = i.getItemMeta();
        for (String lines : plugin.getConfig().getStringList("GUIS.PURCHASE_GUI.ITEMS."+confirm+".LORE")) {
            lines = lines.replace("%kit%", kit).replace("%description%", description).replace("%cost%", cost+"")
                    .replace("%kitname", plugin.getConfig().getString("GUIS.KIT_GUI.ITEMS."+kit.toUpperCase()+".LOCKED"+".NAME"));
            lore.add(FrxqKits.colourize(lines));
        }
        meta.setDisplayName(FrxqKits.colourize(name));
        if (hasGlow(item)) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.setLore(lore);
        i.setItemMeta(meta);
        return i;
    }
}
