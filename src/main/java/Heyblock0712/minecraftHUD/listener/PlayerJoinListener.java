package Heyblock0712.minecraftHUD.listener;

import Heyblock0712.minecraftHUD.MinecraftHUD;
import Heyblock0712.minecraftHUD.api.event.BossBarEvent;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        BossBar bossBar = BossBar.bossBar(
                Component.text("Unknown"),
                0f,
                BossBar.Color.PURPLE,
                BossBar.Overlay.PROGRESS
        );

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                BossBarEvent bossBarEvent = new BossBarEvent(player, bossBar);
                MinecraftHUD.getInstance().getServer().getPluginManager().callEvent(bossBarEvent);
            }
        }.runTaskTimer(MinecraftHUD.getInstance(), 0L, 5L);
    }
}
