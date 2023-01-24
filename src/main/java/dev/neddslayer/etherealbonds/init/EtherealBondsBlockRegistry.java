package dev.neddslayer.etherealbonds.init;

import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static dev.neddslayer.etherealbonds.EtherealBonds.LOGGER;
import static dev.neddslayer.etherealbonds.EtherealBonds.MODID;

public interface EtherealBondsBlockRegistry {

    Map<Identifier, Block> BLOCKS =  new LinkedHashMap<>();

    Block ETHEREAL_SOIL = register("ethereal_soil", new Block(AbstractBlock.Settings.of(Material.SOIL, MapColor.PURPLE).strength(0.75f).sounds(BlockSoundGroup.GRAVEL)));
    Block ETHEREAL_TOPSOIL = register("ethereal_topsoil", new Block(AbstractBlock.Settings.of(Material.SOIL, MapColor.PURPLE).strength(0.85f).sounds(BlockSoundGroup.GRAVEL)));
    Block ETHEREAL_STONE = register("ethereal_stone", new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.PURPLE).strength(1.5f).sounds(BlockSoundGroup.STONE)));
    Block GREEN_TOURMALINE_ORE = register("green_tourmaline_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.GREEN).strength(3).requiresTool().sounds(BlockSoundGroup.STONE)));

    static <T extends Block> T register(String id, T block) {
        BLOCKS.put(new Identifier(MODID, id), block);
        return block;
    }

    static void init() {
        LOGGER.info("Registering blocks...");
        BLOCKS.forEach(
            (id, item) -> Registry.register(Registries.BLOCK, id, item));
        LOGGER.info("Block registration complete!");
    }

}
