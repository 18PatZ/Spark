package com.patrickzhong.spark.util;

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by patrickzhong on 6/26/18.
 */

@Getter
public class ItemBuilder<T extends ItemBuilder<T>> {

    protected Material type;
    protected int data = -1;
    protected int amount = -1;
    protected String name = null;
    protected List<String> lore = new ArrayList<>();
//    protected ItemFlag[] flags = new ItemFlag[0];
    protected HashMap<Enchantment, Integer> enchants = new HashMap<>();
    protected String skullOwner;
    protected String skullOwner64;
    protected Color color;

    protected ItemStack item;
    protected boolean glow = false;

    public static ItemBuilder builder(){
        return new ItemBuilder();
    }

    public T type(Material type){
        this.type = type;
        return (T) this;
    }

    public T data(int data){
        this.data = data;
        return (T) this;
    }

    public T amount(int amount){
        this.amount = amount;
        return (T) this;
    }

    public T name(String name){
        this.name = name;
        return (T) this;
    }

    public T lore(String... lore){
        for (String s : lore) {
            if(!s.contains("\n"))
                this.lore.add(CC.translate(s));
            else {
                String[] split = s.split("\n");
                for (String s1 : split)
                    this.lore.add(CC.translate(s1));
            }
        }
        return (T) this;
    }

    public T lore(List<String> lore){
        lore.forEach(s -> {
            if(!s.contains("\n"))
                this.lore.add(CC.translate(s));
            else {
                String[] split = s.split("\n");
                for (String s1 : split)
                    this.lore.add(CC.translate(s1));
            }
        });
        return (T) this;
    }

//    public T flag(ItemFlag... flags){
//        this.flags = flags;
//        return (T) this;
//    }

    public T enchant(Enchantment enchant, int level){
        enchants.put(enchant, level);
        return (T) this;
    }

    public T skullOwner(String s){
        this.skullOwner = s;
        return (T) this;
    }

    public T skullOwnerBase64(String s){
        this.skullOwner64 = s;
        return (T) this;
    }

    public T item(ItemStack item){
        this.item = item;
        return (T) this;
    }

    public T color(Color color){
        this.color = color;
        return (T) this;
    }

    public T glow(boolean glow){
        this.glow = glow;
        return (T) this;
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

//        if (skullOwner64 != null){
//            SkullMeta headMeta = (SkullMeta) im;
//            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
//            byte[] encodedData = skullOwner64.getBytes();
//            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
//            Field profileField = null;
//            try {
//                profileField = headMeta.getClass().getDeclaredField("profile");
//                profileField.setAccessible(true);
//                profileField.set(headMeta, profile);
//            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
//                e1.printStackTrace();
//            }
//        }
//        else
        if (skullOwner != null)
            ((SkullMeta) im).setOwner(skullOwner);

        if(im instanceof LeatherArmorMeta && color != null)
            ((LeatherArmorMeta) im).setColor(color);

//        im.addItemFlags(flags);
        item.setItemMeta(im);

        enchants.keySet().forEach(e -> item.addUnsafeEnchantment(e, enchants.get(e)));

        if(glow)
            item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);

        return item;
    }

}
