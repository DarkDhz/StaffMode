package dev.darkhorizon.es.sm.gui;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PunishGUI {

    private final Main plugin = Main.getPlugin(Main.class);
    private final Items item = Items.getInstance();

    public enum gui_type {MAIN, SUB, CUSTOM}

    public PunishGUI(Player p, Player target, gui_type e) {
        switch (e) {
            case MAIN:
                this.generateInventory(p, target);
                break;
            case SUB:
                break;
            case CUSTOM:
                break;
        }
        this.generateInventory(p, target);
    }

    private void generateInventory(Player p, Player target) {
        Inventory inv = Bukkit.createInventory(p, 6*9, "TITLE" + target.getName());

        p.openInventory(inv);
    }

    public void generateSubInventory(Player p, Player target) {
        Inventory inv = Bukkit.createInventory(p, 3*9, "TITLE" + target.getName());

        p.openInventory(inv);
    }

}
