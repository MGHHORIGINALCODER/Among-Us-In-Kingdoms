package com.kingdoms.amoungusmod_kingdoms.client;

import com.kingdoms.amoungusmod_kingdoms.Custom.*;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.test_entity;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.test_entity_renderer;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.Customstorage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class Amoungusmod_kingdomsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModSounds.registerSounds();
        ModItemGroups.registerItemGroups();
        Customstorage.AddAllINV_ITEMS();

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TEST_MOB_LAYER, test_entity::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.TEST_MOB, test_entity_renderer::new);
    }
}
