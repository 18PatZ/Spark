package com.patrickzhong.sparkexample.command;

import com.patrickzhong.spark.command.CommandInfo;
import com.patrickzhong.spark.command.SparkCommand;
import com.patrickzhong.spark.util.CC;
import com.patrickzhong.spark.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "echo", aliases = {"print", "out"})
public class EchoCommand extends SparkCommand {

    @Override
    protected void onCommand(Player player, String[] args) {

        checkArgs(args, 1, "/echo <message>");

        Bukkit.broadcastMessage(CC.Good + CC.translate(player.getName() + " says " + TextUtil.combine(args, 0, -1)));

    }

    @Override
    protected void onCommand(ConsoleCommandSender sender, String[] args) {

        checkArgs(args, 1, "/echo <message>");

        Bukkit.broadcastMessage(CC.Welp + CC.translate("Console says " + TextUtil.combine(args, 0, -1)));

    }
}
