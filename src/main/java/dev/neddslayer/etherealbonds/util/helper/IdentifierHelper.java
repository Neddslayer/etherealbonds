package dev.neddslayer.etherealbonds.util.helper;

import net.minecraft.util.Identifier;

import static dev.neddslayer.etherealbonds.EtherealBonds.MODID;

public class IdentifierHelper {
    public static Identifier geo(String id) {
        return new Identifier(MODID, "geo/" + id + ".geo.json");
    }

    public static Identifier textureEntity(String id) {
        return new Identifier(MODID, "textures/entity/" + id + ".png");
    }

    public static Identifier textureItem(String id) {
        return new Identifier(MODID, "textures/item/" + id + ".png");
    }

    public static Identifier animation(String id) {
        return new Identifier(MODID, "animations/" + id + ".animation.json");
    }

}
