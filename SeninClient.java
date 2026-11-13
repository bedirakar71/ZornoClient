package com.senin.client;

import com.senin.client.config.Config;
import com.senin.client.gui.ZornoMenu;
import com.senin.client.hud.HudRenderer;
import com.senin.client.util.HitTracker;
import com.senin.client.util.TargetRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;

public class SeninClient implements ClientModInitializer {
    public static boolean zooming = false;
    private static boolean menuDown, fbDown, nhcDown, sprintDown, hudDown;

    @Override
    public void onInitializeClient() {
        Config.load();
        HudRenderer.register();
        TargetRenderer.register();

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            if (entity instanceof LivingEntity living && player == MinecraftClient.getInstance().player) {
                HitTracker.onHit(living);
                if (Config.hitSound) player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, 0.6f, 1.4f);
            }
            return ActionResult.PASS;
        });

        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);

        ClientPlayConnectionEvents.JOIN.register((h, s, c) -> {
            if (c.player != null) {
                c.player.sendMessage(
                    Text.literal("[Zorno Client v1.3] ").formatted(Formatting.AQUA, Formatting.BOLD)
                        .append(Text.literal("Yüklendi ✓  Sağ Shift = Menü").formatted(Formatting.WHITE)), false);
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(Config::save));
    }

    private void onTick(MinecraftClient mc) {
        long h = mc.getWindow().getHandle();
        boolean m = InputUtil.isKeyPressed(h, Config.keyMenu);
        if (m && !menuDown && mc.currentScreen == null) mc.setScreen(new ZornoMenu());
        menuDown = m;
        if (mc.player == null) return;

        zooming = Config.zoomEnabled && InputUtil.isKeyPressed(h, Config.keyZoom);

        boolean fb = InputUtil.isKeyPressed(h, Config.keyFullbright);
        if (fb && !fbDown) { Config.fullbright = !Config.fullbright;
            mc.player.sendMessage(Text.literal("Fullbright: " + Config.fullbright), true); } fbDown = fb;

        boolean nhc = InputUtil.isKeyPressed(h, Config.keyNoHurtCam);
        if (nhc && !nhcDown) { Config.noHurtCam = !Config.noHurtCam;
            mc.player.sendMessage(Text.literal("NoHurtCam: " + Config.noHurtCam), true); } nhcDown = nhc;

        boolean sp = InputUtil.isKeyPressed(h, Config.keyToggleSprint);
        if (sp && !sprintDown) { Config.toggleSprint = !Config.toggleSprint;
            mc.player.sendMessage(Text.literal("ToggleSprint: " + Config.toggleSprint), true); } sprintDown = sp;

        boolean hd = InputUtil.isKeyPressed(h, Config.keyHud);
        if (hd && !hudDown) { Config.hud = !Config.hud;
            mc.player.sendMessage(Text.literal("HUD: " + Config.hud), true); } hudDown = hd;

        if (Config.toggleSprint) mc.player.setSprinting(true);
    }
}
