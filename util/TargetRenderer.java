package com.senin.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.senin.client.config.Config;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class TargetRenderer {
    public static void register() {
        WorldRenderEvents.LAST.register(ctx -> {
            if (!Config.targetIndicator || !HitTracker.isActive()) return;
            LivingEntity target = HitTracker.lastTarget;
            if (target == null || target.isRemoved()) return;
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null || ctx.consumers() == null) return;

            Vec3d cam = ctx.camera().getPos();
            double x = target.getX() - cam.x;
            double y = target.getY() + target.getHeight() * 0.5 - cam.y;
            double z = target.getZ() - cam.z;
            float angle = ((System.currentTimeMillis() % 2000L) / 2000f) * 360f;
            float[] c = Config.rgb(Config.targetRGB);
            float r = c[0], g = c[1], b = c[2];
            float a = HitTracker.getRemainingAlpha() * 0.9f;

            MatrixStack ms = ctx.matrixStack();
            ms.push();
            ms.translate(x, y, z);

            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.lineWidth(2.5f);

            VertexConsumer vc = ctx.consumers().getBuffer(RenderLayer.getLines());
            ms.push();
            ms.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));
            drawRing(ms, vc, 0.7f, r, g, b, a);
            ms.pop();

            ms.push();
            ms.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-angle * 1.5f));
            ms.multiply(RotationAxis.POSITIVE_X.rotationDegrees(65f));
            drawRing(ms, vc, 0.6f, r * 0.6f, g * 0.6f, b * 0.6f, a * 0.7f);
            ms.pop();

            RenderSystem.lineWidth(1f);
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            ms.pop();
        });
    }

    private static void drawRing(MatrixStack ms, VertexConsumer vc, float radius, float r, float g, float b, float a) {
        Matrix4f mat = ms.peek().getPositionMatrix();
        int seg = 28;
        for (int i = 0; i < seg; i++) {
            float a1 = (float)(i * Math.PI * 2 / seg);
            float a2 = (float)((i + 1) * Math.PI * 2 / seg);
            vc.vertex(mat, (float)Math.cos(a1) * radius, 0f, (float)Math.sin(a1) * radius).color(r, g, b, a);
            vc.vertex(mat, (float)Math.cos(a2) * radius, 0f, (float)Math.sin(a2) * radius).color(r, g, b, a);
        }
    }
}
