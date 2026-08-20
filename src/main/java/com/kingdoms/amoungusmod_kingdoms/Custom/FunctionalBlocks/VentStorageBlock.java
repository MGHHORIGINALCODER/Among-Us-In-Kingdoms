package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.Customstorage;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VentStorageBlock extends Block {
    public static final List<BlockPos> Vents = new ArrayList<>();


    public VentStorageBlock(Settings settings) {
        super(settings);
    }
    public void scan(int Raid,World world,BlockPos Center){
        Vents.clear();
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        double RaidSq = Raid * Raid;
        if(!world.isClient){
            for (int x = -Raid; x <= Raid; x++) {
                for (int y = -Raid; y <= Raid; y++) {
                    for (int z = -Raid; z <= Raid; z++) {
                        if (x * x + y * y + z * z <= RaidSq) {
                            mutablePos.set(Center.getX() + x, Center.getY() + y, Center.getZ() + z);
                            BlockState BS = world.getBlockState(mutablePos);

                            if(BS.getBlock() instanceof VentStorageBlock ve && !mutablePos.toImmutable().equals(Center)){
                                Vents.add(mutablePos.toImmutable());
                            }
                        }

                    }
                }
            }


        }
    }


    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(!world.isClient()){


            if(entity instanceof PlayerEntity player && player.isSneaking() && Customstorage.VENTERS.contains(player)){
                scan(Customstorage.RAID_VENT,world,pos);
                BlockPos blockPos= Vents.get(new Random().nextInt(Vents.size()));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 20, 1, false, false, false));

                if(!player.getBlockPos().equals(blockPos)) {
                    player.teleport(blockPos.getX(),blockPos.getY()+1,blockPos.getZ(),true);
                    world.playSound(
                            player,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            ModSounds.VENT_SOUND,
                            SoundCategory.PLAYERS,
                            1f,
                            1f
                    );
                }

            }
        }
    }

}
