package me.frxq15.frxqkits.command;

import me.frxq15.frxqkits.FrxqKits;
import me.frxq15.frxqkits.gui.menus.PreviewKit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class previewKitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            FrxqKits.log("This command cannot be executed from console.");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("frxqkits.previewkit")) {
            p.sendMessage(FrxqKits.formatMsg("NO_PERMISSION"));
            return true;
        }
        if(args.length == 1) {
            String kit = args[0];
            if(!FrxqKits.getInstance().getFileManager().getKitsFile().isConfigurationSection(kit.toUpperCase())) {
                p.sendMessage(FrxqKits.formatMsg("KIT_NOT_FOUND").replace("%kit%", kit));
                return true;
            }
            String k1 = kit.substring(0, 1).toUpperCase();
            String nameCapitalized = k1 + kit.substring(1);
            new PreviewKit(FrxqKits.getInstance(), p, nameCapitalized).open(p);
            return true;
        }
        p.sendMessage(FrxqKits.colourize("&cUsage: /previewkit <kit>"));
        return true;
    }
}
