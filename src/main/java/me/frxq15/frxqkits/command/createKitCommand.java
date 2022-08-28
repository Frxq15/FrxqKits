package me.frxq15.frxqkits.command;

import me.frxq15.frxqkits.FrxqKits;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class createKitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            FrxqKits.log("This command cannot be executed from console.");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("frxqkits.createkit")) {
            p.sendMessage(FrxqKits.formatMsg("NO_PERMISSION"));
            return true;
        }
        if(args.length == 2) {
            String kit = args[0];
            Integer cooldown;
            try {
                cooldown = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                p.sendMessage(FrxqKits.formatMsg("INVALID_COOLDOWN"));
                return true;
            }
            if(FrxqKits.getInstance().getFileManager().getKitsFile().isConfigurationSection(kit.toUpperCase())) {
                p.sendMessage(FrxqKits.formatMsg("KIT_ALREADY_EXISTS"));
                return true;
            }
            if(cooldown < 0) {
                p.sendMessage(FrxqKits.formatMsg("INVALID_COOLDOWN"));
                return true;
            }
            //create kit
            FileConfiguration file = FrxqKits.getInstance().getFileManager().getKitsFile();
            file.set(kit.toUpperCase() + ".COOLDOWN", cooldown);
            file.set(kit.toUpperCase() + ".GIVE_BY_SLOT", false);
            Map<Integer, ItemStack> items = new HashMap<>();
            Inventory inventory = p.getInventory();

            for (int i = 0; i < 36; i++) {
                ItemStack itemStack = inventory.getItem(i);
                if (itemStack == null) continue;
                if (itemStack.getType() == Material.AIR) continue;
                items.put(i, itemStack);
            }
            items.forEach((slot, item) -> {
                file.set(kit.toUpperCase()+".ITEMS."+slot, item);
            });
            FrxqKits.getInstance().getFileManager().saveKitsFile();
            p.sendMessage(FrxqKits.formatMsg("KIT_CREATED").replace("%kit%", kit).replace("%cooldown%", cooldown+""));
            return true;
        }
        p.sendMessage(FrxqKits.colourize("&cUsage: /createkit <kit> <cooldown>"));
        return true;
    }
}
