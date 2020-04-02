package dev.darkhorizon.es.sm.utils;

import com.earth2me.essentials.IUser;
import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
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

    public static List<String> userReplace(String path, FileConfiguration file, String god, String fly, String h, Location loc, String w) {
        List<String> toReturn = new ArrayList<>();
        for (String cadena : file.getStringList(path)) {
            toReturn.add(cadena.replaceAll("%god", god)
                    .replaceAll("%fly", fly)
                    .replaceAll("%health", h)
                    .replaceAll("%loc", loc.toString())
                    .replaceAll("&", "§")
                    .replaceAll("%x", "" + loc.getBlockX())
                    .replaceAll("%y", "" + loc.getBlockY())
                    .replaceAll("%z", "" + loc.getBlockZ())
                    .replaceAll("%world", w));
        }
        return toReturn;
    }

    public static List<String> slistItemReplace(String path, FileConfiguration file, Player p) {
        List<String> toReturn = new ArrayList<>();
        for (String cadena : file.getStringList(path)) {
            toReturn.add(cadena.replaceAll("%x","" + p.getLocation().getBlockX())
                    .replaceAll("%y", "" + p.getLocation().getBlockY())
                    .replaceAll("%z", "" + p.getLocation().getBlockZ())
                    .replaceAll("&", "§")
                    .replaceAll("%world", p.getWorld().getName()));
        }
        return toReturn;
    }

    public static String simpleMessageReplace(String path, String prefix, FileConfiguration file) {
        return file.getString(path).replaceAll("&", "§").replaceAll("%prefix", prefix);
    }

    public static List<String> simpleListMessageReplace(String path, String prefix, FileConfiguration file) {
        List<String> toReturn = new ArrayList<>();
        for (String s : file.getStringList(path)) {
            toReturn.add(s.replaceAll("&", "§").replaceAll("%prefix", prefix));
        }
        return toReturn;
    }

}
