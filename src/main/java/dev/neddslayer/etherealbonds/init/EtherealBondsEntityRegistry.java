package dev.neddslayer.etherealbonds.init;

import dev.neddslayer.etherealbonds.entity.EtherealPortalEntity;
import dev.neddslayer.etherealbonds.entity.StrandedTravelerEntity;
import dev.neddslayer.etherealbonds.entity.WaspEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

import static dev.neddslayer.etherealbonds.EtherealBonds.LOGGER;
import static dev.neddslayer.etherealbonds.EtherealBonds.MODID;

public class EtherealBondsEntityRegistry {

    public static EntityType<EtherealPortalEntity> ETHEREAL_PORTAL;
    public static EntityType<StrandedTravelerEntity> STRANDED_TRAVELER;
    public static EntityType<WaspEntity> WASP;

    public static void init() {
        if (QuiltLoader.isDevelopmentEnvironment()) LOGGER.info("Registering entities...");
        ETHEREAL_PORTAL = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MODID, "ethereal_portal"),
            QuiltEntityTypeBuilder.create(SpawnGroup.MISC, EtherealPortalEntity::new)
                .setDimensions(EntityDimensions.fixed(3F, 1.25F)).maxBlockTrackingRange(90).trackingTickInterval(1).alwaysUpdateVelocity(true)
                .build());
        STRANDED_TRAVELER = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MODID, "stranded_traveler"),
            QuiltEntityTypeBuilder.create(SpawnGroup.MONSTER, StrandedTravelerEntity::new)
                .setDimensions(EntityDimensions.fixed(0.6F, 1.8F)).maxChunkTrackingRange(20).trackingTickInterval(1).maxBlockTrackingRange(90).alwaysUpdateVelocity(true)
                .build());
        WASP = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MODID, "wasp"),
            QuiltEntityTypeBuilder.create(SpawnGroup.MONSTER, WaspEntity::new)
                .setDimensions(EntityDimensions.fixed(0.15F, 0.2F)).maxBlockTrackingRange(256).maxChunkTrackingRange(90).trackingTickInterval(1).alwaysUpdateVelocity(true)
                .build());
        FabricDefaultAttributeRegistry.register(STRANDED_TRAVELER, StrandedTravelerEntity.createHostileAttributes());
        FabricDefaultAttributeRegistry.register(WASP, WaspEntity.createWaspAttributes());
        if (QuiltLoader.isDevelopmentEnvironment()) LOGGER.info("Entity registration complete!");
    }

}
