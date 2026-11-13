package com.senin.client.mixin;

import com.senin.client.config.Config;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    private void senin$viewModelAndFloat(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (Config.viewModel) {
            matrices.translate(Config.vmPosX, Config.vmPosY, Config.vmPosZ);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(Config.vmRotX));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(Config.vmRotY));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(Config.vmRotZ));
            matrices.scale(Config.vmScale, Config.vmScale, Config.vmScale);
        }
        if (Config.floatingSword && item.getItem() instanceof SwordItem) {
            double t = (System.currentTimeMillis() % 4000L) / 4000.0 * Math.PI * 2.0;
            matrices.translate((float) Math.cos(t) * 0.008f, (float) Math.sin(t) * 0.025f, 0);
        }
    }
}
