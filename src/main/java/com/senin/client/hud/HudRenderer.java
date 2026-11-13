package com.senin.client.hud;

import com.senin.client.config.Config;
import com.senin.client.util.CpsTracker;
import com.senin.client.util.HitTracker;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class HudRenderer {
    public static void register() { HudRenderCallback.EVENT.register(HudRenderer::onHud); }

    private static void onHud(DrawContext ctx, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (!Config.hud || mc.player == null || mc.options.hudHidden) return;
        int x = 4, y = 4;

        ctx.drawTextWithShadow(mc.textRenderer, Text.literal("ZORNO CLIENT").formatted(Formatting.AQUA, Formatting.BOLD), x, y, 0xFFFFFF); y += 12;

        ctx.drawTextWithShadow(mc.textRenderer, Text.literal("FPS: ").formatted(Formatting.GREEN)
                .append(Text.literal(String.valueOf(mc.getCurrentFps())).formatted(Formatting.WHITE)), x, y, 0xFFFFFF); y += 10;

        if (Config.ping) {
            PlayerListEntry e = mc.getNetworkHandler() != null ? mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid()) : null;
            int ping = e != null ? e.getLatency() : 0;
            ctx.drawTextWithShadow(mc.textRenderer, Text.literal("Ping: ").formatted(Formatting.GREEN)
                    .append(Text.literal(ping + "ms").formatted(Formatting.WHITE)), x, y, 0xFFFFFF); y += 10;
        }
        if (Config.cps) {
            ctx.drawTextWithShadow(mc.textRenderer, Text.literal("CPS: ").formatted(Formatting.GREEN)
                    .append(Text.literal(CpsTracker.getLeftCps() + " | " + CpsTracker.getRightCps()).formatted(Formatting.WHITE)), x, y, 0xFFFFFF); y += 10;
        }
        if (Config.coords) {
            ctx.drawTextWithShadow(mc.textRenderer, Text.literal("XYZ: ").formatted(Formatting.GREEN)
                    .append(Text.literal(String.format("%.0f %.0f %.0f", mc.player.getX(), mc.player.getY(), mc.player.getZ())).formatted(Formatting.WHITE)), x, y, 0xFFFFFF); y += 10;
            ctx.drawTextWithShadow(mc.textRenderer, Text.literal("Dir: ").formatted(Formatting.GREEN)
                    .append(Text.literal(mc.player.getHorizontalFacing().getName()).formatted(Formatting.WHITE)), x, y, 0xFFFFFF); y += 10;
        }

        if (Config.keystrokes) {
            int kx = 4, ky = ctx.getScaledWindowHeight() - 60;
            drawKey(ctx, mc, kx + 20, ky, 20, 20, "W", mc.options.forwardKey.isPressed());
            drawKey(ctx, mc, kx, ky + 22, 20, 20, "A", mc.options.leftKey.isPressed());
            drawKey(ctx, mc, kx + 20, ky + 22, 20, 20, "S", mc.options.backKey.isPressed());
            drawKey(ctx, mc, kx + 40, ky + 22, 20, 20, "D", mc.options.rightKey.isPressed());
            drawKey(ctx, mc, kx, ky + 44, 60, 20, "SPACE", mc.options.jumpKey.isPressed());
        }

        if (Config.armor) {
            int ax = ctx.getScaledWindowWidth() - 80, ay = ctx.getScaledWindowHeight() - 20;
            for (EquipmentSlot s : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
                ItemStack st = mc.player.getEquippedStack(s);
                if (!st.isEmpty()) { ctx.drawItem(st, ax, ay); ctx.drawStackOverlay(mc.textRenderer, st, ax, ay); }
                ax += 18;
            }
        }

        if (Config.potionHud) {
            int px = ctx.getScaledWindowWidth() - 4, py = 4;
            for (var eff : mc.player.getStatusEffects()) {
                String name = eff.getEffectType().value().getName().getString();
                int dur = eff.getDuration() / 20;
                String txt = name + " " + (dur / 60) + ":" + String.format("%02d", dur % 60);
                int w = mc.textRenderer.getWidth(txt);
                ctx.drawTextWithShadow(mc.textRenderer, Text.literal(txt).formatted(eff.getEffectType().value().isBeneficial() ? Formatting.GREEN : Formatting.RED), px - w, py, 0xFFFFFF);
                py += 10;
            }
        }

        if (Config.comboCounter && HitTracker.isComboActive()) {
            int combo = HitTracker.comboCount;
            String txt = combo + "x COMBO";
            int cw = mc.textRenderer.getWidth(txt);
            int comboY = ctx.getScaledWindowHeight() / 2 + 30;
            int color = combo >= 10 ? 0xFFFF3333 : combo >= 5 ? 0xFFFFAA00 : 0xFFFFFF00;
            ctx.drawTextWithShadow(mc.textRenderer, Text.literal(txt).formatted(Formatting.BOLD), (ctx.getScaledWindowWidth() - cw) / 2, comboY, color);
        }
    }

    private static void drawKey(DrawContext ctx, MinecraftClient mc, int x, int y, int w, int h, String label, boolean pressed) {
        ctx.fill(x, y, x + w, y + h, pressed ? 0xAA00FF88 : 0xAA222222);
        int tx = x + (w - mc.textRenderer.getWidth(label)) / 2;
        int ty = y + (h - 8) / 2;
        ctx.drawTextWithShadow(mc.textRenderer, Text.literal(label).formatted(Formatting.WHITE), tx, ty, 0xFFFFFF);
    }
}
