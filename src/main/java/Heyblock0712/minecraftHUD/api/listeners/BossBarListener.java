package Heyblock0712.minecraftHUD.api.listeners;

import Heyblock0712.minecraftHUD.api.event.BossBarEvent;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BossBarListener implements Listener {
    @EventHandler
    public void onBossBar(BossBarEvent event) {
        Block block = event.getTargetBlock();

        if (block.getType() != Material.STONE) return;
        event.setHandled(true);

        BossBar bossBar = event.getBossBar();
        bossBar.name(Component.text("測試成功"));
        bossBar.color(BossBar.Color.PINK);

        event.showBossBar(true);
    }
}
