package net.ucrafts.announcement.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldListener implements Listener
{


    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(final WorldLoadEvent event)
    {
        event.getWorld().setGameRuleValue("announceAdvancements", "false");
    }
}
