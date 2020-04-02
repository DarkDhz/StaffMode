package dev.darkhorizon.es.sm.events;

import com.earth2me.essentials.IUser;
import dev.darkhorizon.es.sm.Main;
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

            if (p.getOpenInventory().getTitle().contains("§e§lSanciones para ")) {
                e.setCancelled(true);
                String[] user = p.getOpenInventory().getTitle().split("§e§lSanciones para ");
                Player target = Bukkit.getPlayer(user[1]);
                this.managePunishMain(p, target, item);
                return;
            } else if (p.getOpenInventory().getTitle().contains("§6§lInsultos ")) {
                e.setCancelled(true);
                String[] user = p.getOpenInventory().getTitle().split("§6§lInsultos ");
                Player target = Bukkit.getPlayer(user[1]);
                this.manageSubPunish(p, target, item, p.getInventory().getTitle());
                return;
            } 

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

    private void manageSubPunish(Player launcher, Player target, ItemStack item, String title) {
        if (target != null && launcher != null) {
            if (item.getType() == Material.ENCHANTED_BOOK && item.hasItemMeta()) {
                switch (title) {
                    case "§6§lInsultos ":

                        break;
                }
            }
        }
    }

    private void managePunishMain(Player launcher, Player target, ItemStack item) {
        if (target != null && launcher != null) {
            if (item.getType() == Material.ENCHANTED_BOOK && item.hasItemMeta()) {
                if (item.getItemMeta().getDisplayName().contains("§6§lSpam de Ip ajena.")) {
                    launcher.performCommand("ban " + target.getName() + " Pasar IP ajena");
                    launcher.closeInventory();
                    return;
                }
                if (item.getItemMeta().getDisplayName().contains("§6§lInsultos al staff.")) {
                    PunishGUI.generateSubInventory(launcher, target, "§6§lInsultos ");
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
