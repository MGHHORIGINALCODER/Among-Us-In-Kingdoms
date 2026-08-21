package com.kingdoms.amoungusmod_kingdoms;

import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.ModEntities;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.custom.PurpEntity;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.custom.TestEntity;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItemGroups;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.UpdateAfterCall;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.Customstorage;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.util.Formatting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.registry.Registries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Amoungusmod_kingdoms implements ModInitializer {
    public static final String MOD_ID = "amoungusmod_kingdoms";
    public static final Logger LOGGER = LoggerFactory.getLogger("amoungusmod_kingdoms");
    private static int tickTimer = 0;

    public Amoungusmod_kingdoms() {
    }

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModSounds.registerSounds();
        ModItemGroups.registerItemGroups();


        FabricDefaultAttributeRegistry.register(ModEntities.TEST_ENTITY, TestEntity.createTestEntityA());
        FabricDefaultAttributeRegistry.register(ModEntities.PURP_ENTITY, PurpEntity.createTestEntityA());


        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerEntity player = handler.getPlayer();

            // Handle Custom Spawn Location Teleport
            if (Customstorage.NormalSpawn != null) {
                player.requestTeleport(
                        (double)Customstorage.NormalSpawn.getX(),
                        (double)Customstorage.NormalSpawn.getY(),
                        (double)Customstorage.NormalSpawn.getZ()
                );
            }


            if (Customstorage.ROUND_STARTED && !Customstorage.PLAYERS_IN_ROUNDS.contains(player) && player instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.changeGameMode(GameMode.SPECTATOR);
            }


            if (server.getCurrentPlayerCount() <= 1) {
                Customstorage.Owner = player;
                CommandManager commandManager = player.getWorld().getServer().getCommandManager();
                ServerCommandSource commandSource = player.getWorld().getServer().getCommandSource();
                String playerName = player.getName().getString();

                commandManager.executeWithPrefix(commandSource, "give " + playerName + " " + String.valueOf(Registries.ITEM.getId(ModItems.START)));
                UpdateAfterCall.DisableSelf(player.getWorld(), player.getBlockPos(), 500, ModBlocks.LOCKER_BLOCK);
            }
        });

        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            if (Customstorage.TIMER_ON) {
                ++tickTimer;
                if (tickTimer >= 20) {
                    tickTimer = 0;
                    if (Customstorage.LENGTH_TIMER >= 0) {
                        if (Customstorage.LENGTH_TIMER == 5) {
                            server.getPlayerManager().getPlayerList().forEach((player) -> {
                                World world = player.getServerWorld();
                                world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), ModSounds.COUNTDOWN_SOUND, SoundCategory.MASTER, 1.0F, 1.0F);
                            });
                        }

                        // Broadcast Countdown to Action Bar in Dark Red
                        server.getPlayerManager().broadcast(Text.literal(Customstorage.State_Timer + Customstorage.LENGTH_TIMER).formatted(Formatting.WHITE).formatted(Formatting.BOLD), true);
                        --Customstorage.LENGTH_TIMER;
                    } else {
                        // Reset and run the scheduled timer task
                        Customstorage.TIMER_ON = false;
                        Customstorage.LENGTH_TIMER = 30;
                        Customstorage.TimerExacute.run();
                    }
                }
            }
        });


    }
}
