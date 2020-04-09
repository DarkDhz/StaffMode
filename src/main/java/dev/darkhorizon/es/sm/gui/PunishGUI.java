package dev.darkhorizon.es.sm.gui;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.items.Items;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PunishGUI {

    private final Main plugin = Main.getPlugin(Main.class);
    private final Items item = Items.getInstance();
    private Lang lang = Lang.getInstance();

    public enum gui_type {MAIN, SUB, CUSTOM}

    public PunishGUI(Player p, String target, gui_type e) {
        switch (e) {
            case MAIN:
                this.generateInventory(p, target);
                break;
            case SUB:
                break;
            case CUSTOM:
                break;
        }
        this.generateInventory(p, target);
    }

    private void generateInventory(Player p, String target) {
        Inventory inv = Bukkit.createInventory(p, 6*9, "§e§lSanciones para " + target);
        List<String> lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(19, generateBanItem("§6§lSpam de Ip ajena.", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(21, this.generateBanItem("§6§lAcumulación de mutes (3).", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para warnear");
        inv.setItem(20, this.generateBanItem("§6§lSpam / Flood", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(22, this.generateBanItem("§6§lAcumulación de Baneos por acumulación de Mutes (2)", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(23, this.generateBanItem("§6§lComercialización fuera de la network.", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para ir!");
        inv.setItem(24, this.generateBanItem("§6§lInsultos al staff.", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para ir!");
        inv.setItem(25, this.generateBanItem("§6§lHostigamiento o acoso hacia otro jugador.", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para ir!");
        inv.setItem(28, this.generateBanItem("§6§lInsultos hacia otro jugador.", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para ir!");
        inv.setItem(29, this.generateBanItem("§6§lPedir rango u objetos al staff.", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(14, this.generateBanItem("§6§lIntento de venta de una cuenta.", lore, true));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(15, this.generateBanItem("§6§lVenta de una cuenta", lore, true));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(30, this.generateBanItem("§6§lUso de Bugs", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(31, this.generateBanItem("§6§lMulticuentas", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(32, this.generateBanItem("§6§lMentir al staff", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para ir!");
        inv.setItem(33, this.generateBanItem("§6§lNegarse a SS", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(34, this.generateBanItem("§6§lNombres inadecuados en /ah.", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(37, this.generateBanItem("§6§lEstafa a otro usuario.", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(38, this.generateBanItem("§6§lGrifeo en proteciones ajenas.", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para ir!");
        inv.setItem(39, this.generateBanItem("§6§lMecanismos AntiAFK", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para ir!");
        inv.setItem(40, this.generateBanItem("§6§lEstafa por /ah", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(41, this.generateBanItem("§6§lEstafa.", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para sancionar");
        inv.setItem(42, this.generateBanItem("§6§lTpaKill", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para ir!");
        inv.setItem(43, this.generateBanItem("§6§lFreeKill", lore, false));
        lore = new ArrayList<>();
        lore.add("§e» Clic para ir!");
        inv.setItem(11, this.generateBanItem("§6§lMODIFICACIONES ILEGALES EN SS", lore, true));
        lore = new ArrayList<>();
        lore.add("§e» Clic para ir!");
        inv.setItem(12, this.generateBanItem("§6§lMODIFICACIONES ILEGALES EVIDENTES", lore, true));
        lore = new ArrayList<>();
        lore.add("§e» Clic para ir!");
        inv.setItem(13, this.generateBanItem("§6§lMODIFICACIONES ILEGALES ADMITIDAS", lore, true));
        p.openInventory(replaceEmpty(inv));
    }

    private static Inventory replaceEmpty(Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)  {
                ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§f");
                item.setItemMeta(meta);
                inv.setItem(i, item);
            }
        }
        return inv;
    }

    public static void generateSubInventory(Player p, String target, String title) {
        Inventory inv = Bukkit.createInventory(p, 9, title + target);
        List<String> lore;
        switch (title) {
            case "§6§lBugs ":
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(0, generateBanItem("§6§lBug Leve - Baneo temporal de 1 dia", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(1, generateBanItem("§6§lBug medio - Baneo temporal de 2 días", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(2, generateBanItem("§6§lBug alto - Baneo temporal de 3 días", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(3, generateBanItem("§6§lBug grave - Baneo Permanente", lore, false));
                break;
            case "§6§lEstafa Ah ":
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(0, generateBanItem("§6§l1ª vez - Aviso (warn)", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(1, generateBanItem("§6§l2ª vez - Baneo temporal de 10 días", lore, false));
                break;
            case "§6§lInsultos staff ":
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(0, generateBanItem("§6§l1ª vez - Aviso (warn)", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(1, generateBanItem("§6§l2ª vez - Baneo temporal de 7 días", lore, false));
                break;
            case "§6§lInsultos jugador ":
            case "§6§lAcoso ":
            case "§6§lAntiAfk ":
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(0, generateBanItem("§6§l1ª vez - Aviso (warn)", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(1, generateBanItem("§6§l2ª vez - Baneo temporal de 5 días", lore, false));
                break;
            case "§6§lPedir al Staff ":
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(0, generateBanItem("§6§l1ª vez - Aviso (warn)", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(1, generateBanItem("§6§l2ª vez - Baneo temporal de 3 días", lore, false));
                break;
            case "§6§lNO SS ":
            case "§6§lMOD EVIDENTES ":
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(0, generateBanItem("§6§lSanción con rango", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(1, generateBanItem("§6§lSanción sin rango", lore, false));
                break;
            case "§6§lFreeKill ":
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(0, generateBanItem("§6§l1ª vez - Advertencia (warn)", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(1, generateBanItem("§6§l2ª vez - 2 Aviso (warn)", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para sancionar!");
                inv.setItem(2, generateBanItem("§6§l3ª vez - Baneo temporal de 30min", lore, false));
                break;
            case "§6§lMOD SS ":
                lore = new ArrayList<>();
                lore.add("§eClic para ir!");
                inv.setItem(0, generateBanItem("§6§lUso de Hack Client en SS", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para ir!");
                inv.setItem(1, generateBanItem("§6§lUso de Macros en SS", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para ir!");
                inv.setItem(2, generateBanItem("§6§lUso de Mods Ilegales en SS", lore, false));
                break;
            case "§6§lMOD ADMITIDAS ":
                lore = new ArrayList<>();
                lore.add("§eClic para ir!");
                inv.setItem(0, generateBanItem("§6§lUso de Hack Client Admitido", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para ir!");
                inv.setItem(1, generateBanItem("§6§lUso de Macros Admitido", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para ir!");
                inv.setItem(2, generateBanItem("§6§lUso de Mods Ilegales Admitido", lore, false));
                break;

        }
        inv.setItem(8, generateArrow());
        p.openInventory(replaceEmpty(inv));
    }

    public static void generateSubSub(Player p, String target, String title) {
        Inventory inv = Bukkit.createInventory(p, 9, title + target);
        List<String> lore;
        switch (title) {
            case "§6§lSS HackClient ":
            case "§6§lA Macros ":
            case "§6§lA Mods ":
            case "§6§lA HackClient ":
            case "§6§lSS Macros ":
            case "§6§lSS Mods ":
                lore = new ArrayList<>();
                lore.add("§eClic para ir!");
                inv.setItem(0, generateBanItem("§6§lSanción con rango", lore, false));
                lore = new ArrayList<>();
                lore.add("§eClic para ir!");
                inv.setItem(1, generateBanItem("§6§lSanción sin rango", lore, false));
                break;
        }
        inv.setItem(8, generateArrow());
        p.openInventory(replaceEmpty(inv));

    }


    public static ItemStack generateBanItem(String title, List<String> lore, boolean glow) {
        ItemStack item = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(title);
        meta.setLore(lore);

        if (glow) {
            meta.addEnchant(Enchantment.DURABILITY, 0, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack generateArrow() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cVolver");
        List<String> lore = new ArrayList<>();
        lore.add("§eClic para ir atrás");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }


}
