package dev.darkhorizon.es.sm.data;

import dev.darkhorizon.es.sm.utils.Utils;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {

    public static ArrayList<String> staff_players = new ArrayList<String>();
    public static ArrayList<String> frozen = new ArrayList<String>();
    public static HashMap<String, ItemStack[]> staff_inv = new HashMap<String, ItemStack[]>();
    public static HashMap<String, ItemStack> freeze_helmet = new HashMap<>();
    public static HashMap<String, String> punish = new HashMap<>();
    public static Location punishLoc = Utils.getSSLocation();
    public static Location punishPostLoc = Utils.getSSPostLocation();

}

