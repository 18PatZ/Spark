package com.patrickzhong.spark.gui;

import com.patrickzhong.spark.util.CC;
import com.patrickzhong.spark.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIItem {

    @Getter private Material type;
    @Getter private int data = 0;
    @Getter private int amount = 1;
    @Getter private String name = null;
    @Getter private List<String> lore = new ArrayList<>();
    @Getter private ItemFlag[] flags = new ItemFlag[0];
    @Getter private HashMap<Enchantment, Integer> enchants = new HashMap<>();

    @Getter private ItemStack item;

    @Getter private Runnable leftClick;
    @Getter private Runnable rightClick;
    @Getter private Runnable click;

    private String skullOwner;

    private int slot;
    private SparkGUI gui;

    public GUIItem(int slot, SparkGUI gui){
        this.slot = slot;
        this.gui = gui;
    }

    public GUIItem type(Material type){
        this.type = type;
        return this;
    }

    public GUIItem data(int data){
        this.data = data;
        return this;
    }

    public GUIItem amount(int amount){
        this.amount = amount;
        return this;
    }

    public GUIItem name(String name){
        this.name = name;
        return this;
    }

    public GUIItem lore(String... lore){
        for (String s : lore)
            this.lore.add(CC.translate(s));
        return this;
    }

    public GUIItem lore(List<String> lore){
        lore.forEach(s -> this.lore.add(CC.translate(s)));
        return this;
    }

    public GUIItem flag(ItemFlag... flags){
        this.flags = flags;
        return this;
    }

    public GUIItem enchant(Enchantment enchant, int level){
        enchants.put(enchant, level);
        return this;
    }

    public GUIItem leftClick(Runnable r){
        leftClick = r;
        return this;
    }

    public GUIItem rightClick(Runnable r){
        rightClick = r;
        return this;
    }

    public GUIItem click(Runnable r){
        click = r;
        return this;
    }

    public GUIItem skullOwner(String s){
        this.skullOwner = s;
        return this;
    }

    public GUIItem item(ItemStack item){
        this.item = item;
        return this;
    }

    public void build(){

        if(item == null) {
            if (skullOwner != null) {
                type = Material.SKULL_ITEM;
                data = 3;
            }

            item = new ItemStack(type, amount, (byte) data);
            ItemMeta im = item.getItemMeta();
            if (name != null)
                im.setDisplayName(CC.translate(name));
            if (lore != null)
                im.setLore(lore);

            if (skullOwner != null)
                ((SkullMeta) im).setOwner(skullOwner);

            im.addItemFlags(flags);
            item.setItemMeta(im);
        }
        enchants.keySet().forEach(e -> item.addUnsafeEnchantment(e, enchants.get(e)));

        gui.getInventory().setItem(slot, item);
    }

}
