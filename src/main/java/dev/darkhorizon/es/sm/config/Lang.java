package dev.darkhorizon.es.sm.config;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.items.Items;
import dev.darkhorizon.es.sm.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Lang {

    private final Main plugin = Main.getPlugin(Main.class);
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

    public String prefix = plugin.getConfig().getString("messages.prefix").replaceAll("&", "§");

    // GLOBAL
    public String no_prem = Utils.simpleMessageReplace("messages.global.no_perm", prefix);
    public String offline_player = Utils.simpleMessageReplace("messages.global.offline_player", prefix);
    public String invalid_player = Utils.simpleMessageReplace("messages.global.invalid_player", prefix);

    // Events Related

    public String staff_no_drop = Utils.simpleMessageReplace("events.advises.drop", prefix);
    public String staff_no_place = Utils.simpleMessageReplace("events.advises.place", prefix);
    public String staff_no_break = Utils.simpleMessageReplace("events.advises.break", prefix);
    public String staff_no_pvp = Utils.simpleMessageReplace("events.advises.pvp", prefix);
    public String staff_spawner_break = Utils.simpleMessageReplace("events.advises.spawner_event", prefix);

    // /freeze retaled
    public String frozen_pvp_msg = Utils.simpleMessageReplace("events.freeze.on_pvp", prefix);
    public String freeze_cmd_usage = Utils.simpleMessageReplace("messages.commands.freeze.usage", prefix);
    public String freeze_cmd_player_freezed = Utils.simpleMessageReplace("messages.commands.freeze.freezed", prefix);
    public String freeze_cmd_player_unfreezed = Utils.simpleMessageReplace("messages.commands.freeze.unfreezed", prefix);
    public String freeze_cmd_target_unfreezed = Utils.simpleMessageReplace("messages.commands.freeze.target_unfreezed", prefix);
    public List<String> frozen_ss_msg = plugin.getConfig().getStringList("messages.commands.freeze.ss_msg");

    // /staff related

    public String staff_usage = Utils.simpleMessageReplace("messages.commands.staff.usage", prefix);
    public String enabledStaffMode = Utils.simpleMessageReplace("messages.commands.staff.enable", prefix);
    public String diabledStaffMode = Utils.simpleMessageReplace("messages.commands.staff.disable", prefix);

    // /teleport related
    public String teleport_msg = Utils.simpleMessageReplace("messages.commands.playertp.teleport_to", prefix);
    public String teleport_invalid_msg = Utils.simpleMessageReplace("messages.commands.playertp.teleport_invalid", prefix);

    
   

    // HOTBAR

    // FREEZE

    public int freeze_slot = 0;
    public ItemStack freeze_item = new ItemStack(Material.ICE);
    public String freeze_title = "§9§lCONGELAR";
    public List<String> freeze_lore = generateFrezzeLore();
    private ArrayList<String> generateFrezzeLore() {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§7¡Clic para examinar al jugador!");
        return toReturn;
    }

    // RANDOM TP

    public int random_slot = 1;
    public ItemStack random_item = new ItemStack(Material.COMPASS);
    public String random_title = "§7§lRANDOM TP";
    public ArrayList<String> generateRandomLore() {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§7¡Clic para teletransportarte aleatoriamente!");
        return toReturn;
    }


    // STAFF LIST

    public int slist_slot = 8;
    public String slist_title = "§1§lLISTA DE STAFF";
    public ArrayList<String> slistRandomLore() {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§7¡Clic para ver al staff!");
        return toReturn;
    }

    // EXAMINE

    public int examine_slot = 6;
    public ItemStack examine_item = new ItemStack(Material.CHEST);
    public String examine_title = "§6§lEXAMINAR";
    public ArrayList<String> generateExamineLore() {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§7¡Clic para examinar al jugador!");
        return toReturn;
    }

    // VANISH

    public int vanish_slot = 4;
    public ItemStack vanish_item_enabled = new ItemStack(Material.GLASS_BOTTLE);
    public ItemStack vanish_item_disabled = new ItemStack(Material.POTION);
    public String vanish_title = "§b§lVANISH §8(%state§8)"; //STATE FOR ENABLED OR DISABLED
    public String vanish_title_active = "§6§lACTIVADO";
    public String vanish_title_unactive = "§c§lDESACTIVADO";
    public ArrayList<String> generateVanishLore() {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§7¡Clic para modificar el vanish!");
        return toReturn;
    }

    // END OF HOTBAR

    // EXAMINE GUI
    public int examine_GUI_title_size = plugin.getConfig().getInt("gui.examine.size");
    public String examine_GUI_title_vis = plugin.getConfig().getString("gui.examine.view_title");
    public String examine_GUI_title_edit = plugin.getConfig().getString("gui.examine.edit_title");

    // USER INFO
    public ItemStack user_info_item = new ItemStack(Material.SKULL_ITEM);
    public String user_info_title = "%player";
    public String user_info_enabled = "ON";
    public String user_info_disabled = "OFF";
    public ArrayList<String> generateUserInfoLore(String god, String fly, String h, String loc, String w) {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§1");
        toReturn.add(Utils.userReplace("§7Vida: %health/20", god, fly, h, loc, w));
        toReturn.add(Utils.userReplace("§7Location: %loc", god, fly, h, loc, w));
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
