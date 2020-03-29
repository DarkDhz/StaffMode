package dev.darkhorizon.es.sm.events;

import dev.darkhorizon.es.sm.Main;
import dev.darkhorizon.es.sm.config.Lang;
import dev.darkhorizon.es.sm.items.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class SEventsListener implements Listener {
    private final Main plugin = Main.getPlugin(Main.class);
    private final Items item = Items.getInstance();



    @EventHandler
    public void onBuild(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (plugin.staff_players.contains(e.getPlayer().getName())) {
            p.sendMessage(Lang.staff_no_build);
            e.setCancelled(true);
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
        if (plugin.staff_players.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent e) {
        if (plugin.staff_players.contains(e.getPlayer().getName())) {
            e.getPlayer().sendMessage(Lang.staff_no_drop);
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = e.getEntity();
            if (plugin.staff_players.contains(p.getName())) {
                e.getDrops().clear();
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (this.plugin.frozen.contains(e.getPlayer().getName())) {
            /*ItemStack item = this.plugin.helmet.get(e.getPlayer());
            e.getPlayer().getInventory().setHelmet(item);*/
        }
        if (!plugin.staff_players.contains(e.getPlayer().getName()))
            return;
        this.plugin.staff_players.remove(e.getPlayer().getName());
        item.rehabInventory(e.getPlayer());
    }

}
