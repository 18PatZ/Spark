package com.patrickzhong.spark.util;

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

/**
 * Created by patrickzhong on 6/26/18.
 */
public class ItemBuilder {

    private Material type;
    private int data = 0;
    private int amount = 1;
    private String name = null;
    private List<String> lore = new ArrayList<>();
    private ItemFlag[] flags = new ItemFlag[0];
    private HashMap<Enchantment, Integer> enchants = new HashMap<>();
    private String skullOwner;

    private ItemStack item;

    public static ItemBuilder builder(){
        return new ItemBuilder();
    }

    public ItemBuilder type(Material type){
        this.type = type;
        return this;
    }

    public ItemBuilder data(int data){
        this.data = data;
        return this;
    }

    public ItemBuilder amount(int amount){
        this.amount = amount;
        return this;
    }

    public ItemBuilder name(String name){
        this.name = name;
        return this;
    }

    public ItemBuilder lore(String... lore){
        for (String s : lore)
            this.lore.add(CC.translate(s));
        return this;
    }

    public ItemBuilder lore(List<String> lore){
        lore.forEach(s -> this.lore.add(CC.translate(s)));
        return this;
    }

    public ItemBuilder flag(ItemFlag... flags){
        this.flags = flags;
        return this;
    }

    public ItemBuilder enchant(Enchantment enchant, int level){
        enchants.put(enchant, level);
        return this;
    }

    public ItemBuilder skullOwner(String s){
        this.skullOwner = s;
        return this;
    }

    public ItemStack build(){

        if(skullOwner != null){
            type = Material.SKULL_ITEM;
            data = 3;
        }

        item = new ItemStack(type, amount, (byte) data);
        ItemMeta im = item.getItemMeta();
        if(name != null)
            im.setDisplayName(CC.translate(name));
        if(lore != null)
            im.setLore(lore);
        im.addItemFlags(flags);

        if(skullOwner != null)
            ((SkullMeta) im).setOwner(skullOwner);

        item.setItemMeta(im);
        enchants.keySet().forEach(e -> item.addUnsafeEnchantment(e, enchants.get(e)));

        return item;
    }

}
