package Heyblock0712.minecraftHUD.api.event;

import Heyblock0712.minecraftHUD.api.utils.MinecraftLangManager;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BossBarEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final BossBar bossBar;
    private boolean handled;

    public BossBarEvent(@NotNull final Player player, @NotNull final BossBar bossBar) {
        this.player = player;
        this.bossBar = bossBar;
        this.handled = false;
    }

    public @NotNull BossBar getBossBar() {return this.bossBar;}

    public void showBossBar(boolean visible) {
        if (visible) {
            player.showBossBar(this.bossBar);
        } else {
            player.hideBossBar(this.bossBar);
        }
    }

    public Block getTargetBlock() {
        return player.getTargetBlock(null, 5);
    }

    public Entity getTargetEntity() {
        return player.getTargetEntity(5);
    }

    public String getTargetLabel() {
        return MinecraftLangManager.toMinecraftLocale(player.locale());
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
