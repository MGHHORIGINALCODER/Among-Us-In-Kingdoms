package com.kingdoms.amoungusmod_kingdoms.rendrer;

import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer TEST_ENTITY= new EntityModelLayer(new Identifier(Amoungusmod_kingdoms.MOD_ID,"test_entity_model"),"main");
    public static final EntityModelLayer PURP_ENTITY= new EntityModelLayer(new Identifier(Amoungusmod_kingdoms.MOD_ID,"purp_entity_model"),"main");
}
