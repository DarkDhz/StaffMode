package dev.darkhorizon.es.sm.gui;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class StaffListGUI {

    //private final Main plugin = Main.getPlugin(Main.class);
    private final Items item = Items.getInstance();
    private Lang lang = Lang.getInstance();

    public StaffListGUI(Player p) {
        this.generateInventory(p);
    }

    private void generateInventory(Player p) {
        Inventory inv = Bukkit.createInventory(p, 54, lang.stafflist_GUI_title);
        int count = 0;
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (count <= 53 && staff != null) {
                inv.setItem(count, item.getListStaff(staff));
                count++;
            }
        }
        p.openInventory(inv);
    }
}
