package dev.darkhorizon.es.sm.commands;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.config.Perms;
import dev.darkhorizon.es.sm.gui.PunishGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Punish implements CommandExecutor {

    private final Main plugin = Main.getPlugin(Main.class);
    private Lang lang = Lang.getInstance();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(Perms.punish_permission)) {
                this.manageCommand(p, args);
            } else {
                p.sendMessage(lang.no_prem);
            }
        }
        return true;
    }

    private void manageCommand(Player p, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase(p.getName())) {
                p.sendMessage(lang.invalid_player);
                return;
            }
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (target == null) {
                p.sendMessage(lang.no_exist.replaceAll("%player", args[0]));
                return;
            } else {
                PunishGUI gui = new PunishGUI(p, target.getName(), PunishGUI.gui_type.MAIN);
            }

        } else {
            p.sendMessage(lang.punish_usage);
        }

    }


}
