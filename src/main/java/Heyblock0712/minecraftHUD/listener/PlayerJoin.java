package Heyblock0712.minecraftHUD.listener;

import Heyblock0712.minecraftHUD.MinecraftHUD;
import Heyblock0712.minecraftHUD.api.event.BossBarEvent;
import Heyblock0712.minecraftHUD.api.utils.Format;
import Heyblock0712.minecraftHUD.utils.LangLoader;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
                Component.text("你正在看 : 未知"),
                1.0f,
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

                if (!bossBarEvent.isHandled()) {
                    Block targetBlock = player.getTargetBlock(null, 5);
                    Entity targetEntity = player.getTargetEntity(5);

                    if (targetEntity != null) {
                        handleTargetEntity(player, bossBar, targetEntity);
                    } else if (targetBlock.getType() != Material.AIR) {
                        handleTargetBlock(player, bossBar, targetBlock);
                    } else {
                        player.hideBossBar(bossBar);
                    }
                }
            }
        }.runTaskTimer(MinecraftHUD.getInstance(), 0L, 5L);
    }

    private void handleTargetEntity(Player player, BossBar bossBar, Entity targetEntity) {
        if (targetEntity instanceof LivingEntity livingEntity) {
            double currentHealth = livingEntity.getHealth();
            float healthRatio;

            AttributeInstance maxHealthAttribute = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (maxHealthAttribute != null) {
                double maxHealth = maxHealthAttribute.getValue();
                healthRatio = (float) (currentHealth / maxHealth);
            } else {
                healthRatio = 1.0f;
            }

            String formatEntityName = Format.formatEntityName(targetEntity.getName());
            String entityName = LangLoader.findFullKey(formatEntityName);

            Component message = Component.text()
                    .append(Component.text(entityName + "  "))
                    .append(Component.text("♥ ").color(NamedTextColor.RED))
                    .append(Component.text((int) currentHealth))
                    .build();

            bossBar.overlay(BossBar.Overlay.NOTCHED_20);
            bossBar.color(BossBar.Color.RED);
            bossBar.progress(healthRatio);
            bossBar.name(message);
            player.showBossBar(bossBar);
        }
    }

    private void handleTargetBlock(Player player, BossBar bossBar, Block targetBlock) {
        bossBar.color(BossBar.Color.GREEN);
        bossBar.progress(1.0f);
        bossBar.name(Component.text(LangLoader.findFullKey(targetBlock.getType().name().toLowerCase())));
        player.showBossBar(bossBar);
    }
}
