package com.darksoldier1404.dbp;

import com.darksoldier1404.dbp.commands.DBPCommand;
import com.darksoldier1404.dbp.events.DBPEvent;
import com.darksoldier1404.dppc.utils.DataContainer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BackPack extends JavaPlugin {
    private static BackPack plugin;
    public static DataContainer data;
    public static Set<UUID> currentBackPack = new HashSet<>();

    public static BackPack getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        data = new DataContainer(plugin);
        plugin.getServer().getPluginManager().registerEvents(new DBPEvent(), plugin);
        getCommand("dbp").setExecutor(new DBPCommand());
    }

    @Override
    public void onDisable() {
    }
}
