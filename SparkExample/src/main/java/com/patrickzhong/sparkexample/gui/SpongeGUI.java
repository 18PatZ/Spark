package com.patrickzhong.sparkexample.gui;

import com.patrickzhong.spark.gui.SparkGUI;
import com.patrickzhong.spark.util.CC;
import com.patrickzhong.spark.util.RandomUtil;
import com.patrickzhong.spark.util.TitleUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class SpongeGUI extends SparkGUI {

    @Override
    public void open(Player player, Plugin plugin) {
        super.open(player, plugin, "&lSPONGE &8&lEXAMPLE &0&lGUI", 18);
    }

    @Override
    public void redraw() {

        // YOU MUST CALL THIS FOR THE GUI TO WORK PROPERLY!
        super.redraw();

        List<Integer> slots = new ArrayList<>();

        for(int i = 0; i < inventory.getSize(); i++)
            slots.add(i);

        for(int i = 0; i < 8; i++){
            item(slots.remove(RandomUtil.getRandom().nextInt(slots.size())))
                    .type(Material.SPONGE)
                    .name("&e&lSponge &b&l#" + (i + 1))
                    .lore("&e&l> &7This is a &e&l&nsponge&7!", "", "&e&l> &7Click to &creshuffle&7 the sponges.", "&e&l> &7Right-click for a &bsurprise&7.")
                    .leftClick(() -> redraw())
                    .rightClick(() -> {
                        player.closeInventory();
                        TitleUtil.sendTitle(player, 5, 60, 20, CC.AquaB+"SPAGHETTI!", CC.translate("&7Skoot &eskoot &7skoot"));
                    })
                    .build();
        }

    }

}
