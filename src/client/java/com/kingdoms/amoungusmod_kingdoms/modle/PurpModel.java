// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.kingdoms.amoungusmod_kingdoms.modle;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.custom.PurpEntity;
import com.kingdoms.amoungusmod_kingdoms.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class PurpModel<T extends PurpEntity> extends SinglePartEntityModel<T>  {
	private final ModelPart Controller;

	public PurpModel(ModelPart root) {
		this.Controller = root.getChild("Controller");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Controller = modelPartData.addChild("Controller", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 13.0F, 0.0F));

		ModelPartData Body = Controller.addChild("Body", ModelPartBuilder.create().uv(-2, -1).cuboid(1.0F, -15.0F, -5.0F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-7.0F, -14.0F, -7.0F, 14.0F, 18.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 3.0F, 0.0F));

		ModelPartData backpack = Body.addChild("backpack", ModelPartBuilder.create().uv(0, 30).cuboid(-1.0F, -11.0F, -7.0F, 10.0F, 13.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 0.0F, 12.0F));

		ModelPartData Left_arm = Body.addChild("Left_arm", ModelPartBuilder.create().uv(30, 40).cuboid(0.0F, -1.0F, -2.0F, 3.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(7.0F, -8.0F, 0.0F));

		ModelPartData Right_arm = Body.addChild("Right_arm", ModelPartBuilder.create().uv(44, 40).cuboid(-3.0F, -1.0F, -2.0F, 3.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, -8.0F, 0.0F));

		ModelPartData visor = Body.addChild("visor", ModelPartBuilder.create().uv(30, 30).cuboid(-5.0F, -3.0F, -2.0F, 10.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -9.0F, -8.0F));

		ModelPartData Left_foot = Controller.addChild("Left_foot", ModelPartBuilder.create().uv(0, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 7.0F, 0.0F));

		ModelPartData Right_foot = Controller.addChild("Right_foot", ModelPartBuilder.create().uv(52, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 7.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(PurpEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.animateMovement(ModAnimations.walking, limbSwing, limbSwingAmount, 1f, 1f);
		this.updateAnimation(entity.idleAnimationState,ModAnimations.stopwalking,ageInTicks,1f);
		// Check the entity's current state to dynamically alter playback speed
		if (entity.getCurrentState() == PurpEntity.SitState.SITTING_DOWN) {
			// Plays the sitting down animation forward at standard speed (1.0f)
			this.updateAnimation(entity.sitDownState, ModAnimations.sit, ageInTicks, 1.0f);
		}
		else if (entity.getCurrentState() == PurpEntity.SitState.SITTING_UP) {
			// Plays the exact same sitting animation completely in reverse (-1.0f) to look like standing up!
			this.updateAnimation(entity.sitUpState, ModAnimations.gettingup_purp, ageInTicks, 1.0f);
		}
		else if (entity.getCurrentState() == PurpEntity.SitState.SITTING_IDLE) {
			// Loops your resting/stopwalking posture while fully settled
			this.updateAnimation(entity.sitIdleState, ModAnimations.idlingsit_purp, ageInTicks, 1.0f);
		}
	}







	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Controller.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.Controller;
	}


}