package net.ucrafts.announcement.managers;

import lombok.RequiredArgsConstructor;
import net.ucrafts.announcement.AnnouncementPlugin;
import net.ucrafts.announcement.Config;
import net.ucrafts.announcement.types.ConfigType;
import net.ucrafts.server.pools.jdbc.JdbcHandler;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Random;

@RequiredArgsConstructor
public class AdManager {

    private final Config config;
    private final JdbcHandler dataSource;
    private final HashSet<String> messages = new HashSet<>();

    public void load() {
        this.messages.clear();

        final String query = String.format(
                "SELECT text FROM %s_ad WHERE server = '%s' or server = 'global'",
                this.config.getConfig().getString(ConfigType.DB_TABLES_PREFIX.getName()),
                this.config.getConfig().getString(ConfigType.SERVER.getName())
        );

        Bukkit.getScheduler().runTaskAsynchronously(AnnouncementPlugin.getInstance(), () -> {
            try (Connection connection = this.dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    this.messages.add(result.getString(1));
                }
            } catch (Exception e) {
                throw new RuntimeException("Cant load ad data!");
            }
        });
    }

    public boolean hasMessages() {
        return !this.messages.isEmpty();
    }

    public String getRandomMessage() {
        if (!this.hasMessages()) {
            return null;
        }

        Object[] array = this.messages.toArray();
        int rnd = new Random().nextInt(array.length);

        return String.valueOf(array[rnd]);
    }

}
