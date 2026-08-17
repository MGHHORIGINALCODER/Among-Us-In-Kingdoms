package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;


import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.Death_Block;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

public class murdered extends Item {
    public murdered(Settings settings) {
        super(settings);
    }
    public static int DEAD=0;

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand)  {
        super.useOnEntity(stack, player, entity, hand);
        World world=player.getEntityWorld();

        if(!world.isClient() && !player.getItemCooldownManager().isCoolingDown(stack.getItem()) && Customstorage.CAN_KILL){

            if(entity instanceof ServerPlayerEntity player1) {
                if(ImpCheck.I(player1)){
                    return ActionResult.SUCCESS;
                }
                player1.kill();
                player1.changeGameMode(GameMode.SPECTATOR);
                DEAD+=1;

                world.setBlockState(new BlockPos(player.getBlockPos().getX(),(player.getBlockPos().getY()+1),player.getBlockPos().getZ()), ModBlocks.DEATH_BLOCK.getDefaultState());
                BlockState state = world.getBlockState(player.getBlockPos());
                if(state.getBlock() instanceof Death_Block deathBlock){
                    deathBlock.Owner=player;
                }
                player.getItemCooldownManager().set(player.getStackInHand(hand).getItem(), 400);


                world.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        ModSounds.MURDER_SOUND,
                        SoundCategory.PLAYERS,
                        1.0f,
                        1.0f
                );
                if(DEAD>=world.getPlayers().size()-Customstorage.NUMBER_OF_IMPOSTERS){
                    for(PlayerEntity play : world.getPlayers()){
                        play.getInventory().clear();
                        world.getServer().getCommandManager().executeWithPrefix(
                                world.getServer().getCommandSource(),
                                "tp " + play.getName().getString() + " 27 70 118"
                        );


                        play.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 10, false, false, false));
                        if(play instanceof ServerPlayerEntity sp){
                            sp.changeGameMode(GameMode.SURVIVAL);
                        }

                    }
                    world.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            ModSounds.DEFEAT_SOUND,
                            SoundCategory.PLAYERS,
                            1.0f,
                            1.0f
                    );
                    MutableText message2 = Text.literal("Imposters Win!");
                    world.getServer().getPlayerManager().broadcast(message2, false);
                }


            }



        }
        return ActionResult.SUCCESS;




    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {return TypedActionResult.success(user.getInventory().getMainHandStack(), world.isClient);}

}
