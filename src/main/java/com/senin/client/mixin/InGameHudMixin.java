package com.senin.client.mixin;

import com.senin.client.config.Config;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    private void senin$noFire(DrawContext ctx, Identifier texture, float opacity, CallbackInfo ci) {
        if (Config.noFire && texture != null && texture.getPath().contains("fire")) ci.cancel();
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void senin$crosshair(DrawContext ctx, RenderTickCounter tick, CallbackInfo ci) {
        if (!Config.customCrosshair) return;
        ci.cancel();
        int cx = ctx.getScaledWindowWidth() / 2, cy = ctx.getScaledWindowHeight() / 2;
        int s = Config.crosshairSize, t = Config.crosshairThickness, c = Config.crosshairRGB;
        ctx.fill(cx - s, cy - t / 2, cx + s + 1, cy + t / 2 + 1, c);
        ctx.fill(cx - t / 2, cy - s, cx + t / 2 + 1, cy + s + 1, c);
    }
}
