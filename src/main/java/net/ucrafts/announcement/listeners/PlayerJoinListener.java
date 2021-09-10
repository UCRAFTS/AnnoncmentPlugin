package net.ucrafts.announcement.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.ucrafts.announcement.AnnouncementPlugin;
import net.ucrafts.announcement.Config;
import net.ucrafts.announcement.Utils;
import net.ucrafts.announcement.types.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;

public class PlayerJoinListener implements Listener {


    private final Config config;


    public PlayerJoinListener(Config config) {
        this.config = config;
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        event.setJoinMessage(null);

        this.sendMessage(event.getPlayer(), true);
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        event.setQuitMessage(null);

        this.sendMessage(event.getPlayer(), false);
    }


    private void sendMessage(Player player, boolean isJoin) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        boolean isActionBar = this.config.getConfig().getBoolean(ConfigType.FEATURE_JOIN_ACTION_BAR.getName());
        String message = String.format(
                this.config.getConfig().getString(
                        isJoin
                                ? ConfigType.FEATURE_JOIN_FORMAT.getName()
                                : ConfigType.FEATURE_JOIN_LEFT_FORMAT.getName()
                ),
                player.getName()
        );

        Bukkit.getScheduler().runTaskAsynchronously(AnnouncementPlugin.getInstance(), () -> {
            for (Player p : players) {
                if (p.equals(player)) {
                    continue;
                }

                p.spigot().sendMessage(
                        isActionBar
                                ? ChatMessageType.ACTION_BAR
                                : ChatMessageType.CHAT,
                        new TextComponent(Utils.colorize(message))
                );
            }
        });
    }
}
