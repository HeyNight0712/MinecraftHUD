package Heyblock0712.minecraftHUD;

import Heyblock0712.minecraftHUD.api.listeners.DefaultBossBarListener;
import Heyblock0712.minecraftHUD.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftHUD extends JavaPlugin {
    private static MinecraftHUD instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        getServer().getPluginManager().registerEvents(new DefaultBossBarListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static MinecraftHUD getInstance() {return instance;}
}
