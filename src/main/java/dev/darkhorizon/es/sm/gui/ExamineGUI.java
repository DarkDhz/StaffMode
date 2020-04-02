package dev.darkhorizon.es.sm.gui;

import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.data.Data;
import dev.darkhorizon.es.sm.items.Items;
import dev.darkhorizon.es.sm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ExamineGUI {

    //private final Main plugin = Main.getPlugin(Main.class);
    private final Items item = Items.getInstance();
    private Lang lang = Lang.getInstance();

    public ExamineGUI(Player p, Player target, boolean edit) {
        this.generateInventory(p, target, edit);
    }

    private void generateInventory(Player p, Player target, boolean edit) {
        Inventory inv = null;
        if (edit) {
            inv = Bukkit.createInventory(p, lang.examine_GUI_title_size*9, lang.examine_GUI_title_edit + "" + target.getName());
        } else {
            inv = Bukkit.createInventory(p, lang.examine_GUI_title_size*9, lang.examine_GUI_title_vis + "" + target.getName());
        }

        ItemStack[] items = target.getInventory().getContents();
        inv.setContents(items);
        for (int i = 36; i <= 44; i++) {
            inv.setItem(i, item.getSeparator());
        }
        ItemStack[] armor = target.getInventory().getArmorContents();
        Utils.reverse(armor);
        for (int i = 0; i <= armor.length - 1; i++) {
            inv.setItem(45 + i, armor[i]);
        }
        System.out.println(Data.freeze_helmet.get(target.getName()));
        if (Data.frozen.contains(target.getName())) {
            inv.setItem(45, Data.freeze_helmet.get(target.getName()));
        }

        inv.setItem(52, item.getExTeleportItem());
        inv.setItem(53, item.getInfoItem(target));

        p.openInventory(inv);
    }
}
