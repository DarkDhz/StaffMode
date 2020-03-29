package dev.darkhorizon.es.sm;

import com.earth2me.essentials.Essentials;
import dev.darkhorizon.es.sm.commands.Staff;
import dev.darkhorizon.es.sm.events.SEventsListener;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin {


    public HashMap<String, ItemStack[]> staff_inventory = null;
    public ArrayList<String> staff_players = null;
    public ArrayList<String> frozen = null;
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
        staff_players = new ArrayList<String>();
        staff_inventory = new HashMap<String, ItemStack[]>();
        frozen = new ArrayList<String>();
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

