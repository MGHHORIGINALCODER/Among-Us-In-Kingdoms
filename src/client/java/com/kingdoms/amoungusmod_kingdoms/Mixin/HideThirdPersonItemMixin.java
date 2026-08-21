package com.kingdoms.amoungusmod_kingdoms.Mixin;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.Customstorage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(HeldItemFeatureRenderer.class)
public class HideThirdPersonItemMixin {

    @Inject(
            method = "renderItem",
            at = @At("HEAD"),
            cancellable = true
    )
    private void hideItemInThirdPerson(
            LivingEntity entity,
            ItemStack stack,
            ModelTransformationMode transformationMode,
            Arm arm,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            CallbackInfo ci
    ) {
        // Safe check to ensure the item stack isn't empty
        if (stack != null && !stack.isEmpty()) {

            // Check if the item matches anything in your custom invisibility storage list
            if (Customstorage.INVIS_ITEMS.contains(stack.getItem())) {

                // By removing the player check, this cancels rendering for ALL players on your screen
                ci.cancel();
            }
        }
    }
}
