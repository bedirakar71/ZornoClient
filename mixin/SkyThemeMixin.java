package com.senin.client.mixin;

import com.senin.client.config.Config;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public class SkyThemeMixin {
    @Inject(method = "getSkyColor", at = @At("RETURN"), cancellable = true)
    private void senin$sky(Vec3d cameraPos, float tickDelta, CallbackInfoReturnable<Vec3d> cir) {
        if (Config.skyTheme == 0) return;
        int[] c = Config.skyColor();
        cir.setReturnValue(new Vec3d(c[0] / 255.0, c[1] / 255.0, c[2] / 255.0));
    }
}
