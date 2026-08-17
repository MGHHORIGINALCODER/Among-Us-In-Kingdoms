package com.kingdoms.amoungusmod_kingdoms;

import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItemGroups;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Amoungusmod_kingdoms implements ModInitializer {
    public static final String MOD_ID = "amoungusmod_kingdoms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModSounds.registerSounds();
        ModItemGroups.registerItemGroups();
        //ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
        //    if (entity instanceof ServerPlayerEntity player) {
        //        World world = player.getWorld();
        //        if (player.getLastDeathPos().isPresent()) {
        //            var deathPos = player.getLastDeathPos().get().getPos();
        //            int x = deathPos.getX();
        //            int y = deathPos.getY() + 1;
        //            int z = deathPos.getZ();
//
        //            world.getServer().getCommandManager().executeWithPrefix(
        //                    world.getServer().getCommandSource(),
        //                    "setblock " + x + " " + y + " " + z + " amoungusmod_kingdoms:death_block"
        //            );
        //        }
//
        //    }
        //});
    }


}
