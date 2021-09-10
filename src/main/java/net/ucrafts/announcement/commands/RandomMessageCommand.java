package net.ucrafts.announcement.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import net.ucrafts.announcement.Utils;
import net.ucrafts.announcement.managers.AdManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandAlias("anncm")
public class RandomMessageCommand extends BaseCommand {


    private final AdManager adManager;


    public RandomMessageCommand(AdManager adManager) {
        this.adManager = adManager;
    }


    @Subcommand("random")
    public void onReload(CommandSender sender) {
        if (sender.isOp() && this.adManager.hasMessages()) {
            Bukkit.broadcastMessage(Utils.colorize(this.adManager.getRandomMessage()));
        }
    }
}
