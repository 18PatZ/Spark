package com.patrickzhong.spark.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by patrickzhong on 6/26/18.
 */
public class ItemBuilder {

    private Material type;
    private int data = -1;
    private int amount = -1;
    private String name = null;
    private List<String> lore = new ArrayList<>();
    private ItemFlag[] flags = new ItemFlag[0];
    private HashMap<Enchantment, Integer> enchants = new HashMap<>();
    private String skullOwner;
    private String skullOwner64;
    private Color color;

    private ItemStack item;
    private boolean glow = false;

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
        for (String s : lore) {
            if(!s.contains("\n"))
                this.lore.add(CC.translate(s));
            else {
                String[] split = s.split("\n");
                for (String s1 : split)
                    this.lore.add(CC.translate(s1));
            }
        }
        return this;
    }

    public ItemBuilder lore(List<String> lore){
        lore.forEach(s -> {
            if(!s.contains("\n"))
                this.lore.add(CC.translate(s));
            else {
                String[] split = s.split("\n");
                for (String s1 : split)
                    this.lore.add(CC.translate(s1));
            }
        });
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

    public ItemBuilder skullOwnerBase64(String s){
        this.skullOwner64 = s;
        return this;
    }

    public ItemBuilder item(ItemStack item){
        this.item = item;
        return this;
    }

    public ItemBuilder color(Color color){
        this.color = color;
        return this;
    }

    public ItemBuilder glow(boolean glow){
        this.glow = glow;
        return this;
    }

    public ItemStack build(){

        if (skullOwner != null) {
            type = Material.SKULL_ITEM;
            data = 3;
        }

        if(item == null)
            item = new ItemStack(type, amount == -1 ? 1 : amount, (byte) (data == -1 ? 0 : data));
        else {
            item = item.clone();

            if(amount > -1)
                item.setAmount(amount);
            if(data > -1)
                item.getData().setData((byte) data);
        }

        ItemMeta im = item.getItemMeta();
        if (name != null)
            im.setDisplayName(CC.translate(name));
        if (lore != null)
            im.setLore(lore);

        if (skullOwner64 != null){
            SkullMeta headMeta = (SkullMeta) im;
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            byte[] encodedData = skullOwner64.getBytes();
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            Field profileField = null;
            try {
                profileField = headMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(headMeta, profile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        else if (skullOwner != null)
            ((SkullMeta) im).setOwner(skullOwner);

        if(im instanceof LeatherArmorMeta && color != null)
            ((LeatherArmorMeta) im).setColor(color);

        item.setItemMeta(im);

        enchants.keySet().forEach(e -> item.addUnsafeEnchantment(e, enchants.get(e)));

        if(glow)
            item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);

        im.addItemFlags(flags);

        return item;
    }

}
