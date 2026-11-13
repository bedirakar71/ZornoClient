package com.senin.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.senin.client.config.Config;
import com.senin.client.util.TintedVertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Redirect(
        method = "renderItem",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider;getBuffer(Lnet/minecraft/client/render/RenderLayer;)Lnet/minecraft/client/render/VertexConsumer;")
    )
    private VertexConsumer senin$tintItem(VertexConsumerProvider provider, RenderLayer layer, @Local(argsOnly = true, ordinal = 0) ItemStack stack) {
        VertexConsumer original = provider.getBuffer(layer);
        Item item = stack.getItem();

        if (Config.shieldRed && item instanceof ShieldItem && stack.isDamageable()) {
            float pct = 1.0f - (float) stack.getDamage() / stack.getMaxDamage();
            if (pct < 0.3f) return new TintedVertexConsumer(original, 1.0f, 0.15f, 0.15f);
        }
        if (Config.swordColor && item instanceof SwordItem) {
            float[] c = Config.rgb(Config.swordRGB);
            return new TintedVertexConsumer(original, c[0], c[1], c[2]);
        }
        if (Config.pickaxeColor && item instanceof PickaxeItem) {
            float[] c = Config.rgb(Config.pickaxeRGB);
            return new TintedVertexConsumer(original, c[0], c[1], c[2]);
        }
        return original;
    }
}
