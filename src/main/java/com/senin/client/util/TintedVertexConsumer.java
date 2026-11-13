package com.senin.client.util;

import net.minecraft.client.render.VertexConsumer;

public class TintedVertexConsumer implements VertexConsumer {
    private final VertexConsumer delegate;
    private final float r, g, b;

    public TintedVertexConsumer(VertexConsumer delegate, float r, float g, float b) {
        this.delegate = delegate; this.r = r; this.g = g; this.b = b;
    }

    @Override public VertexConsumer vertex(float x, float y, float z) { delegate.vertex(x, y, z); return this; }
    @Override public VertexConsumer color(int red, int green, int blue, int alpha) {
        delegate.color((int)(red * r), (int)(green * g), (int)(blue * b), alpha); return this;
    }
    @Override public VertexConsumer texture(float u, float v) { delegate.texture(u, v); return this; }
    @Override public VertexConsumer overlay(int u, int v) { delegate.overlay(u, v); return this; }
    @Override public VertexConsumer light(int u, int v) { delegate.light(u, v); return this; }
    @Override public VertexConsumer normal(float x, float y, float z) { delegate.normal(x, y, z); return this; }
}
