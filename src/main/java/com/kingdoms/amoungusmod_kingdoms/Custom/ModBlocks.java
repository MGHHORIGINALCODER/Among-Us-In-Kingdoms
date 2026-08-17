package com.kingdoms.amoungusmod_kingdoms.Custom;

import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.Death_Block;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {

    public static final Block DEATH_BLOCK = registerBlock(
            "death_block",
            settings -> new Death_Block(
                    settings
                            .strength(10000.0f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.AMETHYST_BLOCK)
            )
    );

    public static final Block DOOR_BLOCK = registerBlock(
            "door_block",
            settings -> new Block(FabricBlockSettings.create().strength(4.0f))
    );










    private static Block registerBlock(
            String name,
            Function<AbstractBlock.Settings, Block> function
    ) {
        Block block = function.apply(
                AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
        );

        registerBlockItem(name, block);

        return Registry.register(
                Registries.BLOCK,
                new Identifier(Amoungusmod_kingdoms.MOD_ID, name),
                block
        );
    }

    private static Item registerBlockItem(String name, Block block) {
        Registry.register(
                Registries.ITEM,
                new Identifier(Amoungusmod_kingdoms.MOD_ID, name),
                new BlockItem(
                        block,
                        new Item.Settings()
                )
        );
        return null;
    }

    public static void registerModBlocks() {
        Amoungusmod_kingdoms.LOGGER.info(
                "Registering Mod Blocks for " +
                        Amoungusmod_kingdoms.MOD_ID
        );

        // Add blocks to the Building Blocks creative tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(
                entries -> {
                    entries.add(DEATH_BLOCK);


                }
        );
    }
}