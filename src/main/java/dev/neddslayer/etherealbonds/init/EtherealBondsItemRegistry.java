package dev.neddslayer.etherealbonds.init;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;
import java.util.Map;

import static dev.neddslayer.etherealbonds.EtherealBonds.MODID;
import static dev.neddslayer.etherealbonds.EtherealBonds.LOGGER;


public interface EtherealBondsItemRegistry {

    Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    Item CRYSTALLIZED_ETHEREAL_ENERGY = register("crystallized_ethereal_energy", new Item(new QuiltItemSettings().rarity(Rarity.RARE)));
    Item GREEN_TOURMALINE = register("green_tourmaline", new Item(new QuiltItemSettings()));
    Item ETHEREAL_SOIL = register("ethereal_soil", new BlockItem(EtherealBondsBlockRegistry.ETHEREAL_SOIL, new QuiltItemSettings()));
    Item ETHEREAL_TOPSOIL = register("ethereal_topsoil", new BlockItem(EtherealBondsBlockRegistry.ETHEREAL_TOPSOIL, new QuiltItemSettings()));
    Item ETHEREAL_STONE = register("ethereal_stone", new BlockItem(EtherealBondsBlockRegistry.ETHEREAL_STONE, new QuiltItemSettings()));
    Item GREEN_TOURMALINE_ORE = register("green_tourmaline_ore", new BlockItem(EtherealBondsBlockRegistry.GREEN_TOURMALINE_ORE, new QuiltItemSettings()));


    static <T extends Item> T register(String id, T item) {
        ITEMS.put(new Identifier(MODID, id), item);
        return item;
    }

    static void init() {
        LOGGER.info("Registering items...");
        ITEMS.forEach(
            (id, item) -> Registry.register(Registries.ITEM, id, item));
        LOGGER.info("Item registration complete!");
    }
}
