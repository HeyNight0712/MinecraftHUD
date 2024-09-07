package Heyblock0712.minecraftHUD.utils.hud;

import Heyblock0712.minecraftHUD.api.bossbar.BossBarHUD;

import java.util.HashSet;
import java.util.Set;

public class BossBarManager {
    private static final Set<BossBarHUD> bossBars = new HashSet<>();

    public static void addBlockBossBar(BossBarHUD bossBarHUD) {
        bossBars.add(bossBarHUD);
    }

    public static Set<BossBarHUD> getBlockBossBars() {
        return bossBars;
    }
}
