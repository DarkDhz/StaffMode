package dev.darkhorizon.es.sm.commands;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.FileManger;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.config.Perms;
import dev.darkhorizon.es.sm.data.Data;
import dev.darkhorizon.es.sm.gui.ExamineGUI;
import dev.darkhorizon.es.sm.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class Examine  implements CommandExecutor {

    private final Main plugin = Main.getPlugin(Main.class);
    private Lang lang = Lang.getInstance();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(Perms.examine_permission)) {
                this.manageCommand(p, args);
            } else {
                p.sendMessage(lang.no_prem);
            }
        }
        return true;
    }

    private void manageCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.sendMessage(lang.examine_usage);
            return;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase(p.getName())) {
                p.sendMessage(lang.invalid_player);
                return;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(lang.offline_player.replaceAll("%player", args[0]));
                return;
            }
            if (target.isOp() || target.hasPermission(Perms.main_permission)) {
                p.sendMessage(lang.invalid_player.replaceAll("%player", args[0]));
                return;
            }
            if (Data.frozen.contains(target.getName())) {
                if (p.hasPermission(Perms.examine_edit_permission)) {
                    ExamineGUI ex = new ExamineGUI(p, target, true);
                } else {
                    ExamineGUI ex = new ExamineGUI(p, target, false);
                }
            } else {
                ExamineGUI ex = new ExamineGUI(p, target, false);
            }
        }
    }
}
