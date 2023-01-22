package dev.neddslayer.etherealbonds.init;

import dev.neddslayer.etherealbonds.EtherealBonds;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class EtherealBondsWorldRegistry {

    public static final RegistryKey<DimensionOptions> ETHEREAL_PLANE_OPTIONS = RegistryKey.of(RegistryKeys.DIMENSION, new Identifier(EtherealBonds.MODID, "ethereal_plane"));
    public static RegistryKey<World> ETHEREAL_PLANE = RegistryKey.of(RegistryKeys.WORLD, ETHEREAL_PLANE_OPTIONS.getValue());

    public static void init() {
        ETHEREAL_PLANE = RegistryKey.of(RegistryKeys.WORLD,  new Identifier(EtherealBonds.MODID, "ethereal_plane"));
    }

}
