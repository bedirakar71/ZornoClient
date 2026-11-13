package com.senin.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import java.util.function.IntConsumer;

public class KeybindScreen extends Screen {
    private final Screen parent;
    private final String actionName;
    private final IntConsumer onKey;

    public KeybindScreen(Screen parent, String actionName, IntConsumer onKey) {
        super(Text.literal("Tuş Ata"));
        this.parent = parent; this.actionName = actionName; this.onKey = onKey;
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal(actionName + " için bir tuşa bas").formatted(Formatting.YELLOW), width / 2, height / 2 - 20, 0xFFFFFF);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("İptal: ESC").formatted(Formatting.GRAY), width / 2, height / 2 + 10, 0xAAAAAA);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) { client.setScreen(parent); return true; }
        onKey.accept(keyCode);
        client.setScreen(parent);
        return true;
    }
    @Override public boolean shouldPause() { return false; }
}
