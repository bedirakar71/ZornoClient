package com.senin.client.mixin;

import com.senin.client.config.Config;
import net.minecraft.client.particle.CritParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Random;

@Mixin(CritParticle.Factory.class)
public class CritParticleMixin {
    @Inject(method = "createParticle*", at = @At("RETURN"))
    private void senin$crit(DefaultParticleType type, ClientWorld world, double x, double y, double z, double vx, double vy, double vz, Random random, CallbackInfoReturnable<CritParticle> cir) {
        if (!Config.critColor) return;
        CritParticle p = cir.getReturnValue();
        if (p != null) { float[] c = Config.rgb(Config.critRGB); p.setColor(c[0], c[1], c[2]); }
    }
}
