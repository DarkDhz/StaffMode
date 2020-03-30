package dev.darkhorizon.es.sm.utils;

public class Utils {

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
}
