package com.patrickzhong.spark.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class PotionUtil {


    // level is one-indexed
    public static ItemStack getPotion(PotionType type, int level, boolean splash, boolean extraTime){


        ItemStack pot = new ItemStack(splash ? Material.SPLASH_POTION : Material.POTION);

        PotionMeta pm = (PotionMeta) pot.getItemMeta();

        pm.setBasePotionData(new PotionData(type, extraTime, level > 1));

        pot.setItemMeta(pm);
        return pot;


    }

    public static ItemStack p(PotionType type, int level, boolean splash, boolean extraTime){ return getPotion(type, level, splash, extraTime); }
    public static ItemStack p(PotionType type, int level){ return p(type, level, false, false); }
    public static ItemStack p(PotionType type, boolean extraTime){ return p(type, 1, false, extraTime); }
    public static ItemStack pS(PotionType type){ return p(type, 1, true, false); }
    public static ItemStack p(PotionType type){ return p(type, 1); }

}
