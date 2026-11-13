package com.senin.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashOverlay.class)
public class SplashOverlayMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void senin$zorno(DrawContext ctx, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        int w = ctx.getScaledWindowWidth(), h = ctx.getScaledWindowHeight();
        String title = "ZORNO CLIENT";
        int tw = mc.textRenderer.getWidth(title);
        int tx = (w - tw) / 2, ty = h / 2 + 40;
        ctx.drawTextWithShadow(mc.textRenderer, Text.literal(title).formatted(Formatting.AQUA, Formatting.BOLD), tx, ty, 0xFF00FFFF);
        String sub = "v1.3  •  yükleniyor...";
        int sw = mc.textRenderer.getWidth(sub);
        ctx.drawTextWithShadow(mc.textRenderer, Text.literal(sub).formatted(Formatting.GRAY), (w - sw) / 2, ty + 14, 0xAAAAAA);
    }
}
