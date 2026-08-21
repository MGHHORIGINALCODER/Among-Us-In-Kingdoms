package com.kingdoms.amoungusmod_kingdoms.rendrer;



import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.custom.PurpEntity;

import com.kingdoms.amoungusmod_kingdoms.modle.PurpModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PurpEntityRendrer extends MobEntityRenderer<PurpEntity, PurpModel<PurpEntity>> {
    public static  final Identifier TEXTURE = new Identifier(Amoungusmod_kingdoms.MOD_ID,"textures/entity/purp.png");

    public PurpEntityRendrer(EntityRendererFactory.Context context) {
        super(context, new PurpModel<PurpEntity>(context.getPart(ModModelLayers.PURP_ENTITY)),0.6f);
    }

    @Override
    public Identifier getTexture(PurpEntity entity) {
        return TEXTURE;
    }



    @Override
    public void render(PurpEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        if(mobEntity.isBaby()){
            matrixStack.scale(0.5f,0.5f,0.5f);
        }else{
            matrixStack.scale(1f,1f,1f);
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
