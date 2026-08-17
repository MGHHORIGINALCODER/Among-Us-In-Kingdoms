package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;

public class Death_Block extends Block {
    public Death_Block(Settings settings) {
        super(settings);
    }
    public PlayerEntity Owner=null;

}
