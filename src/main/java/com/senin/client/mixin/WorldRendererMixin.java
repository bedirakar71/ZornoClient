package com.senin.client.mixin;

import com.senin.client.config.Config;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Redirect(
        method = "drawBlockOutline",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexRendering;drawOutline(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/util/shape/VoxelShape;DDDI)V")
    )
    private void senin$blockOutline(MatrixStack ms, VertexConsumer vc, VoxelShape shape, double x, double y, double z, int originalColor) {
        if (Config.blockOutlineColor) {
            net.minecraft.client.render.VertexRendering.drawOutline(ms, vc, shape, x, y, z, Config.outlineRGB);
        } else {
            net.minecraft.client.render.VertexRendering.drawOutline(ms, vc, shape, x, y, z, originalColor);
        }
    }
}
