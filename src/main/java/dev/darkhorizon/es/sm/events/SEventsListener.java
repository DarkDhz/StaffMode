package dev.darkhorizon.es.sm.events;

import com.earth2me.essentials.IUser;
import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.config.Perms;
import dev.darkhorizon.es.sm.data.Data;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

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
        if (e.getBlock().getType() != Material.MOB_SPAWNER) {
            return;
        }

        if (!(Perms.spawner_advisor)) {
            return;
        }

        CreatureSpawner spawner = (CreatureSpawner)e.getBlock().getState();
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission(Perms.spawner_advise_permission)) {
                staff.sendMessage("");
                String msg = lang.staff_spawner_break;
                msg = msg.replaceAll("%player", p.getName());
                msg = msg.replaceAll("%type", spawner.getCreatureTypeName());
            /*switch (spawner.getCreatureTypeName()) {
                case "CaveSpider":

                    staff.sendMessage(msg);
                    break;
                case "Skeleton":
                    staff.sendMessage(""+ p.getName() + " roto un spawner de ");
                    break;
                case "Spider":
                    staff.sendMessage(""+ p.getName() + " roto un spawner de normales");
                    break;
                default:
                    staff.sendMessage("" + p.getName() + " roto un spawner de  " + );
                    break;
            }*/
                staff.sendMessage(msg);
                staff.sendMessage("");
                //staff.playSound(staff.getLocation(), Sound.DIG_WOOL, 10.0F, 1.0F);
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
            if (Data.staff_players.contains(hitted.getPlayer().getName())) {
                e.setCancelled(true);
            }
            if (Data.frozen.contains(hitted.getName())) {
                e.setCancelled(true);
            }
        }

        if (e.getDamager() instanceof Player) {
            Player hitted = (Player) e.getDamager();
            if (Data.frozen.contains(hitted.getName())) {
                hitted.sendMessage(lang.frozen_pvp_msg);
                e.setCancelled(true);
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
                p.performCommand("playertp");
                e.setCancelled(true);
                return;
            }

            if (p.getInventory().getTitle().equals(lang.stafflist_GUI_title)) {
                e.setCancelled(true);
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
                if (Data.frozen.contains(user[1])) {
                    e.setCancelled(false);
                } else {
                    e.setCancelled(true);
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
        if (Data.frozen.contains(e.getPlayer().getName())) {
            /*ItemStack item = this.plugin.helmet.get(e.getPlayer());
            e.getPlayer().getInventory().setHelmet(item);*/
        }
        if (!Data.staff_players.contains(e.getPlayer().getName()))
            return;
        Data.staff_players.remove(e.getPlayer().getName());
        items.rehabInventory(e.getPlayer());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getPlayer();

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
