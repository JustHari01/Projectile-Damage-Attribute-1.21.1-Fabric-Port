package net.projectile_damage;

import net.fabricmc.loader.api.FabricLoader;

public final class Platform {
    private Platform() {
    }

    public static boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}
