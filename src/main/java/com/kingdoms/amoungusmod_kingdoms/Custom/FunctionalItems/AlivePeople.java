package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AlivePeople extends Item {
    public AlivePeople(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        for(PlayerEntity player: world.getPlayers()){
            if(player.isSpectator()){
                MutableText message = Text.literal(player.getName().getString()+" - Dead").formatted(Formatting.RED);
                user.sendMessage(message,false);
            }else{
                MutableText message = Text.literal(player.getName().getString()+" - Alive").formatted(Formatting.GREEN);
                user.sendMessage(message,false);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
    }
}
