package com.senin.client.gui;

import com.senin.client.config.Config;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class KeybindsScreen extends Screen {
    private final Screen parent;
    public KeybindsScreen(Screen parent) { super(Text.literal("Tuş Ayarları")); this.parent = parent; }

    private static String keyName(int k) {
        String n = GLFW.glfwGetKeyName(k, 0);
        if (n == null) return switch (k) {
            case GLFW.GLFW_KEY_RIGHT_SHIFT -> "RShift"; case GLFW.GLFW_KEY_LEFT_SHIFT -> "LShift";
            case GLFW.GLFW_KEY_RIGHT_CONTROL -> "RCtrl"; case GLFW.GLFW_KEY_LEFT_CONTROL -> "LCtrl";
            case GLFW.GLFW_KEY_SPACE -> "Space"; case GLFW.GLFW_KEY_TAB -> "Tab";
            default -> "Key" + k;
        };
        return n.toUpperCase();
    }

    @Override
    protected void init() {
        int cx = width / 2, y = 60, bw = 220, bh = 22, gap = 26;
        btn(cx, y, bw, bh, "Menü", () -> Config.keyMenu, v -> Config.keyMenu = v); y += gap;
        btn(cx, y, bw, bh, "Zoom", () -> Config.keyZoom, v -> Config.keyZoom = v); y += gap;
        btn(cx, y, bw, bh, "Fullbright", () -> Config.keyFullbright, v -> Config.keyFullbright = v); y += gap;
        btn(cx, y, bw, bh, "NoHurtCam", () -> Config.keyNoHurtCam, v -> Config.keyNoHurtCam = v); y += gap;
        btn(cx, y, bw, bh, "Toggle Sprint", () -> Config.keyToggleSprint, v -> Config.keyToggleSprint = v); y += gap;
        btn(cx, y, bw, bh, "HUD", () -> Config.keyHud, v -> Config.keyHud = v);
        addDrawableChild(ButtonWidget.builder(Text.literal("Geri").formatted(Formatting.RED),
            b -> client.setScreen(parent)).dimensions(cx - 50, height - 40, 100, 20).build());
    }

    private void btn(int cx, int y, int w, int h, String name, IntSupplier get, IntConsumer set) {
        ButtonWidget b = ButtonWidget.builder(Text.literal(name + ": §e" + keyName(get.getAsInt())), bb -> {
            client.setScreen(new KeybindScreen(this, name, key -> {
                set.accept(key);
                bb.setMessage(Text.literal(name + ": §e" + keyName(key)));
            }));
        }).dimensions(cx - w / 2, y, w, h).build();
        addDrawableChild(b);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("ZORNO CLIENT — TUŞ AYARLARI").formatted(Formatting.AQUA, Formatting.BOLD), width / 2, 20, 0xFFFFFF);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("Bir tuşa tıkla, sonra yeni tuşu seç").formatted(Formatting.GRAY), width / 2, 36, 0xAAAAAA);
        super.render(ctx, mouseX, mouseY, delta);
    }
    @Override public boolean shouldPause() { return false; }
}
