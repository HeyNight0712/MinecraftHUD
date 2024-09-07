package Heyblock0712.minecraftHUD.api.bossbar;

import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public interface BossBarHUD {

    boolean run(BossBarEvent event);

    class BossBarEvent {
        private final BossBar bossBar;
        private final Block targetBlock;
        private final Entity targetEntity;

        public BossBarEvent(BossBar bossBar, Block block, Entity entity) {
            this.bossBar = bossBar;
            this.targetBlock = block;
            this.targetEntity = entity;
        }

        public BossBar getBossBar() {return bossBar;}
        public Block getTargetBlock() {return targetBlock;}
        public Entity getTargetEntity() {return targetEntity;}
    }
}
