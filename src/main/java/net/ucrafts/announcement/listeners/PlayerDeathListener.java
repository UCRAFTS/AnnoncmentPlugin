package net.ucrafts.announcement.listeners;

import net.ucrafts.announcement.AnnouncementPlugin;
import net.ucrafts.announcement.Config;
import net.ucrafts.announcement.Utils;
import net.ucrafts.announcement.types.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {


    private final Config config;


    public PlayerDeathListener(Config config) {
        this.config = config;
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        event.setDeathMessage(null);

        Player player = event.getEntity();
        Location location = player.getLocation();
        int radius = this.config.getConfig().getInt(ConfigType.FEATURE_DEATH_PVP_RADIUS_NOTIFY.getName());

        if (player.getLastDamageCause() != null && player.getLastDamageCause().getEntity() instanceof Player) {
            Player killer = player.getKiller();

            if (killer != null) {
                String message = String.format(
                        this.config.getConfig().getString(ConfigType.FEATURE_DEATH_PVP_FORMAT.getName()), player.getName(), killer.getName()
                );

                Object[] nearbyPlayers = player.getNearbyEntities(radius, radius, radius).stream().filter(entity -> entity instanceof Player).toArray();

                Bukkit.getScheduler().runTaskAsynchronously(AnnouncementPlugin.getInstance(), () -> {
                    for (Object nearbyPlayer : nearbyPlayers) {
                        ((Player) nearbyPlayer).sendMessage(Utils.colorize(message));
                    }
                });
            }
        }

        player.sendMessage(
                Utils.colorize(String.format(
                        this.config.getConfig().getString(ConfigType.FEATURE_DEATH_LOCATION_FORMAT.getName()),
                        location.getWorld().getName(),
                        (int) location.getX(),
                        (int) location.getY(),
                        (int) location.getZ()
                ))
        );
    }
}
