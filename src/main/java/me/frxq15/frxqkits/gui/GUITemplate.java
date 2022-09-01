package me.frxq15.frxqkits.gui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.frxq15.frxqkits.FrxqKits;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUITemplate {
    public static Map<UUID, GUITemplate> inventoriesByUUID = new HashMap<>();
    public static Map<UUID, UUID> openInventories = new HashMap<>();
    private final FrxqKits plugin;
    private final int rows;
    private final String title;

    private final Inventory inventory;
    private Map<Integer, GUIAction> actions;
    private Map<Integer, GUIAction> left_actions;
    private Map<Integer, GUIAction> right_actions;
    private Map<Integer, GUIAction> middle_actions;
    private UUID uuid;

    public GUITemplate(FrxqKits plugin, int rows, String title) {
        uuid = UUID.randomUUID();
        this.plugin = plugin;
        this.rows = rows;
        this.title = title;
        inventory = Bukkit.createInventory(null, 9 * rows, FrxqKits.colourize(title));
        actions = new HashMap<>();
        left_actions = new HashMap<>();
        right_actions = new HashMap<>();
        middle_actions = new HashMap<>();
        inventoriesByUUID.put(getUUID(), this);
    }

    public interface GUIAction {
        void click(Player player);
    }

    public UUID getUUID() {
        return uuid;
    }

    public static Map<UUID, GUITemplate> getInventoriesByUUID() {
        return inventoriesByUUID;
    }

    public static Map<UUID, UUID> getOpenInventories() {
        return openInventories;
    }

    public void open(Player player) {
        player.openInventory(inventory);
        openInventories.put(player.getUniqueId(), getUUID());
    }
    public void delete() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID u = openInventories.get(p.getUniqueId());
            if (u.equals(getUUID())) {
                p.closeInventory();
            }
        }
        inventoriesByUUID.remove(getUUID());
    }

    public Map<Integer, GUIAction> getActions() {
        return actions;
    }
    public Map<Integer, GUIAction> getLeftActions() { return left_actions; }
    public Map<Integer, GUIAction> getRightActions() { return right_actions; }
    public Map<Integer, GUIAction> getMiddleActions() { return middle_actions; }

    public void setItem(int slot, ItemStack stack, GUIAction action) {
        inventory.setItem(slot, stack);
        if (action != null) {
            actions.put(slot, action);
        }
    }
    public void setKitItem(int slot, ItemStack stack, GUIAction left_action, GUIAction right_action, GUIAction middle_action) {
        inventory.setItem(slot, stack);
        if (left_action != null) {
            left_actions.put(slot, left_action);
        }
        if (right_action != null) {
            right_actions.put(slot, right_action);
        }
        if (middle_action != null) {
            middle_actions.put(slot, middle_action);
        }
    }
    public void setLeftActions(int slot, GUIAction left_action) {
        if (left_action != null) {
            left_actions.put(slot, left_action);
        }
    }
    public void setRightActions(int slot, GUIAction right_action) {
        if (right_action != null) {
            right_actions.put(slot, right_action);
        }
    }
    public void setMiddleActions(int slot, GUIAction middle_action) {
        if (middle_action != null) {
            middle_actions.put(slot, middle_action);
        }
    }

    public void setItem(int slot, ItemStack stack) {
        setItem(slot, stack, null);
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    protected ItemStack createGlowingGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
}
