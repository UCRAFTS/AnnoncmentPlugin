package net.ucrafts.announcement.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import net.ucrafts.announcement.Config;
import net.ucrafts.announcement.Utils;
import net.ucrafts.announcement.managers.AdManager;
import net.ucrafts.announcement.types.ConfigType;
import org.bukkit.command.CommandSender;

@CommandAlias("anncm")
public class ReloadCommand extends BaseCommand
{


    private final Config config;

    private final AdManager adManager;


    public ReloadCommand(Config config, AdManager adManager)
    {
        this.config = config;
        this.adManager = adManager;
    }


    @Subcommand("reload")
    public void onReload(CommandSender sender)
    {
        if (sender.isOp()) {
            // todo: incorrect
            this.config.init();
            this.adManager.load();

            sender.sendMessage(Utils.colorize(this.config.getConfig().getString(ConfigType.RELOAD_MESSAGE.getName())));
        }
    }
}
