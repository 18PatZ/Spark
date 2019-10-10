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
import java.util.Map;

public class Config {

    private File configFile;

    private YamlConfiguration config;

    @Getter
    @Setter
    private boolean changed = false;

    public Config(Plugin plugin, HashMap<String, Object> defaults, String name){
        create(plugin,  name);
        loadDefaults(plugin, defaults, name);
    }

    public Config(Plugin plugin, HashMap<String, Object> defaults, HashMap<String, Object> firstTime, String name){

        boolean newly = create(plugin,  name);
        loadDefaults(plugin, defaults, name);

        if(newly){
            for (Map.Entry<String, Object> entry : firstTime.entrySet())
                config.set(entry.getKey(), entry.getValue());
            save();
        }

    }

    private boolean create(Plugin plugin, String name){ // true if new file

        if(!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();

        File folder = plugin.getDataFolder();

//        if(name.contains("/")) {
//            folder = new File(plugin.getDataFolder(), name.substring(0, name.lastIndexOf("/")));
//
//            if(!folder.exists())
//                folder.mkdir();
//
//            name = name.substring(name.lastIndexOf("/") + 1);
//        }

        configFile = new File(folder, name+".yml");

        if(!configFile.exists()){
            try {
                if(!configFile.getParentFile().exists())
                    configFile.getParentFile().mkdir();
                configFile.createNewFile();
                return true;
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }

        return false;
    }

    private void loadDefaults(Plugin plugin, HashMap<String, Object> defaults, String name){
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
