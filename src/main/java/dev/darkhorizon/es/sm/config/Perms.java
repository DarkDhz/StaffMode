package dev.darkhorizon.es.sm.config;

public class Perms {

    //permissions
    public static String main_permission = "virtual.staff.basic";
    public static String freeze_permission = "virtual.staff.freeze";
    public static String examine_edit_permission = "virtual.staff.examine.edit";
    public static String spawner_advise_permission = main_permission;

    //EVENTS
    public static boolean can_drop = false;
    public static boolean can_pickup = false;
    public static boolean can_place = false;
    public static boolean can_destroy = true;
    public static boolean spawner_advisor = true;


    // GOES WITH main_premision
    public enum pvp_type {ALL, ONLY_STAFFMODE, NEVER}
    public static pvp_type can_pvp = pvp_type.NEVER;
}
