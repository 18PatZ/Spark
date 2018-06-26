package com.patrickzhong.spark;

import com.patrickzhong.spark.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Spark extends JavaPlugin {

    @Override
    public void onEnable() {


        Bukkit.getConsoleSender().sendMessage(CC.translate("&7--------------------------------"));
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(CC.translate("   &c&l&nSPARK ENABLED SUCCESSFULLY&7   "));
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7--------------------------------"));


    }

    @Override
    public void onDisable() {


    }

}
