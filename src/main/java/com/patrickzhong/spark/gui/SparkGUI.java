package com.patrickzhong.spark.gui;


import com.patrickzhong.spark.util.CC;
import com.patrickzhong.spark.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public abstract class SparkGUI implements Listener {

    @Getter protected boolean opened = false;
    @Getter protected Player player;
    @Getter protected String title;
    @Getter protected int size;
    @Getter protected Plugin plugin;
    @Getter protected Inventory inventory;

    private HashMap<Integer, GUIItem> map = new HashMap<>();

    public void open(Player player, Plugin plugin){
        open(player, plugin, "Inventory", 36);
    }

    public void open(Player player, Plugin plugin, String title, int size){

        Bukkit.getPluginManager().registerEvents(this, plugin);

        this.plugin = plugin;
        this.player = player;
        this.title = title;
        this.size = size;

        map.clear();

        opened = true;
        inventory = Bukkit.createInventory(player, size, CC.translate(title));

        redraw();

        player.openInventory(inventory);

    }

    public void setBackground(){
        ItemStack item = ItemBuilder.builder().type(Material.STAINED_GLASS_PANE).data(7).flag(ItemFlag.values()).name(" ").build();
        for(int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, item);
    }

    public void redraw(){
        setBackground();
        map.clear();
    }

    protected GUIItem item(int i){
        GUIItem item = new GUIItem(i, this);
        map.put(i, item);
        return item;
    }

    @EventHandler
    public void onClick(InventoryClickEvent ev){
        if (ev.getWhoClicked().equals(player) && ev.getInventory().equals(inventory)){

            ev.setCancelled(true);

            GUIItem item = map.get(ev.getRawSlot());
            if (item != null){
                if (ev.getClick().isLeftClick())
                    if (item.getLeftClick() != null)
                        item.getLeftClick().run();

                if (ev.getClick().isRightClick())
                    if (item.getRightClick() != null)
                        item.getRightClick().run();

                if (item.getClick() != null)
                    item.getClick().run();
            }

        }
    }

    protected void invalidate(){
        HandlerList.unregisterAll(this);
    }

    protected void close(){
        player.closeInventory();
    }

    @EventHandler
    public void onClose(InventoryCloseEvent ev){
        if(ev.getPlayer().equals(player) && ev.getInventory().equals(inventory)){

            invalidate();

        }
    }

}
