package net.ucrafts.announcement.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AdUpdateTask extends BukkitRunnable
{


    @Override
    public void run()
    {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "anncm reload");
    }
}
