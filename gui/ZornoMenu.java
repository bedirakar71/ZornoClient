package com.senin.client.gui;

import com.senin.client.config.Config;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.function.Consumer;

public class ZornoMenu extends Screen {
    public ZornoMenu() { super(Text.literal("Zorno Client")); }

    @Override
    protected void init() {
        int cx = width / 2, startY = 50;
        int col1 = cx - 305, col2 = cx - 150, col3 = cx + 5;
        int bw = 145, bh = 20, gap = 24;

        int y = startY;
        t(col1, y, bw, bh, "HUD", Config.hud, v -> Config.hud = v); y += gap;
        t(col1, y, bw, bh, "Keystrokes", Config.keystrokes, v -> Config.keystrokes = v); y += gap;
        t(col1, y, bw, bh, "CPS", Config.cps, v -> Config.cps = v); y += gap;
        t(col1, y, bw, bh, "Koordinat", Config.coords, v -> Config.coords = v); y += gap;
        t(col1, y, bw, bh, "Armor", Config.armor, v -> Config.armor = v); y += gap;
        t(col1, y, bw, bh, "Potion HUD", Config.potionHud, v -> Config.potionHud = v); y += gap;
        t(col1, y, bw, bh, "Combo", Config.comboCounter, v -> Config.comboCounter = v); y += gap;
        t(col1, y, bw, bh, "Toggle Sprint", Config.toggleSprint, v -> Config.toggleSprint = v); y += gap;
        t(col1, y, bw, bh, "NoHurtCam", Config.noHurtCam, v -> Config.noHurtCam = v); y += gap;
        t(col1, y, bw, bh, "Fullbright", Config.fullbright, v -> Config.fullbright = v); y += gap;
        t(col1, y, bw, bh, "Zoom", Config.zoomEnabled, v -> Config.zoomEnabled = v); y += gap;
        t(col1, y, bw, bh, "NoFire", Config.noFire, v -> Config.noFire = v);

        y = startY;
        t(col2, y, bw, bh, "Kılıç Rengi", Config.swordColor, v -> Config.swordColor = v); y += gap;
        t(col2, y, bw, bh, "Kazma Rengi", Config.pickaxeColor, v -> Config.pickaxeColor = v); y += gap;
        t(col2, y, bw, bh, "Havada Süzülen", Config.floatingSword, v -> Config.floatingSword = v); y += gap;
        t(col2, y, bw, bh, "Kırmızı Kalkan", Config.shieldRed, v -> Config.shieldRed = v); y += gap;
        t(col2, y, bw, bh, "Crit Parçacık", Config.critColor, v -> Config.critColor = v); y += gap;
        t(col2, y, bw, bh, "Blok Kenar", Config.blockOutlineColor, v -> Config.blockOutlineColor = v); y += gap;
        t(col2, y, bw, bh, "Hit Sound", Config.hitSound, v -> Config.hitSound = v); y += gap;
        t(col2, y, bw, bh, "Custom Crosshair", Config.customCrosshair, v -> Config.customCrosshair = v); y += gap;
        t(col2, y, bw, bh, "Target Indicator", Config.targetIndicator, v -> Config.targetIndicator = v); y += gap;
        t(col2, y, bw, bh, "View Model", Config.viewModel, v -> Config.viewModel = v);

        y = startY;
        t(col3, y, bw, bh, "Entity Culling", Config.entityCulling, v -> Config.entityCulling = v); y += gap;
        t(col3, y, bw, bh, "NoFog", Config.noFog, v -> Config.noFog = v); y += gap;
        t(col3, y, bw, bh, "NoWeather", Config.noWeather, v -> Config.noWeather = v); y += gap;
        t(col3, y, bw, bh, "NoClouds", Config.noClouds, v -> Config.noClouds = v); y += gap;
        t(col3, y, bw, bh, "Particle Limit", Config.reduceParticles, v -> Config.reduceParticles = v); y += gap;

        // Sky theme
        addDrawableChild(ButtonWidget.builder(
            Text.literal("Sky: " + skyName()),
            b -> { Config.skyTheme = (Config.skyTheme + 1) % 5; b.setMessage(Text.literal("Sky: " + skyName())); })
            .dimensions(col3, y, bw, bh).build()); y += gap;

        // VM Sliders
        s(col3, y, bw, bh, "VM X", Config.vmPosX, -1f, 1f, v -> Config.vmPosX = v); y += gap;
        s(col3, y, bw, bh, "VM Y", Config.vmPosY, -1f, 1f, v -> Config.vmPosY = v); y += gap;
        s(col3, y, bw, bh, "VM Scale", Config.vmScale, 0.5f, 2f, v -> Config.vmScale = v); y += gap;

        addDrawableChild(ButtonWidget.builder(Text.literal("§dRenk Seçici"),
            b -> client.setScreen(new ColorsScreen(this))).dimensions(col3, y, bw, bh).build()); y += gap;
        addDrawableChild(ButtonWidget.builder(Text.literal("§bTuş Ayarları"),
            b -> client.setScreen(new KeybindsScreen(this))).dimensions(col3, y, bw, bh).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Kapat (ESC)").formatted(Formatting.RED),
            b -> close()).dimensions(cx - 50, height - 30, 100, 20).build());
    }

    private String skyName() {
        return switch (Config.skyTheme) {
            case 1 -> "§9Uzay"; case 2 -> "§6Sunset"; case 3 -> "§cNether"; case 4 -> "§5Cyberpunk"; default -> "§7Vanilla";
        };
    }

    private void t(int x, int y, int w, int h, String label, boolean cur, Consumer<Boolean> set) {
        addDrawableChild(ButtonWidget.builder(Text.literal(label + ": " + (cur ? "§aAÇIK" : "§cKAPALI")), b -> {
            boolean now = !read(label);
            set.accept(now);
            b.setMessage(Text.literal(label + ": " + (now ? "§aAÇIK" : "§cKAPALI")));
        }).dimensions(x, y, w, h).build());
    }

    private void s(int x, int y, int w, int h, String label, float cur, float min, float max, Consumer<Float> set) {
        addDrawableChild(new SliderWidget(x, y, w, h, Text.literal(label + ": " + String.format("%.2f", cur)), (cur - min) / (max - min)) {
            @Override protected void updateMessage() { setMessage(Text.literal(label + ": " + String.format("%.2f", min + (float)(value * (max - min))))); }
            @Override protected void applyValue() { set.accept(min + (float)(value * (max - min))); }
            private void setMessage(Text t) { super.setMessage(t); }
        });
    }

    private boolean read(String l) {
        return switch (l) {
            case "HUD" -> Config.hud; case "Keystrokes" -> Config.keystrokes; case "CPS" -> Config.cps;
            case "Koordinat" -> Config.coords; case "Armor" -> Config.armor;
            case "Potion HUD" -> Config.potionHud; case "Combo" -> Config.comboCounter;
            case "Toggle Sprint" -> Config.toggleSprint; case "NoHurtCam" -> Config.noHurtCam;
            case "Fullbright" -> Config.fullbright; case "Zoom" -> Config.zoomEnabled;
            case "NoFire" -> Config.noFire; case "Kılıç Rengi" -> Config.swordColor;
            case "Kazma Rengi" -> Config.pickaxeColor; case "Havada Süzülen" -> Config.floatingSword;
            case "Kırmızı Kalkan" -> Config.shieldRed; case "Crit Parçacık" -> Config.critColor;
            case "Blok Kenar" -> Config.blockOutlineColor; case "Hit Sound" -> Config.hitSound;
            case "Custom Crosshair" -> Config.customCrosshair; case "Target Indicator" -> Config.targetIndicator;
            case "View Model" -> Config.viewModel; case "Entity Culling" -> Config.entityCulling;
            case "NoFog" -> Config.noFog; case "NoWeather" -> Config.noWeather;
            case "NoClouds" -> Config.noClouds; case "Particle Limit" -> Config.reduceParticles;
            default -> false;
        };
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("ZORNO CLIENT").formatted(Formatting.AQUA, Formatting.BOLD), width / 2, 20, 0xFFFFFF);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("v1.3 • Sağ Shift = Menü").formatted(Formatting.GRAY), width / 2, 34, 0xAAAAAA);
        super.render(ctx, mouseX, mouseY, delta);
    }
    @Override public boolean shouldPause() { return false; }
}
