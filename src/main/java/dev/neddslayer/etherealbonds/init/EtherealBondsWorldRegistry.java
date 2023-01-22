package dev.neddslayer.etherealbonds.init;

import dev.neddslayer.etherealbonds.EtherealBonds;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class EtherealBondsWorldRegistry {

    public static RegistryKey<World> ETHEREAL_PLANE;

    public static void init() {
        ETHEREAL_PLANE = RegistryKey.of(RegistryKeys.WORLD, new Identifier(EtherealBonds.MODID, "ethereal_plane"));
    }

}
