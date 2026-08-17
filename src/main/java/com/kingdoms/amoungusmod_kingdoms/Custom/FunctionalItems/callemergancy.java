package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.DissapearAfterCall;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class callemergancy extends Item {
    public callemergancy(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        super.use(world, player, hand);

        ItemStack itemStack = player.getStackInHand(hand);

        if(!world.isClient && Customstorage.CAN_KILL){
            DissapearAfterCall.DisableSelf(world,player.getBlockPos(),150, ModBlocks.DEATH_BLOCK);

            for(PlayerEntity p : world.getPlayers()){
                if(p.isSpectator()){
                    MutableText message = Text.literal(p.getName().getString()+" Is Dead");
                    world.getServer().getPlayerManager().broadcast(message, false);
                }
            }

            DissapearAfterCall.DisableSelf(world,player.getBlockPos(),150, ModBlocks.DEATH_BLOCK);
            Customstorage.CAN_KILL=false;
            itemStack.decrement(1);
            for(PlayerEntity play : world.getPlayers()){
                //play.playSound(ModSounds.CALL_MEETING);
                world.getServer().getCommandManager().executeWithPrefix(
                        world.getServer().getCommandSource(),
                        "tp " + play.getName().getString() + " 27 70 118"
                );
                world.getServer().getCommandManager().executeWithPrefix(
                        world.getServer().getCommandSource(),
                        "give " + play.getName().getString() + " " + net.minecraft.registry.Registries.ITEM.getId(ModItems.VOTE)
                );


            }
            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ModSounds.CALL_MEETING,
                    SoundCategory.PLAYERS,
                    1.0f,
                    1.0f
            );

            if (player instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.getItemCooldownManager().set(this, 10); // restores the old Settings#useCooldown(10)
            }

        }
        return TypedActionResult.success(itemStack, world.isClient);




    }
}