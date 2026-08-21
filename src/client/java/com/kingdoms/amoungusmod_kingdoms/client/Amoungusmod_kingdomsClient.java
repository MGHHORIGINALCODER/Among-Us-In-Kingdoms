package com.kingdoms.amoungusmod_kingdoms.client;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.Customstorage;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItemGroups;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.fabricmc.api.ClientModInitializer;

public class Amoungusmod_kingdomsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModSounds.registerSounds();
        ModItemGroups.registerItemGroups();
        Customstorage.AddAllINV_ITEMS();
    }
}
