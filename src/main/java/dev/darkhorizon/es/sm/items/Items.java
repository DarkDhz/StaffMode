package dev.darkhorizon.es.sm.items;

import com.earth2me.essentials.IUser;
import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.data.Data;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Items {

    private static final Main plugin = Main.getPlugin(Main.class);
    private static Items INSTANCE = null;
    private Lang lang = Lang.getInstance();

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
        p.getInventory().setItem(lang.vanish_slot, this.getVanish(p, state));
        p.updateInventory();
    }

    public void setInventory(Player p) {

        p.getInventory().clear();
        p.getInventory().setItem(lang.freeze_slot, this.getFrezze());
        p.getInventory().setItem(lang.random_slot, this.getRandom());
        p.getInventory().setItem(lang.vanish_slot, this.getVanish(p, plugin.ess.getUser(p).isVanished()));
        p.getInventory().setItem(lang.examine_slot, this.getExamine());
        p.getInventory().setItem(lang.slist_slot, this.getHead(p));
        p.getInventory().setItem(2, this.getFly(p, true));
        p.updateInventory();
    }


    private ItemStack getFly(Player p, boolean state) {
        ItemStack item = new ItemStack(Material.FEATHER);


        ItemMeta meta = item.getItemMeta();
        if (state) {
            meta.addEnchant(Enchantment.DURABILITY, 0, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.setLore(lang.vanish_lore);
        String title = "FLY CHETAO (%state)";

        if (state) {
            title = title.replaceAll("%state", lang.vanish_title_active);
        } else {
            title = title.replaceAll("%state", lang.vanish_title_unactive);
        }
        meta.setDisplayName(title);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getVanish(Player p, boolean state) {
        ItemStack item = lang.vanish_item_enabled;
        if (!state) {
            item = lang.vanish_item_disabled;
        }

        ItemMeta meta = item.getItemMeta();
        meta.setLore(lang.vanish_lore);
        String title = lang.vanish_title;

        if (state) {
            title = title.replaceAll("%state", lang.vanish_title_active);
        } else {
            title = title.replaceAll("%state", lang.vanish_title_unactive);
        }
        meta.setDisplayName(title);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getExamine() {
        ItemStack item = lang.examine_item;
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lang.examine_lore);
        meta.setDisplayName(lang.examine_title);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getFrezze() {
        ItemStack item = lang.freeze_item;
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lang.freeze_lore);
        meta.setDisplayName(lang.freeze_title);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getRandom() {
        ItemStack item = lang.random_item;
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lang.random_lore);
        meta.setDisplayName(lang.random_title);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getHead(Player p) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta sm = (SkullMeta) item.getItemMeta();
        sm.setOwner(p.getName());
        sm.setDisplayName(lang.slist_title);
        sm.setLore(lang.slist_lore);
        item.setItemMeta(sm);
        return item;
    }

    public ItemStack getSeparator() {
        ItemStack item = lang.separator_item;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(lang.separator_title);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getInfoItem(Player target) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(target.getName());
        IUser user = plugin.ess.getUser(target);

        String god = lang.user_info_disabled;
        if (user.isGodModeEnabled()) {
            god = lang.user_info_enabled;
        }

        String fly = lang.user_info_disabled;
        if (target.isFlying()) {
            fly = lang.user_info_enabled;
        }

        meta.setDisplayName(lang.user_info_title.replaceAll("%player", target.getName()));
        meta.setLore(lang.generateUserInfoLore(god, fly, "" + target.getHealth(), target.getLocation(), target.getWorld().getName()));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getExTeleportItem() {
        ItemStack item = lang.ex_teleport_item;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(lang.ex_teleport_title);
        meta.setLore(lang.ex_teleport_lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getListStaff(Player p) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta sm = (SkullMeta) item.getItemMeta();
        sm.setOwner(p.getName());
        sm.setDisplayName(lang.lstaff_title.replaceAll("%player", p.getName()));
        sm.setLore(lang.generateLStaffLore(p));
        item.setItemMeta(sm);
        return item;

    }


}
