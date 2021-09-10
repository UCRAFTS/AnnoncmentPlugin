package net.ucrafts.announcement;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import net.ucrafts.announcement.commands.RandomMessageCommand;
import net.ucrafts.announcement.commands.ReloadCommand;
import net.ucrafts.announcement.listeners.PlayerDeathListener;
import net.ucrafts.announcement.listeners.PlayerJoinListener;
import net.ucrafts.announcement.listeners.WorldListener;
import net.ucrafts.announcement.managers.AdManager;
import net.ucrafts.announcement.tasks.AdTask;
import net.ucrafts.announcement.tasks.AdUpdateTask;
import net.ucrafts.announcement.types.ConfigType;
import net.ucrafts.server.pools.impl.PoolsPluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(
        name = "AnnouncementPlugin",
        version = "1.0.0"
)
@Author(value = "oDD1 / Alexander Repin")
@Description(value = "Announcement manager plugin")
@Dependency(value = "upools-bukkit")
public class AnnouncementPlugin extends JavaPlugin {

    @Getter
    private static AnnouncementPlugin instance;

    @Getter
    private static AdManager adManager;

    private Config config;

    @Override
    public void onEnable() {
        AnnouncementPlugin.instance = this;

        this.config = new Config(this);
        this.config.init();

        adManager = new AdManager(
                this.config,
                PoolsPluginImpl.getInstance().getJdbcHandler()
        );
        adManager.load();

        this.registerCommands();
        this.registerListeners();
        this.registerTasks();
    }

    private void registerTasks() {
        if (this.config.getConfig().getBoolean(ConfigType.FEATURE_AD_ENABLE.getName())) {
            int notifyPeriod = (this.config.getConfig().getInt(ConfigType.FEATURE_AD_PERIOD.getName()) * 60) * 20;
            int updatePeriod = (this.config.getConfig().getInt(ConfigType.DB_UPDATED_PERIOD.getName()) * 60) * 20;

            new AdTask(AnnouncementPlugin.adManager).runTaskTimerAsynchronously(this, 0, notifyPeriod);
            new AdUpdateTask().runTaskTimer(this, updatePeriod, updatePeriod);
        }
    }

    private void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new ReloadCommand(this.config, AnnouncementPlugin.adManager));
        commandManager.registerCommand(new RandomMessageCommand(AnnouncementPlugin.adManager));
    }

    private void registerListeners() {
        if (this.config.getConfig().getBoolean(ConfigType.FEATURE_JOIN_ENABLE.getName())) {
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this.config), this);
        }

        if (this.config.getConfig().getBoolean(ConfigType.FEATURE_DEATH_ENABLE.getName())) {
            Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this.config), this);
        }

        if (this.config.getConfig().getBoolean(ConfigType.FEATURE_FEATURE_HIDE_ACHIEVEMENTS_ENABLE.getName())) {
            Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
        }
    }

}
