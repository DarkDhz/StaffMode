package dev.darkhorizon.es.sm.utils;

import dev.darkhorizon.es.sm.Main;
import org.bukkit.entity.Player;

public class Utils {

    private static final Main plugin = Main.getPlugin(Main.class);

    public static void reverse(Object[] array) {
        if (array == null)
            return;
        int i = 0;
        int x = Math.min(array.length, array.length - 1);
        while (x > i) {
            Object tmp = array[x];
            array[x] = array[i];
            array[i] = tmp;
            x--;
            i++;
        }
    }

    public static String userReplace( String cadena, String god, String fly, String h, String loc, String w) {
        return cadena.replaceAll("%god", god).replaceAll("%fly", fly).replaceAll("%health", h).replaceAll("%loc", loc).replaceAll("%world", w);
    }

    public static String slistItemReplace(String cadena, Player p) {
        return cadena.replaceAll("%x","" + p.getLocation().getBlockX())
                .replaceAll("%y", "" + p.getLocation().getBlockY())
                .replaceAll("%z", "" + p.getLocation().getBlockZ())
                .replaceAll("%world", p.getWorld().getName());
    }

    public static String simpleMessageReplace(String path, String prefix) {
        return plugin.getConfig().getString(path).replaceAll("&", "§").replaceAll("%prefix", prefix);
    }

}
