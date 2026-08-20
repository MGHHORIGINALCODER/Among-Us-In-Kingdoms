package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;


import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LocationConfig extends Item {
    public LocationConfig(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            if(player.isSneaking()){
                Customstorage.NormalSpawn=player.getBlockPos();
                world.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        ModSounds.CHANGE_SETTING_DOWN,
                        SoundCategory.PLAYERS,
                        1f,
                        1.5f
                );
                MutableText message = Text.literal("Spawn Place TP: "+Customstorage.NormalSpawn.toString());
                world.getServer().getPlayerManager().broadcast(message, false);
            } else{
                Customstorage.MeetingSpawn=player.getBlockPos();
                world.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        ModSounds.CHANGE_SETTING_UP,
                        SoundCategory.PLAYERS,
                        1f,
                        1.5f
                );
                MutableText message = Text.literal("Meeting TP: "+Customstorage.MeetingSpawn.toString());
                world.getServer().getPlayerManager().broadcast(message, false);
            }

        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

}
