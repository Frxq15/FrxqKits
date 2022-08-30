package me.frxq15.frxqkits.gui.menus;

import me.frxq15.frxqkits.FrxqKits;
import me.frxq15.frxqkits.gui.GUITemplate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PreviewKit extends GUITemplate {
    private final FrxqKits plugin;
    private final Player p;
    private final String kit;
    public PreviewKit(FrxqKits plugin, Player p, String kit) {
        super(plugin, plugin.getConfig().getInt("GUIS.PREVIEW_GUI.ROWS"),
                FrxqKits.colourize(plugin.getConfig().getString("GUIS.PREVIEW_GUI.TITLE").replace("%kit%", kit)));
        this.plugin = plugin;
        this.p = p;
        this.kit = kit;
        initialize();
    }
    public void initialize() {
        plugin.getConfig().getConfigurationSection("GUIS.PREVIEW_GUI.ITEMS").getKeys(false).forEach(item -> {
            int slot = plugin.getConfig().getInt("GUIS.PREVIEW_GUI.ITEMS."+item+".SLOT");
            if(plugin.getConfig().getBoolean("GUIS.PREVIEW_GUI.ITEMS."+item+".IS_BACK_ITEM")) {
                setItem(slot, createItem(item), player -> {
                    p.getOpenInventory().close();
                    new KitMenu(plugin, p).open(p);
                });
            }
            setItem(slot, createItem(item));
            AtomicInteger s = new AtomicInteger(0);
            plugin.getFileManager().getKitsFile().getConfigurationSection(kit.toUpperCase()+".ITEMS").getKeys(false).forEach(i -> {
                setItem(s.get(), plugin.getFileManager().getKitsFile().getItemStack(kit.toUpperCase()+".ITEMS."+i));
                s.getAndIncrement();
            });
        });
    }
    public boolean hasGlow(String item) {
        return plugin.getConfig().getBoolean("GUIS.PREVIEW_GUI.ITEMS."+item+".GLOW");
    }
    ItemStack createItem(String item) {
        //normal item creation
        List<String> lore = new ArrayList<String>();
        String material = plugin.getConfig().getString("GUIS.PREVIEW_GUI.ITEMS."+item+".MATERIAL");
        Integer amount = plugin.getConfig().getInt("GUIS.PREVIEW_GUI.ITEMS."+item+".AMOUNT");
        final ItemStack i = new ItemStack(Material.valueOf(material), amount);
        String name = plugin.getConfig().getString("GUIS.PREVIEW_GUI.ITEMS."+item+".NAME");

        final ItemMeta meta = i.getItemMeta();
        for (String lines : plugin.getConfig().getStringList("GUIS.PREVIEW_GUI.ITEMS."+item+".LORE")) {
            lines = lines.replace("%kit%", kit);
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
