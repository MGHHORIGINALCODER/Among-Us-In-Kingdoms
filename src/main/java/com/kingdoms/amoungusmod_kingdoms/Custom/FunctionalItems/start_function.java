package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.DissapearAfterCall;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.LockBlock;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.UpdateAfterCall;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.registry.Registries;

public class start_function extends Item {
    public start_function(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        super.use(world, player, hand);
        Customstorage.ROUND_STARTED = true;
        Customstorage.VENTERS.clear();
        Customstorage.PLAYERS_IN_ROUNDS.addAll(world.getPlayers());

        ItemStack[] tasks = new ItemStack[]{
                new ItemStack(ModItems.TASK1), new ItemStack(ModItems.TASK2), new ItemStack(ModItems.TASK3),
                new ItemStack(ModItems.TASK4), new ItemStack(ModItems.TASK5), new ItemStack(ModItems.TASK6),
                new ItemStack(ModItems.TASK7), new ItemStack(ModItems.TASK8), new ItemStack(ModItems.TASK9),
                new ItemStack(ModItems.TASK10)
        };

        ItemStack itemStack = player.getStackInHand(hand);

        if (!world.isClient) {
            Customstorage.IMPOSTERS.clear();
            Customstorage.MAX_TASKS = (world.getPlayers().size() - Customstorage.NUMBER_OF_IMPOSTERS) * Customstorage.TASKS_PER_PLAYER;
            Customstorage.TASKS_DONE = 0;
            Customstorage.CAN_KILL = true;
            LockBlock.On_ = true;

            UpdateAfterCall.DisableSelf(player.getWorld(), player.getBlockPos(), 500, ModBlocks.LOCKER_BLOCK);
            DissapearAfterCall.DisableSelf(world, player.getBlockPos(), 150, ModBlocks.DEATH_BLOCK);
            murdered.DEAD = 0;
            itemStack.decrement(1);

            // Clear inventories for all players
            for (PlayerEntity play : world.getPlayers()) {
                play.getInventory().clear();
            }

            // Distribute items and process setup per player
            for (PlayerEntity play : world.getPlayers()) {
                CommandManager commandManager = world.getServer().getCommandManager();
                ServerCommandSource commandSource = world.getServer().getCommandSource();
                String playerName = play.getName().getString();

                // Teleport to emergency meeting spawn
                commandManager.executeWithPrefix(commandSource, "tp " + playerName + " " + Customstorage.MeetingSpawn.getX() + " " + Customstorage.MeetingSpawn.getY() + " " + Customstorage.MeetingSpawn.getZ());
                play.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 10, false, false, false));

                // Hand out random crewmate tasks
                for (int i = 0; i < Customstorage.TASKS_PER_PLAYER; ++i) {
                    int random1 = (new Random()).nextInt(0, tasks.length);
                    commandManager = world.getServer().getCommandManager();
                    commandSource = world.getServer().getCommandSource();
                    playerName = play.getName().getString();
                    commandManager.executeWithPrefix(commandSource, "give " + playerName + " " + String.valueOf(Registries.ITEM.getId(tasks[random1].getItem())));
                }

                // Give core interactive utility items
                commandManager = world.getServer().getCommandManager();
                commandSource = world.getServer().getCommandSource();
                playerName = play.getName().getString();
                commandManager.executeWithPrefix(commandSource, "give " + playerName + " " + String.valueOf(Registries.ITEM.getId(ModItems.REPORT)));

                commandManager = world.getServer().getCommandManager();
                commandSource = world.getServer().getCommandSource();
                playerName = play.getName().getString();
                commandManager.executeWithPrefix(commandSource, "give " + playerName + " " + String.valueOf(Registries.ITEM.getId(ModItems.EMERGENCY_MEETING)) + " " + Customstorage.MEETING_USE);

                commandManager = world.getServer().getCommandManager();
                commandSource = world.getServer().getCommandSource();
                playerName = play.getName().getString();
                commandManager.executeWithPrefix(commandSource, "give " + playerName + " " + String.valueOf(Registries.ITEM.getId(Items.REDSTONE)) + " 64");

                world.getServer().getCommandManager().executeWithPrefix(world.getServer().getCommandSource(), "team join tagVis " + play.getName().getString());

                if (play instanceof ServerPlayerEntity serverPlayerEntity) {
                    Customstorage.SendTitleAndSubtitle(serverPlayerEntity, "Crewmate", "Complete Tasks and Find the Imposter", 5, 5, 5, Formatting.WHITE, Formatting.BOLD, Formatting.AQUA, Formatting.BOLD);
                    serverPlayerEntity.changeGameMode(GameMode.ADVENTURE);
                }
            }

            // Select and allocate Imposters randomly
            List<? extends PlayerEntity> allPlayers = new ArrayList<>(world.getPlayers());
            Collections.shuffle(allPlayers);
            int impostersToAssign = Math.min(Customstorage.NUMBER_OF_IMPOSTERS, allPlayers.size());
            Customstorage.ALIVE_IMPOSTERS = Customstorage.NUMBER_OF_IMPOSTERS;

            for (int i = 0; i < impostersToAssign; ++i) {
                PlayerEntity player_imposter = allPlayers.get(i);
                if (Customstorage.IMPOSTERS.contains(player_imposter)) {
                    ++impostersToAssign;
                } else {
                    Customstorage.IMPOSTERS.add(player_imposter);
                }
            }

            // Distribute Imposter weapons and roles
            for (PlayerEntity ip : Customstorage.IMPOSTERS) {
                CommandManager commandManager = world.getServer().getCommandManager();
                ServerCommandSource commandSource = world.getServer().getCommandSource();
                String imposterName = ip.getName().getString();

                commandManager.executeWithPrefix(commandSource, "give " + imposterName + " " + String.valueOf(Registries.ITEM.getId(ModItems.SAB)));

                commandManager = world.getServer().getCommandManager();
                commandSource = world.getServer().getCommandSource();
                imposterName = ip.getName().getString();
                commandManager.executeWithPrefix(commandSource, "give " + imposterName + " " + String.valueOf(Registries.ITEM.getId(ModItems.MURDERED)));

                world.getServer().getCommandManager().executeWithPrefix(world.getServer().getCommandSource(), "team join imposters " + ip.getName().getString());
                Customstorage.VENTERS.add(ip);

                if (ip instanceof ServerPlayerEntity serverPlayerEntity) {
                    Customstorage.SendTitleAndSubtitle(serverPlayerEntity, "Imposter", "Kill all crewmates and be hidden", 5, 5, 5, Formatting.WHITE, Formatting.BOLD, Formatting.RED, Formatting.BOLD);
                }

                ip.sendMessage(Text.literal("ROLE: Imposter : >\nTEAM:").styled((style) -> style.withColor(Formatting.RED)), false);

                for (PlayerEntity ipe : Customstorage.IMPOSTERS) {
                    ip.sendMessage(Text.literal(ipe.getName().getString()).styled((style) -> style.withColor(Formatting.RED)), false);
                }
            }

            // Restored and completed cut-off audio statement safely
            world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), ModSounds.START_SOUND, SoundCategory.MASTER, 1.0F, 1.0F);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
