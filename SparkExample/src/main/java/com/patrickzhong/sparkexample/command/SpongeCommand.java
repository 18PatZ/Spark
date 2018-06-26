package com.patrickzhong.sparkexample.command;

import com.patrickzhong.spark.command.CommandInfo;
import com.patrickzhong.spark.command.SparkCommand;
import com.patrickzhong.sparkexample.SparkExample;
import com.patrickzhong.sparkexample.gui.SpongeGUI;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "sponge", aliases = {"spaghet"})
public class SpongeCommand extends SparkCommand {

    @Override
    protected void onCommand(Player player, String[] args) {

        if(args.length == 0)
            new SpongeGUI().open(player, SparkExample.getInstance());
        else if(args[0].equalsIgnoreCase("open")) {
            checkArgs(args, 2, "/sponge open <player>");
            new SpongeGUI().open(getPlayer(args[1]), SparkExample.getInstance());
        }

    }

    @Override
    protected void onCommand(ConsoleCommandSender sender, String[] args) {
        checkArgs(args, 1, "/sponge <player>");
        new SpongeGUI().open(getPlayer(args[0]), SparkExample.getInstance());
    }
}
