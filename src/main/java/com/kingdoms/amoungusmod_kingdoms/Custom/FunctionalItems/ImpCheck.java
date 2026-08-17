package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import net.minecraft.entity.player.PlayerEntity;

public class ImpCheck {
    public static boolean I(PlayerEntity player){
        if(Customstorage.IMPOSTERS.contains(player)) {
            return true;
        }
        return false;
    }
}
