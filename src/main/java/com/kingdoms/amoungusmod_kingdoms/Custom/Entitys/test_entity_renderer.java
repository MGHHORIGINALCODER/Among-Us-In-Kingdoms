package com.kingdoms.amoungusmod_kingdoms.Custom.Entitys;

import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.test_entity;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModEntities;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class test_entity_renderer extends MobEntityRenderer {
    // Points to your .png texture inside your assets folder
    private static final Identifier TEXTURE = new Identifier(Amoungusmod_kingdoms.MOD_ID, "textures/entity/test_entity.png");

    public test_entity_renderer(EntityRendererFactory.Context context) {
        // Pass context, an instance of your model using the layer ID, and a shadow radius (0.1f for tiny mobs)
        super(context, new test_entity(context.getPart(ModModelLayers.TEST_MOB_LAYER)), 0.1f);
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return TEXTURE;
    }


}
