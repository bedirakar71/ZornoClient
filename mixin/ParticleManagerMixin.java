package com.senin.client.mixin;

import com.senin.client.config.Config;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    private static long lastReset = 0L;
    private static int count = 0;

    @Inject(method = "addParticle", at = @At("HEAD"), cancellable = true)
    private void senin$limitParticles(Particle particle, CallbackInfo ci) {
        if (!Config.reduceParticles) return;
        long now = System.currentTimeMillis();
        if (now - lastReset > 1000L) { lastReset = now; count = 0; }
        if (count++ > Config.particleLimit) ci.cancel();
    }
}
