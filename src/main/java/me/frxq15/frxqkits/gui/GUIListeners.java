package me.frxq15.frxqkits.gui;

import me.frxq15.frxqkits.gui.menus.KitMenu;
import me.frxq15.frxqkits.gui.menus.PreviewKit;
import me.frxq15.frxqkits.gui.menus.PurchaseMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class GUIListeners implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        UUID inventoryUUID = PreviewKit.openInventories.get(playerUUID);
        if (inventoryUUID != null) {
            e.setCancelled(true);
            GUITemplate gui = GUITemplate.getInventoriesByUUID().get(inventoryUUID);
            PreviewKit.GUIAction action = gui.getActions().get(e.getSlot());
            if(e.getClickedInventory() != player.getOpenInventory().getTopInventory()) return;
            if (action != null) {
                action.click(player);
            }
        }
    }
    @EventHandler
    public void onPurchaseClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        UUID inventoryUUID = PurchaseMenu.openInventories.get(playerUUID);
        if (inventoryUUID != null) {
            e.setCancelled(true);
            GUITemplate gui = GUITemplate.getInventoriesByUUID().get(inventoryUUID);
            PurchaseMenu.GUIAction action = gui.getActions().get(e.getSlot());
            if(e.getClickedInventory() != player.getOpenInventory().getTopInventory()) return;
            if (action != null) {
                action.click(player);
            }
        }
    }
    @EventHandler
    public void kitmenu(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        UUID inventoryUUID = KitMenu.openInventories.get(playerUUID);
        if (inventoryUUID != null) {
            e.setCancelled(true);
            GUITemplate gui = GUITemplate.getInventoriesByUUID().get(inventoryUUID);
            KitMenu.GUIAction left_action = gui.getLeftActions().get(e.getSlot());
            KitMenu.GUIAction right_action = gui.getRightActions().get(e.getSlot());
            KitMenu.GUIAction middle_action = gui.getMiddleActions().get(e.getSlot());
            if(e.getClickedInventory() != player.getOpenInventory().getTopInventory()) return;
            if(e.isLeftClick()) {
                if (left_action != null) {
                    left_action.click(player);
                    return;
                }
            }
            if(e.isRightClick()) {
                if (right_action != null) {
                    right_action.click(player);
                    return;
                }
            }
            if(e.getClick().equals(ClickType.MIDDLE)) {
                if (middle_action != null) {
                    middle_action.click(player);
                }
            }
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        UUID inventoryUUID = PreviewKit.openInventories.get(playerUUID);
        if (inventoryUUID != null) {
            PreviewKit.openInventories.remove(playerUUID);
            return;
        }
        UUID inventoryUUID2 = KitMenu.openInventories.get(playerUUID);
        if (inventoryUUID2 != null) {
            KitMenu.openInventories.remove(playerUUID);
            return;
        }
        UUID inventoryUUID3 = PurchaseMenu.openInventories.get(playerUUID);
        if (inventoryUUID3 != null) {
            PurchaseMenu.openInventories.remove(playerUUID);
            return;
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if(PreviewKit.openInventories.containsKey(playerUUID)) {
            PreviewKit.openInventories.remove(playerUUID);
        }
        if(KitMenu.openInventories.containsKey(playerUUID)) {
            KitMenu.openInventories.remove(playerUUID);
        }
        if(PurchaseMenu.openInventories.containsKey(playerUUID)) {
            PurchaseMenu.openInventories.remove(playerUUID);
        }
    }
}
