package com.patrickzhong.spark.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by patrickzhong on 6/27/18.
 */
public class LocationUtil {

    public static String serialize(Location loc) {
        return loc == null? null : (loc.getWorld().getName() + "///" + loc.getX() + "///" + loc.getY() + "///" + loc.getZ() + "///" + loc.getYaw() + "///" + loc.getPitch()).replace(".", "<<");
    }

    public static Location deserialize(String s) {
        if(s == null) {
            return null;
        } else {
            String[] split = s.replace("<<", ".").split("///");
            World world = Bukkit.getWorld(split[0]);
            if(world == null) return null;
            Location l = new Location(world, p(split[1]), p(split[2]), p(split[3]));
            l.setYaw(f(split[4]));
            l.setPitch(f(split[5]));
            return l;
        }
    }

    private static double p(String s){
        return Double.parseDouble(s);
    }

    private static float f(String s){
        return Float.parseFloat(s);
    }

}
