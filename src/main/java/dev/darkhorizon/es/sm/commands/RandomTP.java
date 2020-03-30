package dev.darkhorizon.es.sm.commands;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.config.Perms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class RandomTP implements CommandExecutor {

    private final Main plugin = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(Perms.tp_permission)) {
                this.manageCommand(p, args);
            } else {
                p.sendMessage(Lang.no_prem);
            }
        }
        return true;
    }

    private void manageCommand(Player p, String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!pl.hasPermission(Perms.main_permission)) {
                players.add(pl);
            }
        }
        Random r = new Random();
        int max = players.size();
        int result = r.nextInt(max + 1);
        p.teleport(players.get(result));
        p.sendMessage(Lang.teleport_msg.replaceAll("%player", players.get(result).getName()));
    }
}
