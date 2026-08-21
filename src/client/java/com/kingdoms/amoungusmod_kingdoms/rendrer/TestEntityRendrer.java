package com.kingdoms.amoungusmod_kingdoms.rendrer;

import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.custom.TestEntity;

import com.kingdoms.amoungusmod_kingdoms.modle.TestEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TestEntityRendrer extends MobEntityRenderer<TestEntity, TestEntityModel<TestEntity>> {
    public static  final Identifier TEXTURE = new Identifier(Amoungusmod_kingdoms.MOD_ID,"textures/entity/test_entity.png");

    public TestEntityRendrer(EntityRendererFactory.Context context) {
        super(context, new TestEntityModel<>(context.getPart(ModModelLayers.TEST_ENTITY)),0.6f);
    }

    @Override
    public Identifier getTexture(TestEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(TestEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        if(mobEntity.isBaby()){
            matrixStack.scale(0.5f,0.5f,0.5f);
        }else{
            matrixStack.scale(1f,1f,1f);
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
