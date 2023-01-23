package dev.neddslayer.etherealbonds.init;

import com.sammy.lodestone.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import dev.neddslayer.etherealbonds.client.particle.emitter.CrystallizedEtherealEnergyParticleEmitter;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

import static dev.neddslayer.etherealbonds.EtherealBonds.LOGGER;
import static dev.neddslayer.etherealbonds.EtherealBonds.MODID;
import static dev.neddslayer.etherealbonds.init.EtherealBondsItemRegistry.CRYSTALLIZED_ETHEREAL_ENERGY;


public interface EtherealBondsParticleEmitterRegistry {

    RegistryKey<Registry<Pair<ItemParticleEmitter, Item[]>>> PARTICLE_EMITTER_KEY = RegistryKey.ofRegistry(new Identifier(MODID, "particle_emitter"));
    Registry<Pair<ItemParticleEmitter, Item[]>> PARTICLE_EMITTER = Registries.registerSimple(PARTICLE_EMITTER_KEY, registry -> EtherealBondsParticleEmitterRegistry.CRYSTALLIZED_ETHEREAL_ENERGY_PARTICLE_EMITTER);
    Map<Identifier, Pair<ItemParticleEmitter, Item[]>> PARTICLE_EMITTERS = new LinkedHashMap<>();

    Pair<ItemParticleEmitter, Item[]> CRYSTALLIZED_ETHEREAL_ENERGY_PARTICLE_EMITTER       = register("crystallized_ethereal_energy_particle_emitter",    new CrystallizedEtherealEnergyParticleEmitter(), CRYSTALLIZED_ETHEREAL_ENERGY);

    static <T extends ItemParticleEmitter> Pair<T, Item[]> register(String id, T particleEmitter, Item... items) {
        PARTICLE_EMITTERS.put(new Identifier(MODID, id), new Pair<>(particleEmitter, items));
        return new Pair<>(particleEmitter, items);
    }

    static void init() {
        LOGGER.info("Registering particle emitters...");
        PARTICLE_EMITTERS.forEach((id, pair) -> Registry.register(EtherealBondsParticleEmitterRegistry.PARTICLE_EMITTER, id, pair));
        LOGGER.info("Particle emitter registration complete!");
    }

}
