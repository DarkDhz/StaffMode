package dev.darkhorizon.es.sm.config;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.items.Items;
import dev.darkhorizon.es.sm.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Lang {

    //private final Main plugin = Main.getPlugin(Main.class);
    private FileManger fm = FileManger.getInstance();
    private static Lang INSTANCE = null;

    private Lang() {
        //TODO Singleton for only 1 object instance
    }

    public static Lang getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized(Lang.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Lang();
                }
            }
        }
    }

    public String prefix = fm.getLang().getString("messages.prefix").replaceAll("&", "§");

    // GLOBAL
    public String no_prem = Utils.simpleMessageReplace("messages.global.no_perm", prefix, fm.getLang());
    public String offline_player = Utils.simpleMessageReplace("messages.global.offline_player", prefix, fm.getLang());
    public String invalid_player = Utils.simpleMessageReplace("messages.global.invalid_player", prefix, fm.getLang());

    // Events Related

    public String staff_no_drop = Utils.simpleMessageReplace("messages.events.advises.drop", prefix, fm.getLang());
    public String staff_no_place = Utils.simpleMessageReplace("messages.events.advises.place", prefix, fm.getLang());
    public String staff_no_break = Utils.simpleMessageReplace("messages.events.advises.break", prefix, fm.getLang());
    public String staff_no_pvp = Utils.simpleMessageReplace("messages.events.advises.pvp", prefix, fm.getLang());
    public String staff_spawner_break = Utils.simpleMessageReplace("messages.events.advises.spawner_event", prefix, fm.getLang());

    // /freeze retaled

    public String frozen_pvp_msg = Utils.simpleMessageReplace("messages.events.freeze.on_pvp", prefix, fm.getLang());
    public String freeze_cmd_usage = Utils.simpleMessageReplace("messages.commands.freeze.usage", prefix, fm.getLang());
    public String freeze_cmd_player_freezed = Utils.simpleMessageReplace("messages.commands.freeze.freezed", prefix, fm.getLang());
    public String freeze_cmd_player_unfreezed = Utils.simpleMessageReplace("messages.commands.freeze.unfreezed", prefix, fm.getLang());
    public String freeze_cmd_target_unfreezed = Utils.simpleMessageReplace("messages.commands.freeze.target_unfreezed", prefix, fm.getLang());
    public List<String> frozen_ss_msg = Utils.simpleListMessageReplace("messages.commands.freeze.ss_msg", prefix, fm.getLang());
    public ItemStack frozen_item = new ItemStack(Material.PACKED_ICE);

    // /staff related

    public String staff_usage = Utils.simpleMessageReplace("messages.commands.staff.usage", prefix, fm.getLang());
    public String enabledStaffMode = Utils.simpleMessageReplace("messages.commands.staff.enable", prefix, fm.getLang());
    public String diabledStaffMode = Utils.simpleMessageReplace("messages.commands.staff.disable", prefix, fm.getLang());

    // /teleport related

    public String teleport_msg = Utils.simpleMessageReplace("messages.commands.playertp.teleport_to", prefix, fm.getLang());
    public String teleport_invalid_msg = Utils.simpleMessageReplace("messages.commands.playertp.teleport_invalid", prefix, fm.getLang());

    // /examine related
    public String examine_usage = Utils.simpleMessageReplace("messages.commands.examine.usage", prefix, fm.getLang());
   
    // /punish related
    public String punish_usage = Utils.simpleMessageReplace("messages.commands.punish.usage", prefix, fm.getLang());


    // HOTBAR

    // FREEZE

    public int freeze_slot = fm.getHotbar().getInt("hotbar.freeze.slot");
    public ItemStack freeze_item = new ItemStack(Material.getMaterial(fm.getHotbar().getInt("hotbar.freeze.item")));
    public String freeze_title = Utils.simpleMessageReplace("hotbar.freeze.title", prefix, fm.getHotbar());
    public List<String> freeze_lore = Utils.simpleListMessageReplace("hotbar.freeze.lore", prefix, fm.getHotbar());

    // RANDOM TP

    public int random_slot = fm.getHotbar().getInt("hotbar.random.slot");
    public ItemStack random_item = new ItemStack(Material.getMaterial(fm.getHotbar().getInt("hotbar.random.item")));
    public String random_title = Utils.simpleMessageReplace("hotbar.random.title", prefix, fm.getHotbar());
    public List<String> random_lore = Utils.simpleListMessageReplace("hotbar.random.lore", prefix, fm.getHotbar());

    // STAFF LIST

    public int slist_slot = fm.getHotbar().getInt("hotbar.stafflist.slot");
    public String slist_title = Utils.simpleMessageReplace("hotbar.stafflist.title", prefix, fm.getHotbar());
    public List<String> slist_lore = Utils.simpleListMessageReplace("hotbar.stafflist.lore", prefix, fm.getHotbar());

    // EXAMINE

    public int examine_slot = fm.getHotbar().getInt("hotbar.examine.slot");
    public ItemStack examine_item = new ItemStack(Material.getMaterial(fm.getHotbar().getInt("hotbar.examine.item")));
    public String examine_title = Utils.simpleMessageReplace("hotbar.examine.title", prefix, fm.getHotbar());
    public List<String> examine_lore = Utils.simpleListMessageReplace("hotbar.examine.lore", prefix, fm.getHotbar());

    // VANISH

    public int vanish_slot = fm.getHotbar().getInt("hotbar.vanish.slot");
    public ItemStack vanish_item_enabled = new ItemStack(Material.getMaterial(fm.getHotbar().getInt("hotbar.vanish.item_enabled")));
    public ItemStack vanish_item_disabled = new ItemStack(Material.getMaterial(fm.getHotbar().getInt("hotbar.vanish.item_disabled")));
    public String vanish_title = Utils.simpleMessageReplace("hotbar.vanish.title", prefix, fm.getHotbar());
    public String vanish_title_active = Utils.simpleMessageReplace("hotbar.vanish.state_enabled", prefix, fm.getHotbar());
    public String vanish_title_unactive = Utils.simpleMessageReplace("hotbar.vanish.state_disabled", prefix, fm.getHotbar());
    public List<String> vanish_lore = Utils.simpleListMessageReplace("hotbar.vanish.lore", prefix, fm.getHotbar());

    // END OF HOTBAR

    // EXAMINE GUI
    public String examine_GUI_title_vis = fm.getGUI().getString("gui.examine.view_title");
    public String examine_GUI_title_edit = fm.getGUI().getString("gui.examine.edit_title");

    // USER INFO
    public ItemStack user_info_item = new ItemStack(Material.SKULL_ITEM);
    public String user_info_title = "%player";
    public String user_info_enabled = "ON";
    public String user_info_disabled = "OFF";
    public ArrayList<String> generateUserInfoLore(String god, String fly, String h, Location loc, String w) {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§1");
        toReturn.add(Utils.userReplace("§7Vida: %health/20", god, fly, h, loc, w));
        toReturn.add(Utils.userReplace("§7Location: %x %y %z", god, fly, h, loc, w));
        toReturn.add(Utils.userReplace("§7Mundo: %world", god, fly, h, loc, w));
        toReturn.add(Utils.userReplace("§7God: %god", god, fly, h, loc, w));
        toReturn.add(Utils.userReplace("§7Fly: %fly", god, fly, h, loc, w));
        toReturn.add("§2");
        return toReturn;
    }

    // TELEPORT
    public ItemStack ex_teleport_item = new ItemStack(Material.COMPASS);
    public String ex_teleport_title = "Ir a donde esta el jugador";
    public ArrayList<String> generateExTeleportLore() {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§7Clic para ir");
        return toReturn;
    }

    // SEPARATOR

    public ItemStack separator_item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
    public String separator_title = "EXAMINANDO...";

    // END OF EXAMINE GUI

    // STAFFLIST GUI

    public String stafflist_GUI_title = "STAFFLIST";


    // PLAYER HEAD
    public String lstaff_title = "§1§l%player";
    public ArrayList<String> generatLStaffLore(Player p) {
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add("§1");
        toReturn.add(Utils.slistItemReplace("§7Posición:", p));
        toReturn.add(Utils.slistItemReplace("§7X: %x", p));
        toReturn.add(Utils.slistItemReplace("§7Y: %y", p));
        toReturn.add(Utils.slistItemReplace("§7Z: %z", p));
        toReturn.add(Utils.slistItemReplace("§7Mundo: %world", p));
        toReturn.add("§7");
        return toReturn;
    }

    // END OF STAFFLIST GUI



    // PUNISH GUI
}
