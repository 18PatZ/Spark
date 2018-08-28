package com.patrickzhong.spark.command;

import com.patrickzhong.spark.SparkPlugin;
import com.patrickzhong.spark.util.CC;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;

public class SparkCommand implements TabCompleter, CommandExecutor {

    @Getter private CommandInfo info = getClass().isAnnotationPresent(CommandInfo.class) ? getClass().getAnnotation(CommandInfo.class) : null;

    protected List<String> onTabComplete(CommandSender sender, String[] args){
        return null;
    }

    protected void onCommand(CommandSender sender, String[] args){}
    protected void onCommand(Player player, String[] args){}
    protected void onCommand(ConsoleCommandSender sender, String[] args){}
    protected void onCommand(BlockCommandSender sender, String[] args){}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        try {
            onCommand(sender, strings);
            if (sender instanceof Player)
                onCommand((Player) sender, strings);
            else if (sender instanceof ConsoleCommandSender)
                onCommand((ConsoleCommandSender) sender, strings);
            else if (sender instanceof BlockCommandSender)
                onCommand((BlockCommandSender) sender, strings);
        }
        catch (CommandException e){
            sender.sendMessage(CC.Poor + CC.translate(e.getMessage()));
        }

        return true;
    }

    protected void checkArgs(String[] args, int check, String msg){
        if(args.length < check)
            throw new CommandException(msg);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return onTabComplete(commandSender, strings);
    }

    public void register(SparkPlugin plugin){
        plugin.registerCommand(this);
    }

    public Player getPlayer(String s){
        return getPlayer(s, "Player not found.");
    }

    public int getInt(String s){
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new CommandException("The amount must be a number!");
        }
    }

    public int getIntStrict(String s){
        int num = getInt(s);
        if(num <= 0)
            throw new CommandException("The amount must be positive!");
        return num;
    }

    public Player getPlayer(String s, String msg){
        Player player = Bukkit.getPlayer(s);
        if(player == null)
            throw new CommandException(msg);
        return player;
    }
}
