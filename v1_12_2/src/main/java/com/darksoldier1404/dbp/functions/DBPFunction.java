package com.darksoldier1404.dbp.functions;

import com.darksoldier1404.dbp.BackPack;
import com.darksoldier1404.dppc.utils.DataContainer;
import com.darksoldier1404.dppc.utils.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("all")
public class DBPFunction {
    private static final BackPack plugin = BackPack.getInstance();
    private static final DataContainer data = plugin.data;

    public static void setCoupon(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "백팩 쿠폰 설정");
        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        for(int i = 0; i < 27; i++) {
            inv.setItem(i, pane);
        }
        inv.setItem(13, data.getConfig().getItemStack("Settings.coupon"));
        p.openInventory(inv);
    }

    public static void saveCoupon(Player p, Inventory inv) {
        ItemStack coupon = inv.getItem(13);
        data.getConfig().set("Settings.coupon", coupon);
        data.save();
        p.sendMessage(data.getPrefix() + "§a쿠폰이 설정되었습니다.");
    }

    public static void getCoupon(CommandSender sender, String ssize, String target) {
        if(data.getConfig().getItemStack("Settings.coupon") == null) {
            sender.sendMessage(data.getPrefix() + "§c쿠폰이 설정되지 않았습니다.");
            return;
        }
        Player targetPlayer = plugin.getServer().getPlayer(target);
        if(targetPlayer == null) {
            sender.sendMessage(data.getPrefix() + "§c그 플레이어는 서버에 접속해있지 않습니다.");
            return;
        }
        int size = 0;
        try{
            size = Integer.parseInt(ssize);
        }catch (NumberFormatException e) {
            sender.sendMessage(data.getPrefix() + "§c크기는 숫자로 입력해주세요.");
            return;
        }
        if(size < 1) {
            sender.sendMessage(data.getPrefix() + "§c크기는 1이상이어야 합니다.");
            return;
        }
        if(size > 6) {
            sender.sendMessage(data.getPrefix() + "§c크기는 6이하이어야 합니다.");
            return;
        }
        ItemStack coupon = data.getConfig().getItemStack("Settings.coupon");
        if(targetPlayer.getInventory().firstEmpty() == -1) {
            sender.sendMessage(data.getPrefix() + "§c" + targetPlayer.getName() + "님의 인벤토리가 꽉 찼습니다.");
            return;
        }
        targetPlayer.getInventory().addItem(NBT.setInventoryTag(NBT.setIntTag(coupon, "dbp_size", size), Bukkit.createInventory(null, size * 9, "백팩 쿠폰"), "dbp_backpack"));
        sender.sendMessage(data.getPrefix() + "§a" + targetPlayer.getName() + "님에게 쿠폰을 지급했습니다.");
    }

    public static void openBackPack(Player p, ItemStack item) {
        p.openInventory(NBT.getInventoryTag(item, "dbp_backpack"));
    }

    public static void saveBackPack(Player p, ItemStack item, Inventory inv) {
        p.getInventory().setItemInMainHand(NBT.setInventoryTag(item, inv, "dbp_backpack"));
    }
}
