package me.frxq15.frxqkits.command;

import me.frxq15.frxqkits.FrxqKits;
import me.frxq15.frxqkits.gui.menus.KitMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class kitCommand implements CommandExecutor, TabCompleter {
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
            Player p = (Player) s;
            new KitMenu(FrxqKits.getInstance(), p).open(p);
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
                s.sendMessage(FrxqKits.formatMsg("KIT_NOT_FOUND").replace("%kit%", kit.substring(0, 1).toUpperCase() + kit.substring(1)));
                return true;
            }
            long getpc = 0;
            if(FrxqKits.getInstance().getFileManager().getCooldownFile().get(p.getName()+"."+kit.toUpperCase()) != null) {
                getpc = FrxqKits.getInstance().getFileManager().getCooldownFile().getLong(p.getName() + "."+kit.toUpperCase());
            }
            long current = System.currentTimeMillis();
            long c = file.getLong(kit.toUpperCase()+".COOLDOWN") * 1000;
            if((current <= getpc &&(!p.hasPermission("frxqkits.cooldownbypass")))) {
                p.sendMessage(FrxqKits.formatMsg("KIT_ON_COOLDOWN")
                        .replace("%cooldown%", FrxqKits.getInstance().getCooldownManager().getPrettyTime((getpc - current))+""));
                return true;
            }
            FrxqKits.getInstance().getFileManager().getCooldownFile().set(p.getName() + "."+kit.toUpperCase(), (c+current));
            FrxqKits.getInstance().getFileManager().saveCooldownFile();

            if(file.getBoolean(kit.toUpperCase()+".GIVE_BY_SLOT")) {
                if(file.getConfigurationSection(kit.toUpperCase()+".ITEMS") != null) {
                    file.getConfigurationSection(kit.toUpperCase() + ".ITEMS").getKeys(false).forEach(slot -> {
                        ItemStack item = file.getItemStack(kit.toUpperCase() + ".ITEMS." + slot);
                        p.getInventory().setItem(Integer.valueOf(slot), item);
                    });
                }
                if(file.getBoolean(kit.toUpperCase()+".AUTO_APPLY_ARMOUR")) {
                    if(p.getInventory().getHelmet() != null) {
                        p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".HELMET"));
                    }
                    if(p.getInventory().getChestplate() != null) {
                        p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".CHESTPLATE"));
                    }
                    if(p.getInventory().getLeggings() != null) {
                        p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".LEGGINGS"));
                    }
                    if(p.getInventory().getBoots() != null) {
                        p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".BOOTS"));
                    }
                    if(p.getInventory().getHelmet() == null) {
                        p.getInventory().setHelmet(file.getItemStack(kit.toUpperCase()+".HELMET"));
                    }
                    if(p.getInventory().getChestplate() == null) {
                        p.getInventory().setChestplate(file.getItemStack(kit.toUpperCase()+".CHESTPLATE"));
                    }
                    if(p.getInventory().getLeggings() == null) {
                        p.getInventory().setLeggings(file.getItemStack(kit.toUpperCase()+".LEGGINGS"));
                    }
                    if(p.getInventory().getBoots() == null) {
                        p.getInventory().setBoots(file.getItemStack(kit.toUpperCase()+".BOOTS"));
                    }
                }
                s.sendMessage(FrxqKits.formatMsg("KIT_GIVEN")
                        .replace("%kit%", kit.substring(0, 1).toUpperCase() + kit.substring(1)).replace("%player%", p.getName()));
                return true;
            }
            if(file.getConfigurationSection(kit.toUpperCase()+".ITEMS") != null) {
                file.getConfigurationSection(kit.toUpperCase() + ".ITEMS").getKeys(false).forEach(slot -> {
                    ItemStack item = file.getItemStack(kit.toUpperCase() + ".ITEMS." + slot);
                    p.getInventory().addItem(item);
                });
            }
            if(file.getBoolean(kit.toUpperCase()+".AUTO_APPLY_ARMOUR")) {
                if(p.getInventory().getHelmet() != null) {
                    p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".HELMET"));
                }
                if(p.getInventory().getChestplate() != null) {
                    p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".CHESTPLATE"));
                }
                if(p.getInventory().getLeggings() != null) {
                    p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".LEGGINGS"));
                }
                if(p.getInventory().getBoots() != null) {
                    p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".BOOTS"));
                }
                if(p.getInventory().getHelmet() == null) {
                    p.getInventory().setHelmet(file.getItemStack(kit.toUpperCase()+".HELMET"));
                }
                if(p.getInventory().getChestplate() == null) {
                    p.getInventory().setChestplate(file.getItemStack(kit.toUpperCase()+".CHESTPLATE"));
                }
                if(p.getInventory().getLeggings() == null) {
                    p.getInventory().setLeggings(file.getItemStack(kit.toUpperCase()+".LEGGINGS"));
                }
                if(p.getInventory().getBoots() == null) {
                    p.getInventory().setBoots(file.getItemStack(kit.toUpperCase()+".BOOTS"));
                }
            }
            s.sendMessage(FrxqKits.formatMsg("KIT_RECEIVED").replace("%kit%", kit.substring(0, 1).toUpperCase() + kit.substring(1)));
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
                if(file.getBoolean(kit.toUpperCase()+".AUTO_APPLY_ARMOUR")) {
                    if(p.getInventory().getHelmet() != null) {
                        p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".HELMET"));
                    }
                    if(p.getInventory().getChestplate() != null) {
                        p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".CHESTPLATE"));
                    }
                    if(p.getInventory().getLeggings() != null) {
                        p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".LEGGINGS"));
                    }
                    if(p.getInventory().getBoots() != null) {
                        p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".BOOTS"));
                    }
                    if(p.getInventory().getHelmet() == null) {
                        p.getInventory().setHelmet(file.getItemStack(kit.toUpperCase()+".HELMET"));
                    }
                    if(p.getInventory().getChestplate() == null) {
                        p.getInventory().setChestplate(file.getItemStack(kit.toUpperCase()+".CHESTPLATE"));
                    }
                    if(p.getInventory().getLeggings() == null) {
                        p.getInventory().setLeggings(file.getItemStack(kit.toUpperCase()+".LEGGINGS"));
                    }
                    if(p.getInventory().getBoots() == null) {
                        p.getInventory().setBoots(file.getItemStack(kit.toUpperCase()+".BOOTS"));
                    }
                }
                s.sendMessage(FrxqKits.formatMsg("KIT_GIVEN")
                        .replace("%kit%", kit.substring(0, 1).toUpperCase() + kit.substring(1)).replace("%player%", p.getName()));
                return true;
            }
            if(file.getConfigurationSection(kit.toUpperCase()+".ITEMS") != null) {
                file.getConfigurationSection(kit.toUpperCase() + ".ITEMS").getKeys(false).forEach(slot -> {
                    ItemStack item = file.getItemStack(kit.toUpperCase() + ".ITEMS." + slot);
                    p.getInventory().addItem(item);
                });
            }
            if(file.getBoolean(kit.toUpperCase()+".AUTO_APPLY_ARMOUR")) {
                if(p.getInventory().getHelmet() != null) {
                    p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".HELMET"));
                }
                if(p.getInventory().getChestplate() != null) {
                    p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".CHESTPLATE"));
                }
                if(p.getInventory().getLeggings() != null) {
                    p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".LEGGINGS"));
                }
                if(p.getInventory().getBoots() != null) {
                    p.getInventory().addItem(file.getItemStack(kit.toUpperCase()+".BOOTS"));
                }
                if(p.getInventory().getHelmet() == null) {
                    p.getInventory().setHelmet(file.getItemStack(kit.toUpperCase()+".HELMET"));
                }
                if(p.getInventory().getChestplate() == null) {
                    p.getInventory().setChestplate(file.getItemStack(kit.toUpperCase()+".CHESTPLATE"));
                }
                if(p.getInventory().getLeggings() == null) {
                    p.getInventory().setLeggings(file.getItemStack(kit.toUpperCase()+".LEGGINGS"));
                }
                if(p.getInventory().getBoots() == null) {
                    p.getInventory().setBoots(file.getItemStack(kit.toUpperCase()+".BOOTS"));
                }
            }
            s.sendMessage(FrxqKits.formatMsg("KIT_GIVEN")
                    .replace("%kit%", kit.substring(0, 1).toUpperCase() + kit.substring(1)).replace("%player%", p.getName()));
            return true;
        }
        if(!(s instanceof Player)) {
            FrxqKits.log("This command cannot be executed from console.");
            return true;
        }
        s.sendMessage(FrxqKits.colourize("&cUsage: /kit <kit>"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if(args.length == 1) {
            FrxqKits.getInstance().getFileManager().getKitsFile().getKeys(false).forEach(kit -> {
                if(sender.hasPermission("frxqkits.kit."+kit.toLowerCase())) {
                    kit = kit.toLowerCase();
                    String newKit = kit.substring(0, 1).toUpperCase() + kit.substring(1);
                    completions.add(newKit);
                }
            });
            return completions;
        }
        if(args.length == 2) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                completions.add(player.getName());
            });
            return completions;
        }
        return null;
    }
}
