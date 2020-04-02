package dev.darkhorizon.es.sm.gui;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.items.Items;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PunishGUI {

    private final Main plugin = Main.getPlugin(Main.class);
    private final Items item = Items.getInstance();
    private Lang lang = Lang.getInstance();

    public enum gui_type {MAIN, SUB, CUSTOM}

    public PunishGUI(Player p, Player target, gui_type e) {
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

    private void generateInventory(Player p, Player target) {

        Inventory inv = Bukkit.createInventory(p, 6*9, "§e§lMenú de sanciones de " + target.getName());
        List<String> lore = new ArrayList<>();
        lore.add("➟Warn al jugador");
        inv.setItem(11, this.generateBanItem("§6§lSpam de Ip ajena.", lore));
        lore = new ArrayList<>();
        lore.add("nombrar");
        inv.setItem(12, this.generateBanItem("§6§lAcumulación de mutes.", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para ir!");
        inv.setItem(13, this.generateBanItem("§6§lSpam / Flood", lore));
        lore = new ArrayList<>();
        lore.add("nombrar");
        inv.setItem(14, this.generateBanItem("§6§lAcumulación de Baneos por acumulación de Mutes.", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para ir!");
        inv.setItem(15, this.generateBanItem("§6§lComercialización de objetos con beneficios fuera de la network.", lore));
        lore = new ArrayList<>();
        lore.add("nombrar");
        inv.setItem(19, this.generateBanItem("§6§lInsultos al staff.", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para ir!");
        inv.setItem(20, this.generateBanItem("§6§lHostigamiento o acoso hacia otro jugador.", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para ir!");
        inv.setItem(21, this.generateBanItem("§6§lInsultos hacia otro jugador.", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para ir!");
        inv.setItem(22, this.generateBanItem("§6§lPedir rango u objetos al staff.", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para ir!");
        inv.setItem(23, this.generateBanItem("§6§lIntento de venta de una cuenta.", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para ir!");
        inv.setItem(24, this.generateBanItem("§6§lVenta de una cuenta", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(25, this.generateBanItem("§6§lEvadir sanción", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(28, this.generateBanItem("§6§lMulticuentas", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(29, this.generateBanItem("§6§lMentir al staff", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(30, this.generateBanItem("§6§lNegarse a SS", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(31, this.generateBanItem("§6§lVenta de objetos con nombres inadecuados en /ah.s", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(32, this.generateBanItem("§6§lEstafa a otro usuario.", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(33, this.generateBanItem("§6§lRomper objetos en protecciones ajenas (Grifeo).", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(34, this.generateBanItem("§6§lMecanismos AntiAFK (solo aplicable a usuarios sin rango)", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(37, this.generateBanItem("§6§lEstafa por /ah", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(38, this.generateBanItem("§6§lEstafa.", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(39, this.generateBanItem("§6§lTPAKill", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para sancionar!");
        inv.setItem(40, this.generateBanItem("§6§lFreeKill en MinaPvP o Coliseo", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para ir!");
        inv.setItem(41, this.generateBanItem("§6§lMODIFICACIONES ILEGALES EN SS", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para ir!");
        inv.setItem(42, this.generateBanItem("§6§lMODIFICACIONES ILEGALES EVIDENTES", lore));
        lore = new ArrayList<>();
        lore.add("§eClic para ir!");
        inv.setItem(43, this.generateBanItem("§6§lMODIFICACIONES ILEGALES ADMITIDAS", lore));
        p.openInventory(inv);
    }

    public void generateSubInventory(Player p, Player target) {
        Inventory inv = Bukkit.createInventory(p, 3*9, "TITLE" + target.getName());

        p.openInventory(inv);
    }

    public ItemStack generateBanItem(String title, List<String> lore) {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(title);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

}
