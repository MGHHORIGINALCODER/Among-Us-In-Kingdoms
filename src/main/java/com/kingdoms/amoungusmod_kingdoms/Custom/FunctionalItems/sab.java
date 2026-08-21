package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.UpdateAfterCall;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class sab extends Item {
    public sab(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        super.use(world, player, hand);

        if(!world.isClient()){

            for(PlayerEntity play : world.getPlayers()){
                //play.playSound(ModSounds.CALL_MEETING);
                UpdateAfterCall.DisableSelf(world,player.getBlockPos(),150,ModBlocks.LOCKER_BLOCK);
                if (!Customstorage.IMPOSTERS.contains(play)) {

                    play.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 600, 12, false, false, true));
                    play.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 1000, 10, false, false, true));
                    play.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 1, false, false, true));

                }
                play.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 40, 0, false, false, true));


            }

            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ModSounds.CALL_MEETING,
                    SoundCategory.PLAYERS,
                    0.5f,
                    1.5f
            );
            player.getItemCooldownManager().set(player.getStackInHand(hand).getItem(), 600);



        }
        return TypedActionResult.success(player.getStackInHand(hand), world.isClient);




    }
}
