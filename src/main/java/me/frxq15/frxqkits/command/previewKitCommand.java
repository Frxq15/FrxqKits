package me.frxq15.frxqkits.command;

import me.frxq15.frxqkits.FrxqKits;
import me.frxq15.frxqkits.gui.menus.PreviewKit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class previewKitCommand implements CommandExecutor, TabCompleter {
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
            String nameCapitalized = kit.substring(0, 1).toUpperCase() + kit.substring(1);
            new PreviewKit(FrxqKits.getInstance(), p, nameCapitalized).open(p);
            return true;
        }
        p.sendMessage(FrxqKits.colourize("&cUsage: /previewkit <kit>"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            FrxqKits.getInstance().getFileManager().getKitsFile().getKeys(false).forEach(kit -> {
                if (sender.hasPermission("frxqkits.kit." + kit.toLowerCase())) {
                    kit = kit.toLowerCase();
                    String newKit = kit.substring(0, 1).toUpperCase() + kit.substring(1);
                    completions.add(newKit);
                }
            });
            return completions;
        }
        return null;
    }
}
