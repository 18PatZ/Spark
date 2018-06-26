package com.patrickzhong.spark;

import com.patrickzhong.spark.command.CommandInfo;
import com.patrickzhong.spark.command.SparkCommand;
import com.patrickzhong.spark.util.CC;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class SparkPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage(CC.translate("&c[SPARK] &7Enabled Spark hook &b&l" + getClass().getName()));

    }

    public void registerCommand(SparkCommand cmd){

        CommandInfo info = cmd.getInfo();
        if(info == null)
            throw new IllegalArgumentException("The command " + cmd.getClass().getName() + " cannot be registered as it has no CommandInfo annotation!");

        PluginCommand pcmd = getCommand(info.name());

        if(pcmd == null){

            // inject into command map

            try {
                Constructor cmdConst = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
                cmdConst.setAccessible(true);
                pcmd = (PluginCommand) cmdConst.newInstance(info.name(), this);

                pcmd.setAliases(Arrays.asList(info.aliases()));
                pcmd.setDescription(info.description());
                pcmd.setUsage(info.usage());

                Field cmdMap = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
                cmdMap.setAccessible(true);
                CommandMap map = (CommandMap) cmdMap.get(Bukkit.getPluginManager());

                map.register(info.name(), pcmd);

            } catch (NoSuchMethodException | IllegalAccessException |
                    InstantiationException | InvocationTargetException |
                    NoSuchFieldException e) {
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(CC.translate("&c[SPARK] Failed to register /" + info.name() + "."));
            }

        }

        pcmd.setExecutor(cmd);
        pcmd.setTabCompleter(cmd);

        Bukkit.getConsoleSender().sendMessage(CC.translate("&c[SPARK] &7Command &e/" + info.name() + "&7 registered."));

    }

}


