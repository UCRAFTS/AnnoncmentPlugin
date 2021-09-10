package net.ucrafts.announcement;

import de.leonhard.storage.Json;
import de.leonhard.storage.internal.FlatFile;
import lombok.Getter;
import net.ucrafts.announcement.types.ConfigType;

public class Config {

    @Getter
    private final FlatFile config;

    public Config(AnnouncementPlugin plugin) {
        this.config = new Json("config", plugin.getDataFolder().getPath());
    }

    public void init() {
        this.config.setDefault(ConfigType.DB_TABLES_PREFIX.getName(), "announcement");
        this.config.setDefault(ConfigType.DB_UPDATED_PERIOD.getName(), 60);
        this.config.setDefault(ConfigType.SERVER.getName(), "global");
        this.config.setDefault(ConfigType.RELOAD_MESSAGE.getName(), "§aПлагин успешно перезагружен!");
        this.config.setDefault(ConfigType.FEATURE_AD_ENABLE.getName(), true);
        this.config.setDefault(ConfigType.FEATURE_AD_PERIOD.getName(), 5);
        this.config.setDefault(ConfigType.FEATURE_DEATH_ENABLE.getName(), true);
        this.config.setDefault(ConfigType.FEATURE_DEATH_PVP_RADIUS_NOTIFY.getName(), 100);
        this.config.setDefault(ConfigType.FEATURE_DEATH_PVP_FORMAT.getName(), " §l§6⚔ §l§6Игрок §f%s §6был убит игроком §l§f%s");
        this.config.setDefault(ConfigType.FEATURE_DEATH_LOCATION_FORMAT.getName(), " §l§6☠ §l§6Локация Вашей смерти: §7%s, %s, %s, %s");
        this.config.setDefault(ConfigType.FEATURE_JOIN_ENABLE.getName(), true);
        this.config.setDefault(ConfigType.FEATURE_JOIN_ACTION_BAR.getName(), true);
        this.config.setDefault(ConfigType.FEATURE_JOIN_FORMAT.getName(), "§8Игрок §7%s §aзашел §8на сервер");
        this.config.setDefault(ConfigType.FEATURE_JOIN_LEFT_FORMAT.getName(), "§8Игрок §7%s §cпокинул сервер");
        this.config.setDefault(ConfigType.FEATURE_FEATURE_HIDE_ACHIEVEMENTS_ENABLE.getName(), true);
    }

}
