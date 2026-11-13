package com.senin.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.senin.client.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SimpleOption.class)
public class SimpleOptionMixin {
    @ModifyReturnValue(method = "getValue", at = @At("RETURN"))
    private Object senin$modifyValue(Object original) {
        SimpleOption<?> self = (SimpleOption<?>)(Object)this;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null || mc.options == null) return original;

        if (Config.fullbright && self == mc.options.getGamma() && original instanceof Double) return 15.0;
        if (Config.noClouds && self == mc.options.getCloudRenderMode() && original instanceof CloudRenderMode) return CloudRenderMode.OFF;
        return original;
    }
}
