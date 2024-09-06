package Heyblock0712.minecraftHUD.listener;

import Heyblock0712.minecraftHUD.MinecraftHUD;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        BossBar bossBar = BossBar.bossBar(
                Component.text("你正在看 :"),
                1.0f,
                BossBar.Color.GREEN,
                BossBar.Overlay.PROGRESS
        );

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                Block targetBlock = player.getTargetBlock(null, 5);
                Entity targetEntity = player.getTargetEntity(5);

                if (targetEntity != null) {
                    bossBar.name(Component.text(targetEntity.getName()));
                    player.showBossBar(bossBar);
                } else if (targetBlock.getType() != Material.AIR) {
                        bossBar.name(Component.text(targetBlock.getType().name()));
                        player.showBossBar(bossBar);
                } else {
                    player.hideBossBar(bossBar);
                }
            }
        }.runTaskTimer(MinecraftHUD.getInstance(), 0L, 5L);
    }
}
