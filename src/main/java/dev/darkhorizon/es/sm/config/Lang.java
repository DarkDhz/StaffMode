package dev.darkhorizon.es.sm.config;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Lang {

    public static String prefix = "§8[§6SM§r§8] §7";

    //Permisions Related
    public static String no_prem = prefix + "No tienes permisos para ejecutar este comando";


    public static String staff_usage = prefix + " /staff para activar o desactivar el staffMode";

    // Events Related

    public static String staff_no_drop = prefix + "No puedes dropear items en modo staff";
    public static String staff_no_place = prefix + "No puedes colocar bloques en modo staff";
    public static String staff_no_break = prefix + "No puedes romper bloques en modo staff";
    public static String staff_no_pvp = prefix + "Pvp Desactivado en modo Staff";
    public static String staff_spawner_break = prefix + "%player ha roto un spawner de %type";


    // /staff related

    public static String enabledStaffMode = prefix + "¡Ahora estas en modo Staff!";
    public static String diabledStaffMode = prefix + "¡Ya no estas en modo Staff!";


    // PLAYER HOT-BAR

    // FREEZE
    public static int freeze_slot = 0;
    public static ItemStack freeze_item = new ItemStack(Material.ICE);
    public static String freeze_title = "§9§lCONGELAR";
    public static List<String> freeze_lore = generateFrezzeLore();

    private static ArrayList<String> generateFrezzeLore() {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§7¡Clic para examinar al jugador!");
        return toReturn;
    }

    // RANDOM TP

    public static int random_slot = 1;
    public static ItemStack random_item = new ItemStack(Material.COMPASS);
    public static String random_title = "§7§lRANDOM TP";
    public static List<String> random_lore = generateRandomLore();
    private static ArrayList<String> generateRandomLore() {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§7¡Clic para teletransportarte aleatoriamente!");
        return toReturn;
    }


    // STAFF LIST

    public static int slist_slot = 8;
    public static String slist_title = "§1§lLISTA DE STAFF";
    public static List<String> slist_lore = slistRandomLore();
    private static ArrayList<String> slistRandomLore() {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§7¡Clic para ver al staff!");
        return toReturn;
    }

    // EXAMINE

    public static int examine_slot = 6;
    public static ItemStack examine_item = new ItemStack(Material.CHEST);
    public static String examine_title = "§6§lEXAMINAR";
    public static List<String> examine_lore = generateExamineLore();
    private static ArrayList<String> generateExamineLore() {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§7¡Clic para examinar al jugador!");
        return toReturn;
    }

    // VANISH

    public static int vanish_slot = 4;
    public static ItemStack vanish_item = new ItemStack(Material.POTION);
    public static String vanish_title = "§b§lVANISH §8(%state§8)"; //STATE FOR ENABLED OR DISABLED
    public static String vanish_title_active = "§6§lACTIVADO";
    public static String vanish_title_unactive = "§c§lDESACTIVADO";
    public static List<String> vanish_lore = generateVanishLore();
    private static ArrayList<String> generateVanishLore() {
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("§7¡Clic para modificar el vanish!");
        return toReturn;
    }



}
