package com.patrickzhong.spark.file;

import com.patrickzhong.spark.util.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Config {

    private File configFile;

    private YamlConfiguration config;

    @Getter
    @Setter
    private boolean changed = false;

    public Config(Plugin plugin, HashMap<String, Object> defaults, String name){

        if(!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        configFile = new File(plugin.getDataFolder(), name+".yml");
        if(!configFile.exists()){
            try {
                configFile.createNewFile();
            } catch (IOException ignored) {
            }
        }

        load();

        if(defaults != null){
            for(String path : defaults.keySet())
                config.addDefault(path, defaults.get(path));
            config.options().copyDefaults(true);
            save();
        }

        int delay = 5 * 60 * 20;

        new BukkitRunnable(){
            @Override
            public void run() {
                if(isChanged())
                    save();
            }
        }.runTaskTimerAsynchronously(plugin, delay, delay);

        Bukkit.getConsoleSender().sendMessage(CC.translate("&c[SPARK] &7YAML file &e" + name + ".yml&7 registered."));

    }

    public YamlConfiguration getFile(){
        return config;
    }

    public void save(){
        try {
            config.save(configFile);
            changed = false;
        }
        catch (Exception ignored){
        }
    }

    public void load(){
        config = YamlConfiguration.loadConfiguration(configFile);
    }

}
