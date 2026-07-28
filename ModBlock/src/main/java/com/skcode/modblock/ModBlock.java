package com.skcode.modblock;

import org.bukkit.plugin.java.JavaPlugin;

public final class ModBlock extends JavaPlugin {

    private static ModBlock instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new ModBlockListener(this), this);

        getLogger().info("=======================================");
        getLogger().info("  ModBlock запущен! Автор: lnxsn");
        getLogger().info("=======================================");
    }

    @Override
    public void onDisable() {
        getLogger().info("ModBlock выключен.");
    }

    public static ModBlock getInstance() {
        return instance;
    }
}
