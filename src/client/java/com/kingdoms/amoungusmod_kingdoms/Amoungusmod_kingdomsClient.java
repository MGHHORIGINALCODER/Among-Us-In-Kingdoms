package com.kingdoms.amoungusmod_kingdoms;


import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.ModEntities;


import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.Customstorage;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItemGroups;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;


import com.kingdoms.amoungusmod_kingdoms.modle.PurpModel;
import com.kingdoms.amoungusmod_kingdoms.modle.TestEntityModel;
import com.kingdoms.amoungusmod_kingdoms.rendrer.ModModelLayers;
import com.kingdoms.amoungusmod_kingdoms.rendrer.PurpEntityRendrer;
import com.kingdoms.amoungusmod_kingdoms.rendrer.TestEntityRendrer;
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

        EntityRendererRegistry.register(ModEntities.TEST_ENTITY, TestEntityRendrer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TEST_ENTITY, TestEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.PURP_ENTITY, PurpEntityRendrer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.PURP_ENTITY, PurpModel::getTexturedModelData);



    }
}
