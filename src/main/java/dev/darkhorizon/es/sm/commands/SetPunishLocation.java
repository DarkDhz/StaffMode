package dev.darkhorizon.es.sm.commands;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.FileManger;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.config.Perms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPunishLocation implements CommandExecutor {

    private FileManger fm = FileManger.getInstance();
    private final Main plugin = Main.getPlugin(Main.class);
    private Lang lang = Lang.getInstance();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            manageCommand((Player) sender);
        }
        return false;
    }

    private void manageCommand(Player launcher) {
        if (launcher.hasPermission(Perms.loc_permission)) {
            launcher.sendMessage(lang.prefix + "Has establecido la localizacion de SS en tu posicion.");
            fm.getConfig().set("punish.world", launcher.getLocation().getWorld().getName());
            fm.getConfig().set("punish.x", launcher.getLocation().getX());
            fm.getConfig().set("punish.y", launcher.getLocation().getY());
            fm.getConfig().set("punish.z", launcher.getLocation().getZ());
        } else {
            launcher.sendMessage(lang.no_prem);
        }
    }

}
