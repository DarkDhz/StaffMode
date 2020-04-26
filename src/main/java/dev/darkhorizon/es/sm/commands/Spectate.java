package dev.darkhorizon.es.sm.commands;

import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.config.Perms;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spectate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            manageCommand((Player) commandSender, strings);
        }
        return true;
    }

    private void manageCommand(Player launcher, String[] strings) {
        if (launcher.hasPermission(Perms.spec_permission)) {
            if (launcher.getGameMode() == GameMode.SURVIVAL) {
                launcher.setGameMode(GameMode.SPECTATOR);
                launcher.sendMessage(Lang.getInstance().prefix + " §fNuevo modo de juego: §6Espectador");
            } else {
                launcher.setGameMode(GameMode.SURVIVAL);
                launcher.sendMessage(Lang.getInstance().prefix + " §fNuevo modo de juego: §6Survival");
            }
        }
    }
}
