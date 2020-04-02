package dev.darkhorizon.es.sm.gui;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.items.Items;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PunishGUI {

    private final Main plugin = Main.getPlugin(Main.class);
    private final Items item = Items.getInstance();
    private Lang lang = Lang.getInstance();

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
        Inventory inv = Bukkit.createInventory(p, 6*9, "SANCIONES PRINCIPAL" + target.getName());
        List<String> lore = new ArrayList<>();
        lore.add("§7Clic para ir!");
        inv.setItem(0, this.generateBanItem("SANCIONES", lore));
        lore = new ArrayList<>();
        lore.add("§7Clic para ir!");
        inv.setItem(1, this.generateBanItem("ABUELAS", lore));
        p.openInventory(inv);
    }

    public void generateSubInventory(Player p, Player target) {
        Inventory inv = Bukkit.createInventory(p, 3*9, "TITLE" + target.getName());

        p.openInventory(inv);
    }

    public ItemStack generateBanItem(String title, List<String> lore) {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(title);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

}
