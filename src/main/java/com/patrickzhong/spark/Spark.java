package com.patrickzhong.spark;

import com.patrickzhong.spark.util.CC;
import com.patrickzhong.spark.util.TitleUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Spark extends JavaPlugin {

    @Override
    public void onEnable() {

        new TitleUtil();

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
