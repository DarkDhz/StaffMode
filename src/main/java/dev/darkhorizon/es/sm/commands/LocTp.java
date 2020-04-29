package dev.darkhorizon.es.sm.commands;

import dev.darkhorizon.es.sm.config.Perms;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocTp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player launcher = (Player) commandSender;
            if (commandSender.hasPermission(Perms.main_permission)) {
                if (strings.length == 3) {

                    //launcher.teleport(new Location(launcher.getWorld(), (int[]) strings[0]));
                }
            }
        }
        return true;
    }
}
