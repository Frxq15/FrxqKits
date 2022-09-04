package me.frxq15.frxqkits.command;

import me.frxq15.frxqkits.FrxqKits;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class deleteKitCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if(!s.hasPermission("frxqkits.deletekit")) {
            s.sendMessage(FrxqKits.formatMsg("NO_PERMISSION"));
            return true;
        }
        if(args.length == 1) {
            String kit = args[0];
            if(!FrxqKits.getInstance().getFileManager().getKitsFile().isConfigurationSection(kit.toUpperCase())) {
                s.sendMessage(FrxqKits.formatMsg("KIT_NOT_FOUND").replace("%kit%", kit));
                return true;
            }
            FrxqKits.getInstance().getFileManager().getKitsFile().set(kit.toUpperCase(), null);
            FrxqKits.getInstance().getFileManager().saveKitsFile();
            s.sendMessage(FrxqKits.formatMsg("KIT_DELETED").replace("%kit%", kit.substring(0, 1).toUpperCase() + kit.substring(1)));
            return true;
        }
        s.sendMessage(FrxqKits.colourize("&cUsage: /deletekit <kit>"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String alias, String[] args) {
        if(args.length == 1) {
            List<String> completions = new ArrayList<>();
            FrxqKits.getInstance().getFileManager().getKitsFile().getKeys(false).forEach(kit -> {
                kit = kit.toLowerCase();
                String k = kit.substring(0, 1).toUpperCase() + kit.substring(1);
                completions.add(k);
            });
            return completions;
        }
        return null;
    }
}
