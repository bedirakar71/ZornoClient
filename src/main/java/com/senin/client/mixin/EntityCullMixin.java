package com.senin.client.mixin;

import com.senin.client.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityCullMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void senin$cull(Entity entity, double x, double y, double z, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (!Config.entityCulling) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || entity == mc.player || entity.isRemoved()) return;
        Vec3d cam = mc.gameRenderer.getCamera().getPos();
        double dx = entity.getX() - cam.x, dy = entity.getY() - cam.y, dz = entity.getZ() - cam.z;
        if (dx * dx + dy * dy + dz * dz > (double) Config.cullDistance * Config.cullDistance) ci.cancel();
    }
}
