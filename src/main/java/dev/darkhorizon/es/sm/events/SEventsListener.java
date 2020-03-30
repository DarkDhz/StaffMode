package dev.darkhorizon.es.sm.events;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.config.Perms;
import dev.darkhorizon.es.sm.data.Data;
import dev.darkhorizon.es.sm.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class SEventsListener implements Listener {
    private static final Main plugin = Main.getPlugin(Main.class);
    private final Items item = Items.getInstance();



    @EventHandler
    public void onDestroy(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (Data.staff_players.contains(e.getPlayer().getName())) {
            if (!(Perms.can_destroy)) {
                p.sendMessage(Lang.staff_no_break);
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
                String msg = Lang.staff_spawner_break;
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
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (Data.staff_players.contains(e.getPlayer().getName())) {
            if (!(Perms.can_place)) {
                p.sendMessage(Lang.staff_no_place);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void getDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player hitted = (Player) e.getEntity();
            if (Data.staff_players.contains(hitted.getPlayer().getName())) {
                e.setCancelled(true);
            }
        }

        if (e.getDamager() instanceof Player) {
            Player hitted = (Player) e.getEntity();
            if (hitted.hasPermission(Perms.main_permission)) {
                switch (Perms.can_pvp) {
                    case ALL:
                        e.setCancelled(false);
                        break;
                    case ONLY_STAFFMODE:
                        if (Data.staff_players.contains(hitted.getPlayer().getName())) {
                            hitted.sendMessage(Lang.staff_no_pvp);
                            e.setCancelled(true);
                        }
                        break;
                    case NEVER:
                        hitted.sendMessage(Lang.staff_no_pvp);
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

        ItemStack item = e.getCurrentItem();

        if (item.getType() == Material.SKULL_ITEM && item.hasItemMeta() && item.getItemMeta().getDisplayName().contains(Lang.freeze_title)) {
            e.setCancelled(true);
            return;
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
                e.getPlayer().sendMessage(Lang.staff_no_drop);
                e.setCancelled(true);
            }

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
        item.rehabInventory(e.getPlayer());
    }

}
