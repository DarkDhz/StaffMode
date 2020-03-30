package dev.darkhorizon.es.sm;

import com.earth2me.essentials.Essentials;
import dev.darkhorizon.es.sm.commands.Staff;
import dev.darkhorizon.es.sm.events.SEventsListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin {

    public Essentials ess = null;

    @Override
    public void onEnable() {
        super.onEnable();
        this.initVariables();
        this.initCommands();
        this.initEvents();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void initVariables() {
        ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
    }

    private void initCommands() {
        this.getCommand("staff").setExecutor(new Staff());
    }

    private void initEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new SEventsListener(), this);
    }


}

