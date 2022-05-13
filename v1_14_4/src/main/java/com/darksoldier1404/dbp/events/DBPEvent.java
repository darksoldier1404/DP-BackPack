package com.darksoldier1404.dbp.events;

import com.darksoldier1404.dbp.BackPack;
import com.darksoldier1404.dbp.functions.DBPFunction;
import com.darksoldier1404.dppc.utils.NBT;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

@SuppressWarnings("all")
public class DBPEvent implements Listener {
    private final BackPack plugin = BackPack.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(plugin.currentBackPack.contains(p.getUniqueId())) {
            if(e.getClick() == ClickType.NUMBER_KEY) {
                e.setCancelled(true);
                return;
            }
        }
        if(e.getCurrentItem() != null) {
            if(NBT.hasTagKey(e.getCurrentItem(), "dbp_size")) {
                if(plugin.currentBackPack.contains(p.getUniqueId())) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
        if(e.getView().getTitle().contains("백팩 쿠폰 설정")) {
            if(e.getSlot() != 13 && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(plugin.currentBackPack.contains(e.getPlayer().getUniqueId())) {
            DBPFunction.saveBackPack((Player) e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand(), e.getInventory());
            plugin.currentBackPack.remove(e.getPlayer().getUniqueId());
            return;
        }
        if(e.getView().getTitle().contains("백팩 쿠폰 설정")) {
            DBPFunction.saveCoupon((Player) e.getPlayer(), e.getInventory());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getHand() == EquipmentSlot.OFF_HAND) return;
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getItem() != null) {
                if(NBT.hasTagKey(e.getItem(), "dbp_size")) {
                    DBPFunction.openBackPack(e.getPlayer(), e.getItem());
                    plugin.currentBackPack.add(e.getPlayer().getUniqueId());
                    e.setCancelled(true);
                }
            }
        }
    }
}
