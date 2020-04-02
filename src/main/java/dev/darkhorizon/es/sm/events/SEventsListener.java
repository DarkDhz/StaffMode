package dev.darkhorizon.es.sm.events;

import com.earth2me.essentials.IUser;
import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.commands.Punish;
import dev.darkhorizon.es.sm.commands.Staff;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.config.Perms;
import dev.darkhorizon.es.sm.data.Data;
import dev.darkhorizon.es.sm.gui.PunishGUI;
import dev.darkhorizon.es.sm.items.Items;
import net.ess3.api.events.AfkStatusChangeEvent;
import net.ess3.api.events.VanishStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SEventsListener implements Listener {
    private final Main plugin = Main.getPlugin(Main.class);
    private final Items items = Items.getInstance();
    private Lang lang = Lang.getInstance();

    @EventHandler
    public void onVanishChange(VanishStatusChangeEvent e) {
        Player p = Bukkit.getPlayer(e.getAffected().getName());
        if (p == null) {
            return;
        }
        if (Data.staff_players.contains(p.getName())) {
            items.updateVanish(p, e.getValue());
            return;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player))
            return;

        Player clicker = e.getPlayer();
        Player hitted = (Player) e.getRightClicked();

        if (Data.staff_players.contains(clicker.getName())) {
            if (clicker.getItemInHand().getType() == lang.freeze_item.getType() && clicker.getItemInHand().hasItemMeta()
                    && clicker.getItemInHand().getItemMeta().getDisplayName().equals(lang.freeze_title)) {
                String command = "freeze " + hitted.getName();
                clicker.performCommand(command);
                e.setCancelled(true);
            }
            if (clicker.getItemInHand().getType() == lang.examine_item.getType() && clicker.getItemInHand().hasItemMeta()
                    && clicker.getItemInHand().getItemMeta().getDisplayName().equals(lang.examine_title)) {
                String command = "examine " + hitted.getName();
                clicker.performCommand(command);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (Data.staff_players.contains(p.getName())) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (p.getItemInHand().getType() == Material.SKULL_ITEM && p.getItemInHand().hasItemMeta()
                        && p.getItemInHand().getItemMeta().getDisplayName().contains(lang.slist_title)) {
                    String command = "stafflist";
                    p.performCommand(command);
                    e.setCancelled(true);
                    return;
                }
                if (p.getItemInHand().getType() == lang.vanish_item_disabled.getType() && p.getItemInHand().hasItemMeta()
                        && p.getItemInHand().getItemMeta().getDisplayName().contains(lang.vanish_title_unactive)) {
                    IUser user = plugin.ess.getUser(p);
                    user.setVanished(true);
                    items.updateVanish(p, true);
                    e.setCancelled(true);
                    return;
                }
                if (p.getItemInHand().getType() == lang.vanish_item_enabled.getType() && p.getItemInHand().hasItemMeta()
                        && p.getItemInHand().getItemMeta().getDisplayName().contains(lang.vanish_title_active)) {
                    IUser user = plugin.ess.getUser(p);
                    user.setVanished(false);
                    items.updateVanish(p, false);
                    e.setCancelled(true);
                    return;
                }
                if (p.getItemInHand().getType() == lang.random_item.getType() && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName().contains(lang.random_title)) {
                    p.performCommand("playertp");
                    e.setCancelled(true);
                    return;
                }
            }
        }


    }

    @EventHandler
    public void onDestroy(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (Data.staff_players.contains(e.getPlayer().getName())) {
            if (!(Perms.can_destroy)) {
                p.sendMessage(lang.staff_no_break);
                e.setCancelled(true);
            }

        }
        if (e.getBlock().getType() != Material.MOB_SPAWNER || !(Perms.spawner_advisor)) {
            return;
        }

        CreatureSpawner spawner = (CreatureSpawner)e.getBlock().getState();
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission(Perms.spawner_advise_permission)) {
                staff.sendMessage("");
                String msg = lang.staff_spawner_break;
                msg = msg.replaceAll("%player", p.getName());
                msg = msg.replaceAll("%type", spawner.getCreatureTypeName());
                staff.sendMessage(msg);
                staff.sendMessage("");
            }
        }
        if (Data.frozen.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (Data.staff_players.contains(e.getPlayer().getName())) {
            if (!(Perms.can_place)) {
                p.sendMessage(lang.staff_no_place);
                e.setCancelled(true);
            }
        }
        if (Data.frozen.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void getDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player hitted = (Player) e.getEntity();
            if (Data.staff_players.contains(hitted.getPlayer().getName()) || Data.frozen.contains(hitted.getName())) {
                e.setCancelled(true);
            }
        }

        if (e.getDamager() instanceof Player) {
            Player hitted = (Player) e.getDamager();
            if (hitted.isOp()) {
                return;
            }
            if (Data.frozen.contains(hitted.getName())) {
                hitted.sendMessage(lang.frozen_pvp_msg);
                e.setCancelled(true);
                return;
            }
            if (hitted.hasPermission(Perms.main_permission)) {
                switch (Perms.can_pvp) {
                    case ALL:
                        e.setCancelled(false);
                        break;
                    case ONLY_STAFFMODE:
                        if (Data.staff_players.contains(hitted.getPlayer().getName())) {
                            hitted.sendMessage(lang.staff_no_pvp);
                            e.setCancelled(true);
                        }
                        break;
                    case NEVER:
                        hitted.sendMessage(lang.staff_no_pvp);
                        e.setCancelled(true);
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if (Data.frozen.contains(p.getName()) || p.hasPermission(Perms.main_permission)) {
                e.setDamage(0);
                return;
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getSlotType() == null) {
            return;
        }

        Player p = (Player) e.getWhoClicked();
        if (Data.frozen.contains(p.getName())) {
            e.setCancelled(true);
            return;
        }

        ItemStack item = e.getCurrentItem();
        if (p.hasPermission(Perms.main_permission)) {
            if (Data.staff_players.contains(p.getName())) {
                if (item.getType() == Material.SKULL_ITEM && item.hasItemMeta()) {
                    e.setCancelled(true);
                    return;
                }
                if (item.getType() == lang.vanish_item_disabled.getType() && item.hasItemMeta()) {
                    e.setCancelled(true);
                    return;
                }
                if (item.getType() == lang.vanish_item_enabled.getType() && item.hasItemMeta() && item.getItemMeta().getDisplayName().contains(lang.vanish_title)) {
                    e.setCancelled(true);
                    return;
                }

                if (item.getType() == lang.random_item.getType() && item.hasItemMeta() && item.getItemMeta().getDisplayName().contains(lang.random_title)) {
                    e.setCancelled(true);
                    return;
                }

                if (p.getInventory().getTitle().equals(lang.stafflist_GUI_title)) {
                    e.setCancelled(true);
                    return;
                }
            }

            managePunishTitles(p, e, item);

            if (p.getOpenInventory().getTitle().contains(lang.examine_GUI_title_vis)) {
                String[] user = p.getOpenInventory().getTitle().split(lang.examine_GUI_title_vis);
                Player target = Bukkit.getPlayer(user[1]);
                if (target == null) {
                    p.closeInventory();
                    return;
                }
                if (item.getType() == lang.ex_teleport_item.getType() && item.hasItemMeta() && item.getItemMeta().getDisplayName().contains(lang.ex_teleport_title)) {
                    p.teleport(target.getLocation());
                    p.closeInventory();
                }
                e.setCancelled(true);
                return;
            }
            if (p.getOpenInventory().getTitle().contains(lang.examine_GUI_title_edit)) {
                String[] user = p.getOpenInventory().getTitle().split(lang.examine_GUI_title_edit);
                Player target = Bukkit.getPlayer(user[1]);
                if (target == null) {
                    p.closeInventory();
                    return;
                }
                if (e.getSlot() > 35 && e.getSlot() < 45 || e.getSlot() == 53) {
                    e.setCancelled(true);
                }
                if (item.getType() == lang.ex_teleport_item.getType() && item.hasItemMeta() && item.getItemMeta().getDisplayName().contains(lang.ex_teleport_title)) {
                    p.teleport(target.getLocation());
                    p.closeInventory();
                }

            }
        }
    }

    private void managePunishTitles(Player p, InventoryClickEvent e, ItemStack item) {
        if (p.getOpenInventory().getTitle().contains("§e§lSanciones para ")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§e§lSanciones para ");
            Player target = Bukkit.getPlayer(user[1]);
            this.managePunishMain(p, target, item);
            return;
        } else if (p.getOpenInventory().getTitle().contains("§6§lInsultos staff")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§6§lInsultos staff ");
            Player target = Bukkit.getPlayer(user[1]);
            this.manageSubPunish(p, target, item, "§6§lInsultos staff ");
            return;
        } else if (p.getOpenInventory().getTitle().contains("§6§lAcoso ")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§6§lAcoso ");
            Player target = Bukkit.getPlayer(user[1]);
            this.manageSubPunish(p, target, item, "§6§lAcoso ");
            return;
        } else if (p.getOpenInventory().getTitle().contains("§6§lPedir al Staff ")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§6§lPedir al Staff ");
            Player target = Bukkit.getPlayer(user[1]);
            this.manageSubPunish(p, target, item, "§6§lPedir al Staff ");
            return;
        } else if (p.getOpenInventory().getTitle().contains("§6§lNO SS ")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§6§lNO SS ");
            Player target = Bukkit.getPlayer(user[1]);
            this.manageSubPunish(p, target, item, "§6§lNO SS ");
            return;
        } else if (p.getOpenInventory().getTitle().contains("§6§lAntiAfk ")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§6§lAntiAfk ");
            Player target = Bukkit.getPlayer(user[1]);
            this.manageSubPunish(p, target, item, "§6§lAntiAfk ");
            return;
        } else if (p.getOpenInventory().getTitle().contains("§6§lFreeKill ")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§6§lFreeKill ");
            Player target = Bukkit.getPlayer(user[1]);
            this.manageSubPunish(p, target, item, "§6§lFreeKill ");
            return;
        } else if (p.getOpenInventory().getTitle().contains("§6§lMOD SS ")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§6§lMOD SS ");
            Player target = Bukkit.getPlayer(user[1]);
            this.manageSubPunish(p, target, item, "§6§lFreeKill ");
            return;
        } else if (p.getOpenInventory().getTitle().contains("§6§lHackClient ")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§6§lHackClient ");
            Player target = Bukkit.getPlayer(user[1]);
            this.manageSubSub(p, target, item, "§6§lHackClient ");
            return;
        } else if (p.getOpenInventory().getTitle().contains("§6§lMacros ")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§6§lMacros ");
            Player target = Bukkit.getPlayer(user[1]);
            this.manageSubSub(p, target, item, "§6§lMacros ");
            return;
        } else if (p.getOpenInventory().getTitle().contains("§6§lMods ")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§6§lMods ");
            Player target = Bukkit.getPlayer(user[1]);
            this.manageSubSub(p, target, item, "§6§lMods ");
            return;
        } else if (p.getOpenInventory().getTitle().contains("§6§lMOD EVIDENTES ")) {
            e.setCancelled(true);
            String[] user = p.getOpenInventory().getTitle().split("§6§lMOD EVIDENTES ");
            Player target = Bukkit.getPlayer(user[1]);
            this.manageSubPunish(p, target, item, "§6§lMOD EVIDENTES ");
            return;
        }
    }

    private void manageSubPunish(Player launcher, Player target, ItemStack item, String title) {
        if (target != null && launcher != null) {
            if (item.getType() == Material.BOOK_AND_QUILL && item.hasItemMeta()) {
                switch (title) {
                    case "§6§lInsultos staff ":
                        if (item.getItemMeta().getDisplayName().contains("§6§l1ª vez - Aviso (warn)")) {
                            launcher.performCommand("warn " + target.getName() + " Insultos al staff");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§l2ª vez - Baneo temporal de 7 días")) {
                            launcher.performCommand("ban " + target.getName() + " 6d Insultos al staff");
                            launcher.closeInventory();
                            return;
                        }
                        break;
                    case "§6§lAcoso ":
                        if (item.getItemMeta().getDisplayName().contains("§6§l1ª vez - Aviso (warn)")) {
                            launcher.performCommand("warn " + target.getName() + " Insultos al staff");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§l2ª vez - Baneo temporal de 5 días")) {
                            launcher.performCommand("ban " + target.getName() + " 5d Comportamiento inadecuado");
                            launcher.closeInventory();
                            return;
                        }
                        break;
                    case "§6§lPedir al Staff ":
                        if (item.getItemMeta().getDisplayName().contains("§6§l1ª vez - Aviso (warn)")) {
                            launcher.performCommand("warn " + target.getName() + " Insultos al staff");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§l2ª vez - Baneo temporal de 3 días")) {
                            launcher.performCommand("ban " + target.getName() + " 3d Comportamiento inadecuado");
                            launcher.closeInventory();
                            return;
                        }
                        break;
                    case "§6§lNO SS ":
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción con rango")) {
                            launcher.performCommand("warn " + target.getName() + " 30d Negarse a SS");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción sin rango")) {
                            launcher.performCommand("ban " + target.getName() + " Negarse a SS");
                            launcher.closeInventory();
                            return;
                        }
                        break;
                    case "§6§lAntiAfk ":
                        if (item.getItemMeta().getDisplayName().contains("§6§l1ª vez - Aviso (warn)")) {
                            launcher.performCommand("warn " + target.getName() + " Mecanismos AntiAFK");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§l2ª vez - Baneo temporal de 5 días")) {
                            launcher.performCommand("ban " + target.getName() + " 5d Mecanismos AntiAFK");
                            launcher.closeInventory();
                            return;
                        }
                        break;
                    case "§6§lFreeKill ":
                        if (item.getItemMeta().getDisplayName().contains("§6§l1ª vez - Advertencia (warn)")) {
                            launcher.performCommand("warn " + target.getName() + " Mecanismos AntiAFK");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§l2ª vez - 2 Aviso (warn)")) {
                            launcher.performCommand("warn " + target.getName() + " Mecanismos AntiAFK");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§l3ª vez - Baneo temporal de 30min")) {
                            launcher.performCommand("ban " + target.getName() + " 30m FreeKill");
                            launcher.closeInventory();
                            return;
                        }
                        break;
                    case "§6§lMOD SS ":
                        if (item.getItemMeta().getDisplayName().contains("§6§lUso de Hack Client")) {
                            PunishGUI.generateSubSub(launcher, target, "§6§lHackClient ");
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§lUso de macros")) {
                            PunishGUI.generateSubSub(launcher, target, "§6§lMacros ");
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§lUso de mods ilegales")) {
                            PunishGUI.generateSubSub(launcher, target, "§6§lMods ");
                            return;
                        }
                        break;
                    case "§6§lMOD ADMITIDAS ":
                        if (item.getItemMeta().getDisplayName().contains("§6§l[A] Uso de Hack Client")) {
                            PunishGUI.generateSubSub(launcher, target, "§6§l[A] HackClient ");
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§l[A] Uso de macros")) {
                            PunishGUI.generateSubSub(launcher, target, "§6§l[A] Macros ");
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§l[A] Uso de mods ilegales")) {
                            PunishGUI.generateSubSub(launcher, target, "§6§l[A] Mods ");
                            return;
                        }
                        break;
                    case "§6§lMOD EVIDENTES ":
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción con rango")) {
                            launcher.performCommand("warn " + target.getName() + " 30d Modificaciones ilegales [E]");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción sin rango")) {
                            launcher.performCommand("warn " + target.getName() + " Modificaciones ilegales [E]");
                            launcher.closeInventory();
                            return;
                        }
                        break;
                }
            }
        }
    }

    private void manageSubSub(Player launcher, Player target, ItemStack item, String title) {
        if (target != null && launcher != null) {
            if (item.getType() == Material.BOOK_AND_QUILL && item.hasItemMeta()) {
                switch (title) {
                    case "§6§l[SS] HackClient ":
                    case "§6§l[SS] Mods ":
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción con rango")) {
                            launcher.performCommand("warn " + target.getName() + " 30d Modificaciones ilegales [SS]");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción sin rango")) {
                            launcher.performCommand("warn " + target.getName() + " Modificaciones ilegales [SS]");
                            launcher.closeInventory();
                            return;
                        }
                        break;
                    case "§6§l[SS] Macros ":
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción con rango")) {
                            launcher.performCommand("warn " + target.getName() + " 14d Modificaciones ilegales [SS]");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción sin rango")) {
                            launcher.performCommand("warn " + target.getName() + " 30d Modificaciones ilegales [SS]");
                            launcher.closeInventory();
                            return;
                        }
                        break;
                    case "§6§l[A] HackClient ":
                    case "§6§l[A] Mods ":
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción con rango")) {
                            launcher.performCommand("warn " + target.getName() + " 14d Modificaciones ilegales [A]");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción sin rango")) {
                            launcher.performCommand("warn " + target.getName() + " 20d Modificaciones ilegales [A]");
                            launcher.closeInventory();
                            return;
                        }
                        break;
                    case "§6§l[A] Macros ":
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción con rango")) {
                            launcher.performCommand("warn " + target.getName() + " 7d Modificaciones ilegales [A]");
                            launcher.closeInventory();
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().contains("§6§lSanción sin rango")) {
                            launcher.performCommand("warn " + target.getName() + " 14d Modificaciones ilegales [A]");
                            launcher.closeInventory();
                            return;
                        }
                        break;
                }
            }
        }
    }

    private void managePunishMain(Player launcher, Player target, ItemStack item) {
        if (target != null && launcher != null) {
            if (item.getType() == Material.BOOK_AND_QUILL && item.hasItemMeta()) {
                if (item.getItemMeta().getDisplayName().contains("§6§lSpam de Ip ajena")) {
                    launcher.performCommand("ban " + target.getName() + " Pasar IP ajena");
                    launcher.closeInventory();
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lInsultos al staff.")) {
                    PunishGUI.generateSubInventory(launcher, target, "§6§lInsultos staff ");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lHostigamiento o acoso hacia otro jugador.")) {
                    PunishGUI.generateSubInventory(launcher, target, "§6§lAcoso ");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lPedir rango u objetos al staff.")) {
                    PunishGUI.generateSubInventory(launcher, target, "§6§lPedir al Staff ");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lNegarse a SS")) {
                    PunishGUI.generateSubInventory(launcher, target, "§6§lNO SS ");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lMecanismos AntiAFK (solo aplicable a usuarios sin rango)")) {
                    PunishGUI.generateSubInventory(launcher, target, "§6§lAntiAfk ");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lFreeKill en MinaPvP o Coliseo")) {
                    PunishGUI.generateSubInventory(launcher, target, "§6§lFreeKill ");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lMODIFICACIONES ILEGALES EN SS")) {
                    PunishGUI.generateSubInventory(launcher, target, "§6§lMOD SS ");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lMODIFICACIONES ILEGALES EVIDENTES")) {
                    PunishGUI.generateSubInventory(launcher, target, "§6§lMOD EVIDENTES ");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lMODIFICACIONES ILEGALES EVIDENTES")) {
                    PunishGUI.generateSubInventory(launcher, target, "§6§lMOD ADMITIDAS ");
                    return;
                }

                if (item.getItemMeta().getDisplayName().contains("§6§lAcumulación de mutes (3)")) {
                    launcher.performCommand("ban " + target.getName() + " 3d Acumulación de mutes");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lSpam / Flood")) {
                    launcher.performCommand("warn " + target.getName() + " Spam / Flood");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lAcumulación de Baneos por acumulación de Mutes (2)")) {
                    launcher.performCommand("ban " + target.getName() + " 10d Acumulación de baneos");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lComercialización de objetos con beneficios fuera de la network.")) {
                    launcher.performCommand("ban " + target.getName() + " Comercialización (Fuera de la network)");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lIntento de venta de una cuenta.")) {
                    launcher.performCommand("ban " + target.getName() + " 3d Intento de comercialización");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lVenta de una cuenta")) {
                    launcher.performCommand("ban " + target.getName() + " Venta de cuenta");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lEvadir sanción")) {
                    launcher.performCommand("ban " + target.getName() + " 3d Evasión de Sanción");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lMulticuentas")) {
                    launcher.performCommand("ipban " + target.getName() + " 14d Multicuentas");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lMentir al staff")) {
                    launcher.performCommand("ban " + target.getName() + " 4d Mentir al Staff");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lGrifeo en proteciones ajenas")) {
                    launcher.performCommand("ban " + target.getName() + " 10d Grifeo (Survival)");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lEstafa.")) {
                    launcher.performCommand("ban " + target.getName() + " 10d Estafa (Survival)");
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lTpaKill")) {
                    launcher.performCommand("ban " + target.getName() + " 7d TpaKill (Survival)");
                    return;
                }
            }
        }
    }

    @EventHandler
    public void pickUp(PlayerPickupItemEvent e) {
        if (Data.staff_players.contains(e.getPlayer().getName())) {
            if (!(Perms.can_pickup)) {
                e.setCancelled(true);
            }
        }
        if (Data.frozen.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent e) {
        if (Data.staff_players.contains(e.getPlayer().getName())) {
            if (!(Perms.can_drop)) {
                e.getPlayer().sendMessage(lang.staff_no_drop);
                e.setCancelled(true);
            }
        }
        if (Data.frozen.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = e.getEntity();
            if (Data.staff_players.contains(p.getName())) {
                e.getDrops().clear();
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Data.frozen.contains(p.getName())) {
            p.getInventory().setHelmet(Data.freeze_helmet.get(p.getName()));
            return;
        }
        if (Data.staff_players.contains(p.getName())) {
            Data.staff_players.remove(p.getName());
            items.rehabInventory(p);
            Data.staff_inv.remove(p.getName());
            return;
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getPlayer();
        if (inv.getTitle().contains(lang.examine_GUI_title_edit)) {
            String[] user = p.getOpenInventory().getTitle().split(lang.examine_GUI_title_edit);
            ItemStack[] contents = inv.getContents();
            Player target = Bukkit.getPlayer(user[1]);
            if (target == null) {
                return;
            }

            ItemStack[] new_content = new ItemStack[36];
            for (int i = 0; i < 36; i++) {
                new_content[i] = contents[i];
            }
            target.getInventory().setContents(new_content);
            Data.freeze_helmet.replace(target.getName(), contents[45]);
            target.getInventory().setChestplate(contents[46]);
            target.getInventory().setLeggings(contents[47]);
            target.getInventory().setBoots(contents[48]);

        }

    }

    @EventHandler
    public void onAfk(AfkStatusChangeEvent e) {
        Player p = Bukkit.getPlayer(e.getAffected().getName());
        if (p == null) {
            return;
        }
        if (Data.frozen.contains(p.getName()) && e.getValue()) {
            e.getAffected().setAfk(false);
        }

    }

}
