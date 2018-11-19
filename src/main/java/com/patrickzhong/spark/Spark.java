package com.patrickzhong.spark;

import com.patrickzhong.spark.util.CC;
import com.patrickzhong.spark.util.NMSUtil;
import com.patrickzhong.spark.util.TitleUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Spark extends JavaPlugin {

    @Getter private static Spark instance;

    @Override
    public void onEnable() {

        instance = this;

        new TitleUtil();
        new NMSUtil();

        Bukkit.getConsoleSender().sendMessage(CC.translate("&7--------------------------------"));
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(CC.translate("   &c&l&nSPARK &e&k&nENABLED SUCCESSFULLY&7   "));
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7--------------------------------"));


    }

    @Override
    public void onDisable() {


    }

}
