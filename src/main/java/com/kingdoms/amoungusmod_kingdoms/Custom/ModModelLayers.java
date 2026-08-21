package com.kingdoms.amoungusmod_kingdoms.Custom;

import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer TEST_MOB_LAYER = new EntityModelLayer(
            new Identifier(Amoungusmod_kingdoms.MOD_ID, "test_entity"), "main"
    );
}
