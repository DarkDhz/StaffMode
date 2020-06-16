package dev.darkhorizon.es.sm.config;

public class Perms {

    // Permissions
    public static String main_permission = "virtual.staff.basic";
    public static String freeze_permission = "virtual.staff.freeze";

    public static String tp_permission = main_permission;
    public static String punish_permission = "virtual.staff.punish";
    public static String loc_permission = "virtual.staff.editloc";
    public static String slist_permission = main_permission;
    public static String examine_permission = main_permission;
    public static String spec_permission = "virtual.staff.spectate";
    public static String examine_edit_permission = "virtual.staff.examine.edit";
    public static String spawner_advise_permission = main_permission;
    public static String alerts_on_join = "virtual.staff.alerts";

    // EVENTS
    public static boolean can_drop = false;
    public static boolean can_pickup = false;
    public static boolean can_place = false;
    public static boolean can_destroy = false;
    public static boolean spawner_advisor = true;

    // GOES WITH main_premision
    public enum pvp_type {ALL, ONLY_STAFFMODE, NEVER}
    public static pvp_type can_pvp = pvp_type.NEVER;
}
