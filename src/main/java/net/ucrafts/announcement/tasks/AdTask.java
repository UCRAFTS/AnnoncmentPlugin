package net.ucrafts.announcement.tasks;

import net.ucrafts.announcement.Utils;
import net.ucrafts.announcement.managers.AdManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AdTask extends BukkitRunnable
{


    private final AdManager adManager;


    public AdTask(AdManager adManager)
    {
        this.adManager = adManager;
    }


    @Override
    public void run()
    {
        if (this.adManager.hasMessages()) {
            Bukkit.broadcastMessage(Utils.colorize(this.adManager.getRandomMessage()));
        }
    }
}
