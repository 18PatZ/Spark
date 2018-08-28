package com.patrickzhong.spark.util;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by patrickzhong on 8/17/18.
 */
public class NMSUtil {

    @Getter private static NMSUtil instance;

    private String ver;
    private String cbP;
    private String nmP;

    private Class craftItemC;
    private Method getHandle;
    private Class nbtC;
    private Class stackC;
    private Method hasTag;
    private Method getTag;
    private Method asNMSCopy;
    private Method asCraftMirror;

    public NMSUtil(){
        instance = this;

        ver = Bukkit.getServer().getClass().getPackage().getName();
        ver = ver.substring(ver.lastIndexOf(".") + 1);

        nmP = "net.minecraft.server." + ver + ".";
        cbP = "org.bukkit.craftbukkit." + ver + ".";

        try {
            craftItemC = Class.forName(cbP + "inventory.CraftItemStack");
            asNMSCopy = craftItemC.getMethod("asNMSCopy", ItemStack.class);
//            getHandle = craftItemC.getMethod("getHandle");
            nbtC = Class.forName(nmP + "NBTTagCompound");
            stackC = Class.forName(nmP + "ItemStack");
            hasTag = stackC.getMethod("hasTag");
            getTag = stackC.getMethod("getTag");

            asCraftMirror = craftItemC.getMethod("asCraftMirror", stackC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getStack(ItemStack item) throws InvocationTargetException, IllegalAccessException {
        return asNMSCopy.invoke(null, item);
    }

    public ItemStack attachItemTag(ItemStack item, String key, Object value){

        try {
            Object stack = getStack(item);

            Object tag = hasTag(stack) ? getTag.invoke(stack) : nbtC.getConstructor().newInstance();

            if(value instanceof Integer)
                nbtC.getMethod("setInt", String.class, int.class).invoke(tag, key, value);
            else if(value instanceof String)
                nbtC.getMethod("setString", String.class, String.class).invoke(tag, key, value);
            else if(value instanceof Double)
                nbtC.getMethod("setDouble", String.class, double.class).invoke(tag, key, value);
            else if(value instanceof Boolean)
                nbtC.getMethod("setBoolean", String.class, boolean.class).invoke(tag, key, value);
            else if(value instanceof int[])
                nbtC.getMethod("setIntArray", String.class, int[].class).invoke(tag, key, value);
            else if(value instanceof Byte)
                nbtC.getMethod("setByte", String.class, byte.class).invoke(tag, key, value);
            else if(value instanceof Long)
                nbtC.getMethod("setLong", String.class, long.class).invoke(tag, key, value);
            else
                throw new IllegalArgumentException("Only integer, String, double, boolean, int array, byte, and long values are currently supported.");

            stackC.getMethod("setTag", nbtC).invoke(stack, tag);

            return (ItemStack) asCraftMirror.invoke(null, stack);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }

    private boolean hasTag(Object object){
        try {
            return (boolean) hasTag.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasItemTag(ItemStack item, String key){
        if(item == null) return false;

        try {
            Object stack = getStack(item);

            if(!hasTag(stack)) return false;

            Object tag = getTag.invoke(stack);
            if(tag == null) return false;

            return (boolean) nbtC.getMethod("hasKey", String.class).invoke(tag, key);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getItemTagInt(ItemStack item, String key, int def){
        if(item == null) return def;

        try {
            Object stack = getStack(item);

            if(!hasTag(stack)) return def;

            Object tag = getTag.invoke(stack);
            if(tag == null) return def;

            return (int) nbtC.getMethod("getInt", String.class).invoke(tag, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return def;
    }

    public String getItemTagString(ItemStack item, String key, String def){
        if(item == null) return def;

        try {
            Object stack = getStack(item);

            if(!hasTag(stack)) return def;

            Object tag = getTag.invoke(stack);
            if(tag == null) return def;

            return (String) nbtC.getMethod("getString", String.class).invoke(tag, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return def;
    }

    public double getItemTagDouble(ItemStack item, String key, double def){
        if(item == null) return def;

        try {
            Object stack = getStack(item);

            if(!hasTag(stack)) return def;

            Object tag = getTag.invoke(stack);
            if(tag == null) return def;

            return (double) nbtC.getMethod("getDouble", String.class).invoke(tag, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return def;
    }

    public int[] getItemTagIntArray(ItemStack item, String key, int... def){
        if(item == null) return def;

        try {
            Object stack = getStack(item);

            if(!hasTag(stack)) return def;

            Object tag = getTag.invoke(stack);
            if(tag == null) return def;

            return (int[]) nbtC.getMethod("getIntArray", String.class).invoke(tag, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return def;
    }

    public byte getItemTagByte(ItemStack item, String key, byte def){
        if(item == null) return def;

        try {
            Object stack = getStack(item);

            if(!hasTag(stack)) return def;

            Object tag = getTag.invoke(stack);
            if(tag == null) return def;

            return (byte) nbtC.getMethod("getByte", String.class).invoke(tag, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return def;
    }

    public long getItemTagLong(ItemStack item, String key, long def){
        if(item == null) return def;

        try {
            Object stack = getStack(item);

            if(!hasTag(stack)) return def;

            Object tag = getTag.invoke(stack);
            if(tag == null) return def;

            return (long) nbtC.getMethod("getLong", String.class).invoke(tag, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return def;
    }

    public boolean getItemTagBoolean(ItemStack item, String key, boolean def){
        if(item == null) return def;

        try {
            Object stack = getStack(item);

            if(!hasTag(stack)) return def;

            Object tag = getTag.invoke(stack);
            if(tag == null) return def;

            return (boolean) nbtC.getMethod("getBoolean", String.class).invoke(tag, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return def;
    }

}
