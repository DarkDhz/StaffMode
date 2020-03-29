package dev.darkhorizon.es.sm.commands;

import com.earth2me.essentials.IUser;
import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.config.Perms;
import dev.darkhorizon.es.sm.items.Items;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Staff implements CommandExecutor {

    private final Main plugin = Main.getPlugin(Main.class);
    private final Items item = Items.getInstance();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(Perms.main_premission)) {
                this.manageCommand(p, args);
            } else {
                p.sendMessage(Lang.no_prem);
            }
        }
        return false;
    }

    private void manageCommand(Player p, String [] args) {
        if (args.length == 0) {
            manageStaffMode(p);
        } else {
            p.sendMessage(Lang.staff_usage);
        }
    }

    private void manageStaffMode(Player p) {
        if (plugin.staff_players.contains(p.getName())) {
            plugin.staff_players.remove(p.getName());
            plugin.staff_inventory.remove(p.getName());
            // Rehab players inventory
            item.rehabInventory(p);

            p.sendMessage(Lang.diabledStaffMode);
        } else {
            plugin.staff_players.add(p.getName());
            plugin.staff_inventory.put(p.getName(), p.getInventory().getContents());

            // Get essentials user
            IUser user = plugin.ess.getUser(p);
            // Enable vanish for user
            user.setVanished(true);

            item.setInventory(p);
            p.sendMessage(Lang.enabledStaffMode);
        }
    }


}
