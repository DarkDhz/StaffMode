package dev.darkhorizon.es.sm.commands;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.config.Perms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Freeze implements CommandExecutor {

    private final Main plugin = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(Perms.freeze_permission)) {
                this.manageCommand(p, args);
            } else {
                p.sendMessage(Lang.no_prem);
            }
        }
        return true;
    }

    private void manageCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.sendMessage("USAGE");
            return;
        }
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage("offline");
                return;
            }
        }
    }
}
