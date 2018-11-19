package com.patrickzhong.spark.util;

import com.patrickzhong.spark.Spark;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class FireworkUtil {

    public static Firework spawn(Location loc, boolean flicker, boolean trail, FireworkEffect.Type type, Color[] colors, Color[] fades){
        Firework f = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(FireworkEffect.builder()
                .flicker(flicker)
                .trail(trail)
                .withColor(colors)
                .withFade(fades)
                .with(type)
                .build());
        f.setFireworkMeta(fm);

        return f;
    }

    public static Firework spawnRandom(Location loc){
        return spawn(loc, false, true, FireworkEffect.Type.BURST, AU.a(Color.RED, Color.YELLOW), AU.a(Color.AQUA));
    }

    public static void spawnRandom(Location loc, int explode){

        Firework f = spawnRandom(loc);

        new BukkitRunnable(){
            @Override
            public void run() {
                f.detonate();
            }
        }.runTaskLater(Spark.getInstance(), explode);
    }

}
