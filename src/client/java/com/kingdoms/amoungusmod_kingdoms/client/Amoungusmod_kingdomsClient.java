package com.kingdoms.amoungusmod_kingdoms.client;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.Customstorage;
import net.fabricmc.api.ClientModInitializer;

public class Amoungusmod_kingdomsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Customstorage.AddAllINV_ITEMS();
    }
}
