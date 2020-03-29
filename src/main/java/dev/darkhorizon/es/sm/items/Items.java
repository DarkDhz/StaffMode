package dev.darkhorizon.es.sm.items;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Items {

    private final Main plugin = Main.getPlugin(Main.class);
    private static Items INSTANCE = null;

    private Items() {
        //TODO Singleton for only 1 object instance
    }

    public static Items getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized(Main.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Items();
                }
            }
        }
    }

    public void rehabInventory(Player p, Inventory inv) {
        p.getInventory().clear();
        for (int i = 0; i < inv.getSize(); i++) {
            p.getInventory().setItem(i, inv.getItem(i));
        }
    }

    public void setInventory(Player p) {

        p.getInventory().clear();

        ItemStack item = new ItemStack(Material.ICE);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add("para congelar al jugador!");
        meta.setLore(lore);
        meta.setDisplayName(Lang.freeze_title);
        item.setItemMeta(meta);
        lore.clear();
        ItemStack skull = getHead(p);
        ItemStack item3 = new ItemStack(Material.CHEST);
        ItemMeta meta3 = item3.getItemMeta();
        lore.add("para examinar al jugador!");
        meta3.setLore(lore);
        meta3.setDisplayName("");
        item3.setItemMeta(meta3);
        lore.clear();
        ItemStack item4 = new ItemStack(Material.COMPASS);
        ItemMeta meta4 = item4.getItemMeta();
        lore.add("para teletransportarte aleatoriamente!");
        meta4.setLore(lore);
        meta4.setDisplayName("TP");
        item4.setItemMeta(meta4);
        lore.clear();
        p.getInventory().setItem(4, item3);
        p.getInventory().setItem(0, item);
        p.getInventory().setItem(1, item4);
        p.getInventory().setItem(8, skull);
    }

    private ItemStack getHead(Player p) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta sm = (SkullMeta)item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add("para ver al staff!");
        sm.setOwner(p.getName());
        sm.setDisplayName("DE STAFF");
        sm.setLore(lore);
        item.setItemMeta((ItemMeta)sm);
        return item;
    }
}
