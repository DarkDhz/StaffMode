package dev.darkhorizon.es.sm;

import com.earth2me.essentials.Essentials;
import dev.darkhorizon.es.sm.commands.*;
import dev.darkhorizon.es.sm.events.SEventsListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public Essentials ess = null;

    private FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        super.onEnable();
        this.initConfig();
        this.initVariables();
        this.initCommands();
        this.initEvents();
    }

    @Override
    public void onDisable() {
        super.onDisable();

    }

    private void initConfig() {
        this.config.options().copyDefaults(true);
        saveConfig();
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
    }

    private void initEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new SEventsListener(), this);
    }


}

