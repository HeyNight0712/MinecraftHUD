package Heyblock0712.minecraftHUD.api.listeners;

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
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class DefaultBossBarListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onBossBar(BossBarEvent event) {
        Entity entity = event.getTargetEntity();
        Block block = event.getTargetBlock();
        BossBar bossBar = event.getBossBar();

        if (entity != null) {
            handleTargetEntity(event, bossBar, entity);
        } else if (block.getType() != Material.AIR) {
            handleTargetBlock(event, bossBar, block);
        } else {
            event.showBossBar(false);
        }
    }

    private void handleTargetEntity(BossBarEvent event, BossBar bossBar, Entity targetEntity) {
        if (targetEntity instanceof LivingEntity livingEntity) {
            double currentHealth = livingEntity.getHealth();
            float healthRatio;

            AttributeInstance maxHealthAttribute = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            String formatEntityName = Format.formatEntityName(targetEntity.getName());
            String entityName = LangLoader.findFullKey(formatEntityName);

            Component message = Component.text()
                    .append(Component.text(entityName))
                    .build();

            if (maxHealthAttribute != null) {
                double maxHealth = maxHealthAttribute.getValue();
                healthRatio = (float) (currentHealth / maxHealth);
                message = Component.text()
                        .append(message)
                        .append(Component.text("     ♥  ").color(NamedTextColor.RED))
                        .append(Component.text((int) currentHealth + " / " + (int) maxHealth))
                        .build();
            } else {
                healthRatio = 0f;
            }

            bossBar.overlay(BossBar.Overlay.NOTCHED_20);
            bossBar.color(BossBar.Color.RED);
            bossBar.progress(healthRatio);
            bossBar.name(message);
            event.showBossBar(true);
        }
    }

    private void handleTargetBlock(BossBarEvent event, BossBar bossBar, Block targetBlock) {
        bossBar.color(BossBar.Color.GREEN);
        bossBar.progress(1.0f);
        bossBar.name(Component.text(LangLoader.findFullKey(targetBlock.getType().name().toLowerCase())));
        event.showBossBar(true);
    }
}
