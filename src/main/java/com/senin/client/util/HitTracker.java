package com.senin.client.util;

import com.senin.client.config.Config;
import net.minecraft.entity.LivingEntity;

public class HitTracker {
    public static LivingEntity lastTarget = null;
    public static long lastHitTime = 0L;
    public static int comboCount = 0;
    public static long comboLastHit = 0L;
    public static LivingEntity comboLastTarget = null;

    public static void onHit(LivingEntity target) {
        lastTarget = target;
        lastHitTime = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        if (target != comboLastTarget || (now - comboLastHit) > (Config.comboTimeout * 1000L)) comboCount = 1;
        else comboCount++;
        comboLastTarget = target;
        comboLastHit = now;
    }

    public static boolean isActive() {
        if (lastTarget == null || lastTarget.isRemoved()) return false;
        return (System.currentTimeMillis() - lastHitTime) < (Config.targetDuration * 1000L);
    }

    public static float getRemainingAlpha() {
        if (!isActive()) return 0f;
        long elapsed = System.currentTimeMillis() - lastHitTime;
        long total = (long)(Config.targetDuration * 1000L);
        return 1f - (float) elapsed / total;
    }

    public static boolean isComboActive() {
        return comboCount > 0 && (System.currentTimeMillis() - comboLastHit) < (Config.comboTimeout * 1000L);
    }
}
