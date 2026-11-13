package com.senin.client.mixin;

import com.senin.client.config.Config;
import net.minecraft.client.particle.DamageParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Random;

@Mixin(DamageParticle.Factory.class)
public class CritParticleMixin {
    @Inject(method = "createParticle*", at = @At("RETURN"))
    private void senin$crit(SimpleParticleType type, ClientWorld world, double x, double y, double z, double vx, double vy, double vz, Random random, CallbackInfoReturnable<DamageParticle> cir) {
        if (!Config.critColor) return;
        DamageParticle p = cir.getReturnValue();
        if (p != null) { 
            float[] c = Config.rgb(Config.critRGB); 
            p.setColor(c[0], c[1], c[2]); 
        }
    }
}
