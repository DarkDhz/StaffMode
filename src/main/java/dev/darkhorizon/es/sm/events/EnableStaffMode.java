package dev.darkhorizon.es.sm.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnableStaffMode extends Event {

    private final Player player;


    private static final HandlerList HANDLERS = new HandlerList();

    public EnableStaffMode(Player p) {
        this.player = p;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return this.player;
    }
}
