package dev.neddslayer.etherealbonds.init;

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


    static <T extends Item> T register(String id, T item) {
        ITEMS.put(new Identifier(MODID, id), item);
        return item;
    }

    static void init() {
        LOGGER.info("Registering items...");
        ITEMS.forEach(
            (id, item) -> Registry.register(Registries.ITEM, id, item));
        LOGGER.info("Item registry completed!");
    }
}
