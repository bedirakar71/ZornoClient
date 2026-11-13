package com.senin.client.gui;

import com.senin.client.config.Config;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class ColorsScreen extends Screen {
    private final Screen parent;
    public ColorsScreen(Screen parent) { super(Text.literal("Renkler")); this.parent = parent; }

    private static int R(int c) { return (c >> 16) & 0xFF; }
    private static int G(int c) { return (c >> 8) & 0xFF; }
    private static int B(int c) { return c & 0xFF; }
    private static int make(int r, int g, int b) { return 0xFF000000 | (r << 16) | (g << 8) | b; }

    @Override
    protected void init() {
        int y = 40, cx = width / 2;
        row(cx, y, "Kılıç", () -> Config.swordRGB, v -> { Config.swordRGB = v; Config.swordColor = true; }); y += 40;
        row(cx, y, "Kazma", () -> Config.pickaxeRGB, v -> { Config.pickaxeRGB = v; Config.pickaxeColor = true; }); y += 40;
        row(cx, y, "Crit", () -> Config.critRGB, v -> { Config.critRGB = v; Config.critColor = true; }); y += 40;
        row(cx, y, "Blok", () -> Config.outlineRGB, v -> { Config.outlineRGB = v; Config.blockOutlineColor = true; }); y += 40;
        row(cx, y, "Target", () -> Config.targetRGB, v -> Config.targetRGB = v); y += 40;
        row(cx, y, "Crosshair", () -> Config.crosshairRGB, v -> { Config.crosshairRGB = v; Config.customCrosshair = true; }); y += 40;

        addDrawableChild(ButtonWidget.builder(Text.literal("Geri").formatted(Formatting.RED),
            b -> client.setScreen(parent)).dimensions(cx - 50, height - 40, 100, 20).build());
    }

    private void row(int cx, int y, String label, IntSupplier get, IntConsumer set) {
        int c = get.getAsInt();
        int r = R(c), g = G(c), b = B(c);
        int bx = cx - 250;

        addDrawableChild(new RGBSlider(bx, y, 140, 20, "R", r, v -> set.accept(make(v, G(get.getAsInt()), B(get.getAsInt())))));
        addDrawableChild(new RGBSlider(bx + 145, y, 140, 20, "G", g, v -> set.accept(make(R(get.getAsInt()), v, B(get.getAsInt())))));
        addDrawableChild(new RGBSlider(bx + 290, y, 140, 20, "B", b, v -> set.accept(make(R(get.getAsInt()), G(get.getAsInt()), v))));
        addDrawableChild(ButtonWidget.builder(Text.literal(""), bb -> {}).dimensions(bx + 435, y, 20, 20).build());
    }

    private static class RGBSlider extends SliderWidget {
        private final String label;
        private final IntConsumer on;
        RGBSlider(int x, int y, int w, int h, String label, int val, IntConsumer on) {
            super(x, y, w, h, Text.literal(label + ": " + val), val / 255.0);
            this.label = label; this.on = on;
        }
        @Override protected void updateMessage() { super.setMessage(Text.literal(label + ": " + (int)(value * 255))); }
        @Override protected void applyValue() { on.accept((int)(value * 255)); }
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("ZORNO CLIENT — RENK SEÇİCİ").formatted(Formatting.AQUA, Formatting.BOLD), width / 2, 20, 0xFFFFFF);

        int y = 40, cx = width / 2 + 200;
        int[] colors = {Config.swordRGB, Config.pickaxeRGB, Config.critRGB, Config.outlineRGB, Config.targetRGB, Config.crosshairRGB};
        for (int c : colors) {
            ctx.fill(cx, y + 2, cx + 20, y + 18, 0xFF000000 | (c & 0xFFFFFF));
            y += 40;
        }
        super.render(ctx, mouseX, mouseY, delta);
    }
    @Override public boolean shouldPause() { return false; }
}
