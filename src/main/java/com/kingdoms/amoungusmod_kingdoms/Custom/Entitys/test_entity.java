// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.kingdoms.amoungusmod_kingdoms.Custom.Entitys;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class test_entity extends EntityModel<Entity> {
	private final ModelPart Main;
	public test_entity(ModelPart root) {
		this.Main = root.getChild("Main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Main = modelPartData.addChild("Main", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -9.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 16, 16);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}