package com.patrickzhong.spark.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Created by patrickzhong on 6/27/18.
 */
public class LocationUtil {

    public static String serialize(Location loc){
        if(loc == null) return null;
        return loc.getWorld().getName() + "///" + loc.getX() + "///" + loc.getY() + "///" + loc.getZ() + "///" + loc.getYaw() + "///" + loc.getPitch();
    }

    public static Location deserialize(String s){
        if(s == null) return null;
        String[] split = s.split("///");
        Location l = new Location(Bukkit.getWorld(split[0]), p(split[1]), p(split[2]), p(split[3]));
        l.setYaw(f(split[4]));
        l.setPitch(f(split[5]));
        return l;
    }

    private static double p(String s){
        return Double.parseDouble(s);
    }

    private static float f(String s){
        return Float.parseFloat(s);
    }

}
