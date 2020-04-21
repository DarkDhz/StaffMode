package dev.darkhorizon.es.sm;

import com.earth2me.essentials.Essentials;
import dev.darkhorizon.es.sm.commands.*;
import dev.darkhorizon.es.sm.config.FileManger;
import dev.darkhorizon.es.sm.data.Data;
import dev.darkhorizon.es.sm.events.SEventsListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public Essentials ess = null;


    @Override
    public void onEnable() {
        super.onEnable();
        FileManger.initFiles();
        this.initVariables();
        this.initCommands();
        this.initEvents();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        for (String name : Data.staff_players) {
            Player p = Bukkit.getPlayer(name);
            if (p != null) {
                p.getInventory().setContents(Data.staff_inv.get(name));
            }
        }
        for (String name : Data.freeze_helmet.keySet()) {
            Player p = Bukkit.getPlayer(name);
            if (p != null) {
                p.getInventory().setHelmet(Data.freeze_helmet.get(name));
            }
        }

    }


    private void initVariables() {
        ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
    }

    private void initCommands() {
        this.getCommand("staff").setExecutor(new Staff());
        this.getCommand("examine").setExecutor(new Examine());
        this.getCommand("freeze").setExecutor(new Freeze());
        this.getCommand("stafflist").setExecutor(new StaffList());
        this.getCommand("playertp").setExecutor(new RandomTP());
        this.getCommand("punish").setExecutor(new Punish());
    }

    private void initEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new SEventsListener(), this);
    }


}

