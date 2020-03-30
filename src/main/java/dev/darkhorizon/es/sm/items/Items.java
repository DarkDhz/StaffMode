package dev.darkhorizon.es.sm.items;

import com.earth2me.essentials.IUser;
import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.data.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Items {

    private static final Main plugin = Main.getPlugin(Main.class);
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
            synchronized(Items.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Items();
                }
            }
        }
    }

    public void rehabInventory(Player p) {
        p.getInventory().clear();
        p.getInventory().setContents(Data.staff_inv.get(p.getName()));
    }

    public void setInventory(Player p) {
        p.getInventory().clear();
        p.getInventory().setItem(Lang.freeze_slot, this.getFrezze());
        p.getInventory().setItem(Lang.random_slot, this.getRandom());
        p.getInventory().setItem(Lang.vanish_slot, this.getVanish(p));
        p.getInventory().setItem(Lang.examine_slot, this.getExamine());
        p.getInventory().setItem(Lang.slist_slot, this.getHead(p));
    }

    private ItemStack getVanish(Player p) {
        ItemStack item = Lang.vanish_item;
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Lang.vanish_lore);
        String title = Lang.vanish_title;
        IUser user = plugin.ess.getUser(p);
        if (user.isVanished()) {
            title = title.replaceAll("%state", Lang.vanish_title_active);
        } else {
            title = title.replaceAll("%state", Lang.vanish_title_unactive);
        }
        meta.setDisplayName(title);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getExamine() {
        ItemStack item = Lang.examine_item;
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Lang.examine_lore);
        meta.setDisplayName(Lang.examine_title);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getFrezze() {
        ItemStack item = Lang.freeze_item;
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Lang.freeze_lore);
        meta.setDisplayName(Lang.freeze_title);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getRandom() {
        ItemStack item = Lang.random_item;
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Lang.random_lore);
        meta.setDisplayName(Lang.random_title);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getHead(Player p) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta sm = (SkullMeta) item.getItemMeta();
        sm.setOwner(p.getName());
        sm.setDisplayName(Lang.slist_title);
        sm.setLore(Lang.slist_lore);
        item.setItemMeta(sm);
        return item;
    }
}
