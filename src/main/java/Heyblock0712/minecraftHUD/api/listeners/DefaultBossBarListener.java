package Heyblock0712.minecraftHUD.api.listeners;

import Heyblock0712.minecraftHUD.api.event.BossBarEvent;
import Heyblock0712.minecraftHUD.api.utils.MinecraftLangManager;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
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
            String key = "entity.minecraft." + targetEntity.getType();
            String playerTranslation = event.getTargetLabel();

            Component customName = livingEntity.customName();
            TextComponent.Builder builder = Component.text();

            // Name
            if (customName != null) {
                builder.append(customName);
            } else {
                String translation = MinecraftLangManager.getTranslation(playerTranslation, key);
                builder.append(Component.text(translation));
            }

            // HP
            if (maxHealthAttribute != null) {
                double maxHealth = maxHealthAttribute.getValue();
                healthRatio = (float) (currentHealth / maxHealth);
                builder
                        .append(Component.text("     ♥  ").color(NamedTextColor.RED))
                        .append(Component.text((int) currentHealth + " / " + (int) maxHealth));
            } else {
                healthRatio = 0f;
            }

            Component message = builder.build();

            bossBar.overlay(BossBar.Overlay.NOTCHED_20);
            bossBar.color(BossBar.Color.RED);
            bossBar.progress(healthRatio);
            bossBar.name(message);
            event.showBossBar(true);
        }
    }

    private void handleTargetBlock(BossBarEvent event, BossBar bossBar, Block targetBlock) {
        String key = "block.minecraft." + targetBlock.getType();
        String playerTranslation = event.getTargetLabel();
        String translation = MinecraftLangManager.getTranslation(playerTranslation, key);

        bossBar.color(BossBar.Color.GREEN);
        bossBar.progress(1.0f);
        bossBar.name(Component.text(translation));
        event.showBossBar(true);
    }
}
