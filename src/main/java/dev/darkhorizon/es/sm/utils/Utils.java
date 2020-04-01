package dev.darkhorizon.es.sm.utils;

import com.earth2me.essentials.IUser;
import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> simpleListMessageReplace(String path, String prefix) {
        List<String> toReturn = new ArrayList<>();
        for (String s : plugin.getConfig().getStringList(path)) {
            toReturn.add(s.replaceAll("&", "§").replaceAll("%prefix", prefix));
        }
        return toReturn;
    }

}
