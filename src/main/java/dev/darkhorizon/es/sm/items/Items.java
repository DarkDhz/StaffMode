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

import java.util.ArrayList;
import java.util.List;

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
        p.updateInventory();
    }

    public void updateVanish(Player p, boolean state) {
        p.getInventory().setItem(Lang.vanish_slot, this.getVanish(p, state));
        p.updateInventory();
    }

    public void setInventory(Player p) {

        p.getInventory().clear();
        p.getInventory().setItem(Lang.freeze_slot, this.getFrezze());
        p.getInventory().setItem(Lang.random_slot, this.getRandom());
        p.getInventory().setItem(Lang.vanish_slot, this.getVanish(p, plugin.ess.getUser(p).isVanished()));
        p.getInventory().setItem(Lang.examine_slot, this.getExamine());
        p.getInventory().setItem(Lang.slist_slot, this.getHead(p));
        p.updateInventory();
    }

    private ItemStack getVanish(Player p, boolean state) {
        ItemStack item = Lang.vanish_item_enabled;
        if (!state) {
            item = Lang.vanish_item_disabled;
        }

        ItemMeta meta = item.getItemMeta();
        meta.setLore(Lang.generateVanishLore());
        String title = Lang.vanish_title;

        if (state) {
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
        meta.setLore(Lang.generateExamineLore());
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
        meta.setLore(Lang.generateRandomLore());
        meta.setDisplayName(Lang.random_title);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getHead(Player p) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta sm = (SkullMeta) item.getItemMeta();
        sm.setOwner(p.getName());
        sm.setDisplayName(Lang.slist_title);
        sm.setLore(Lang.generateRandomLore());
        item.setItemMeta(sm);
        return item;
    }

    public ItemStack getSeparator() {
        ItemStack item = Lang.separator_item;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Lang.separator_title);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getInfoItem(Player target) {
        ItemStack item = Lang.user_info_item;
        ItemMeta meta = item.getItemMeta();
        IUser user = plugin.ess.getUser(target);

        String god = Lang.user_info_disabled;
        if (user.isGodModeEnabled()) {
            god = Lang.user_info_enabled;
        }

        String fly = Lang.user_info_disabled;
        if (target.isFlying()) {
            fly = Lang.user_info_enabled;
        }

        String loc = "X: " + target.getLocation().getBlockX() + " Y: " + target.getLocation().getBlockY()
                + " Z: " + target.getLocation().getBlockZ();

        meta.setDisplayName(Lang.user_info_title.replaceAll("%player", target.getName()));


        meta.setLore(Lang.generateUserInfoLore(god, fly, "" + target.getHealth(), loc, target.getWorld().getName()));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getExTeleportItem() {
        ItemStack item = Lang.ex_teleport_item;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Lang.ex_teleport_title);
        meta.setLore(Lang.generateExTeleportLore());
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getListStaff(Player p) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta sm = (SkullMeta) item.getItemMeta();
        sm.setOwner(p.getName());
        sm.setDisplayName(Lang.lstaff_title.replaceAll("%player", p.getName()));
        sm.setLore(Lang.generatLStaffLore(p));
        item.setItemMeta(sm);
        return item;

    }


}
