package com.darksoldier1404.dbp.commands;

import com.darksoldier1404.dbp.BackPack;
import com.darksoldier1404.dbp.functions.DBPFunction;
import com.darksoldier1404.dppc.utils.DataContainer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class DBPCommand implements CommandExecutor, TabCompleter {
    private final DataContainer data = BackPack.data;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(data.getPrefix() + "권한이 없습니다.");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(data.getPrefix() + "/dbp 쿠폰설정  - 백팩으로 사용할 아이템을 설정합니다.");
            sender.sendMessage(data.getPrefix() + "/dbp 쿠폰발급 <Size 1~6> (닉네임)");
            return false;
        }
        if (args[0].equalsIgnoreCase("쿠폰설정")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(data.getPrefix() + "게임내에서만 사용할 수 있습니다.");
                return false;
            }
            if (args.length == 1) {
                DBPFunction.setCoupon((Player) sender);
                return false;
            }
        }
        if (args[0].equalsIgnoreCase("쿠폰발급")) {
            if (args.length == 1) {
                sender.sendMessage(data.getPrefix() + "사이즈를 입력해주세요.");
                return false;
            }
            if (args.length == 2) {
                DBPFunction.getCoupon(sender, args[1], sender.getName());
                return false;
            }
            if (args.length == 3) {
                DBPFunction.getCoupon(sender, args[1], args[2]);
                return false;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender.isOp()) {
            if (args.length == 1) {
                return Arrays.asList("쿠폰설정", "쿠폰발급");
            }
        }
        return null;
    }
}
