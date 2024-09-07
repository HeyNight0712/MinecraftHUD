package Heyblock0712.minecraftHUD;

import Heyblock0712.minecraftHUD.api.listeners.BossBarListener;
import Heyblock0712.minecraftHUD.listener.PlayerJoin;
import Heyblock0712.minecraftHUD.utils.LangLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftHUD extends JavaPlugin {
    private static MinecraftHUD instance;

    @Override
    public void onEnable() {
        instance = this;

        LangLoader.loadLangJsonFromUrl("https://raw.githubusercontent.com/InventivetalentDev/minecraft-assets/24w35a/assets/minecraft/lang/zh_tw.json");

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new BossBarListener(), this);

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static MinecraftHUD getInstance() {return instance;}
}
