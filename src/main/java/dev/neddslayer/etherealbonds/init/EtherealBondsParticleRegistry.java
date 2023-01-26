package dev.neddslayer.etherealbonds.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static dev.neddslayer.etherealbonds.EtherealBonds.MODID;

public class EtherealBondsParticleRegistry {

    public static DefaultParticleType ETHEREAL_PLANE_AMBIENT_PARTICLE = FabricParticleTypes.simple();

    public static void init() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(MODID, "ethereal_plane_ambient_particle"), ETHEREAL_PLANE_AMBIENT_PARTICLE);
    }

}
