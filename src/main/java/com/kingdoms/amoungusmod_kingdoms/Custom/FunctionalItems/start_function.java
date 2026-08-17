package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.DissapearAfterCall;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class start_function extends Item {

    public start_function(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        super.use(world, player, hand);
        ItemStack[] tasks = {
                new ItemStack(ModItems.TASK1),
                new ItemStack(ModItems.TASK2),
                new ItemStack(ModItems.TASK3),
                new ItemStack(ModItems.TASK4),
                new ItemStack(ModItems.TASK5),
                new ItemStack(ModItems.TASK6),
                new ItemStack(ModItems.TASK7),
                new ItemStack(ModItems.TASK8),
                new ItemStack(ModItems.TASK9),
                new ItemStack(ModItems.TASK10)
        };

        ItemStack itemStack = player.getStackInHand(hand);

        if(!world.isClient){
            Customstorage.IMPOSTERS.clear();
            Customstorage.MAX_TASKS= (world.getPlayers().size() - Customstorage.NUMBER_OF_IMPOSTERS) * Customstorage.TASKS_PER_PLAYER;
            Customstorage.TASKS_DONE= 0;
            Customstorage.CAN_KILL=true;

            DissapearAfterCall.DisableSelf(world,player.getBlockPos(),150, ModBlocks.DEATH_BLOCK);
            murdered.DEAD=0;
            itemStack.decrement(1);
            for (PlayerEntity play : world.getPlayers()) {
                play.getInventory().clear();
            }

            for(PlayerEntity play : world.getPlayers()){
                //play.playSound(ModSounds.CALL_MEETING);




                world.getServer().getCommandManager().executeWithPrefix(
                        world.getServer().getCommandSource(),
                        "tp " + play.getName().getString() + " 27 70 118"
                );


                play.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 10, false, false, false));

                for (int i = 0; i < Customstorage.TASKS_PER_PLAYER; i++) {
                    int random1 = new Random().nextInt(0, tasks.length);

                    world.getServer().getCommandManager().executeWithPrefix(
                            world.getServer().getCommandSource(),
                            "give " + play.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(tasks[random1].getItem())
                    );
                }




                world.getServer().getCommandManager().executeWithPrefix(
                        world.getServer().getCommandSource(),
                        "give " + play.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(ModItems.REPORT)
                );
                world.getServer().getCommandManager().executeWithPrefix(
                        world.getServer().getCommandSource(),
                        "give " + play.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(ModItems.EMERGENCY_MEETING)+" 2"
                );
                world.getServer().getCommandManager().executeWithPrefix(
                        world.getServer().getCommandSource(),
                        "team join tagVis "+play.getName().getString()
                );
                play.sendMessage(Text.literal("ROLE: Crewmate : >").styled(style -> style.withColor(Formatting.GREEN)),false);

                if(play instanceof ServerPlayerEntity sp){
                    sp.changeGameMode(GameMode.SURVIVAL);
                }



            }

            List<? extends PlayerEntity> allPlayers = new ArrayList<>(world.getPlayers());
            Collections.shuffle(allPlayers);

            int impostersToAssign = Math.min(Customstorage.NUMBER_OF_IMPOSTERS,allPlayers.size());


            for (int i = 0; i < impostersToAssign; i++) {
                PlayerEntity player_imposter = allPlayers.get(i);
                Customstorage.IMPOSTERS.add(player_imposter);
            }
            for(PlayerEntity ip : Customstorage.IMPOSTERS){
                world.getServer().getCommandManager().executeWithPrefix(
                        world.getServer().getCommandSource(),
                        "give " + ip.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(ModItems.SAB)
                );
                world.getServer().getCommandManager().executeWithPrefix(
                        world.getServer().getCommandSource(),
                        "give " + ip.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(ModItems.MURDERED)
                );
                world.getServer().getCommandManager().executeWithPrefix(
                        world.getServer().getCommandSource(),
                        "team join imposters "+ip.getName().getString()
                );

                ip.sendMessage(Text.literal("ROLE: Imposter : >\nTEAM:").styled(style -> style.withColor(Formatting.RED)),false);
                for(PlayerEntity ipe : Customstorage.IMPOSTERS){
                ip.sendMessage(Text.literal(ipe.getName().getString()).styled(style -> style.withColor(Formatting.RED)),false);
                }

            }





            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ModSounds.START_SOUND,
                    SoundCategory.PLAYERS,
                    1.0f,
                    1.0f
            );



        }
        return TypedActionResult.success(itemStack, world.isClient);




    }
}