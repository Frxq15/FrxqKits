package me.frxq15.frxqkits.command;

import me.frxq15.frxqkits.FrxqKits;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class kitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if(!s.hasPermission("frxqkits.createkit")) {
            s.sendMessage(FrxqKits.formatMsg("NO_PERMISSION"));
            return true;
        }
        FileConfiguration file = FrxqKits.getInstance().getFileManager().getKitsFile();
        if(args.length == 0) {
            if(!(s instanceof Player)) {
                FrxqKits.log("This command cannot be executed from console.");
                return true;
            }
            //open gui
            return true;
        }
        if(args.length == 1) {
            if(!(s instanceof Player)) {
                FrxqKits.log("This command cannot be executed from console.");
                return true;
            }
            Player p = (Player) s;
            String kit = args[0];
            if(!file.isConfigurationSection(kit.toUpperCase())) {
                s.sendMessage(FrxqKits.formatMsg("KIT_NOT_FOUND").replace("%kit%", kit));
                return true;
            }
            int cooldown = file.getInt(kit.toUpperCase()+".COOLDOWN");
            long c = cooldown;
            FrxqKits.getInstance().getFileManager().getCooldownFile().set(p.getName() + "."+kit.toUpperCase(), c);
            FrxqKits.getInstance().getFileManager().saveCooldownFile();

            if(file.getBoolean(kit.toUpperCase()+".GIVE_BY_SLOT")) {
                file.getConfigurationSection(kit.toUpperCase()+".ITEMS").getKeys(false).forEach(slot -> {
                    ItemStack item = file.getItemStack(kit.toUpperCase()+".ITEMS."+slot);
                    p.getInventory().setItem(Integer.valueOf(slot), item);
                });
                return true;
            }
            file.getConfigurationSection(kit.toUpperCase()+".ITEMS").getKeys(false).forEach(slot -> {
                ItemStack item = file.getItemStack(kit.toUpperCase()+".ITEMS."+slot);
                p.getInventory().addItem(item);
            });
            s.sendMessage(FrxqKits.formatMsg("KIT_RECEIVED").replace("%kit%", kit));
            return true;
        }
        if(args.length == 2) {
            String kit = args[0];
            String target = args[1];
            if(Bukkit.getPlayer(target) == null) {
                s.sendMessage(FrxqKits.formatMsg("PLAYER_NOT_FOUND"));
                return true;
            }
            if(!file.isConfigurationSection(kit.toUpperCase())) {
                s.sendMessage(FrxqKits.formatMsg("KIT_NOT_FOUND").replace("%kit%", kit));
                return true;
            }
            Player p = Bukkit.getPlayer(target);
            if(file.getBoolean(kit.toUpperCase()+".GIVE_BY_SLOT")) {
                file.getConfigurationSection(kit.toUpperCase()+".ITEMS").getKeys(false).forEach(slot -> {
                    ItemStack item = file.getItemStack(kit.toUpperCase()+".ITEMS."+slot);
                    p.getInventory().setItem(Integer.valueOf(slot), item);
                });
                return true;
            }
            file.getConfigurationSection(kit.toUpperCase()+".ITEMS").getKeys(false).forEach(slot -> {
                ItemStack item = file.getItemStack(kit.toUpperCase()+".ITEMS."+slot);
                p.getInventory().addItem(item);
            });
            s.sendMessage(FrxqKits.formatMsg("KIT_GIVEN").replace("%kit%", kit).replace("%player%", p.getName()));
            return true;
        }
        if(!(s instanceof Player)) {
            FrxqKits.log("This command cannot be executed from console.");
            return true;
        }
        s.sendMessage(FrxqKits.colourize("&cUsage: /kit <kit>"));
        return true;
    }
}
