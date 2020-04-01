package dev.darkhorizon.es.sm.commands;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.config.Perms;
import dev.darkhorizon.es.sm.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Freeze implements CommandExecutor {

    private final Main plugin = Main.getPlugin(Main.class);
    private Lang lang = Lang.getInstance();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(Perms.freeze_permission)) {
                this.manageCommand(p, args);
            } else {
                p.sendMessage(lang.no_prem);
            }
        }
        return true;
    }

    private void manageCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.sendMessage(lang.freeze_cmd_usage);
            return;
        }
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(lang.offline_player.replaceAll("5player", args[0]));
                return;
            } else {
                if (target.isOp()) {
                    p.sendMessage("INVALID PLAYER");
                    return;
                }
                if (Data.frozen.contains(target.getName())) {
                    Data.frozen.remove(target.getName());
                    p.sendMessage("EL JUGADOR YA NO ESTA EN REVISION");
                    target.sendMessage("Ya no estas en revision");
                } else {
                    Data.frozen.add(target.getName());
                    p.sendMessage("HAS CONEGELADO A UN USUARIO");
                    final Location pLoc = target.getLocation();

                    (new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (Data.frozen.contains(target.getName())) {
                                for (String msg : lang.frozen_ss_msg()) {
                                    target.sendMessage(msg);
                                }
                            } else {
                                cancel();
                            }
                        }
                    }).runTaskTimerAsynchronously(plugin, 0L, 100L);
                    (new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (Data.frozen.contains(target.getName())) {
                                if (!target.getLocation().equals(pLoc)) {
                                    target.teleport(pLoc);
                                }
                            } else {
                                cancel();
                            }
                        }
                    }).runTaskTimerAsynchronously(plugin, 0L, 20L);

                }
            }
        }
    }
}
