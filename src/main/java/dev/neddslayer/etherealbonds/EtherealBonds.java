package dev.neddslayer.etherealbonds;

import dev.neddslayer.etherealbonds.init.*;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents;
import org.quiltmc.qsl.worldgen.dimension.api.QuiltDimensions;

import static net.minecraft.entity.EntityType.COW;

public class EtherealBonds implements ModInitializer {

    public static final String MODID = "etherealbonds";
    public static final Logger LOGGER = LogManager.getLogger("Ethereal Bonds");

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Registering Ethereal Bonds!");
        EtherealBondsBlockRegistry.init();
        EtherealBondsItemRegistry.init();
        EtherealBondsEntityRegistry.init();
        EtherealBondsParticleRegistry.init();
        EtherealBondsWorldRegistry.init();

        ServerLifecycleEvents.READY.register(server -> {
            if (QuiltLoader.isDevelopmentEnvironment()) {
                ServerWorld overworld = server.getWorld(World.OVERWORLD);
                ServerWorld world = server.getWorld(EtherealBondsWorldRegistry.ETHEREAL_PLANE);

                LOGGER.info("Running Ethereal Plane's entity test!");

                if (world == null) throw new AssertionError("Test world doesn't exist.");

                Entity entity = COW.create(overworld);

                if (entity == null) throw new AssertionError("Could not create entity!");
                if (!entity.world.getRegistryKey().equals(World.OVERWORLD))
                    throw new AssertionError("Entity starting world isn't the overworld");

                TeleportTarget target = new TeleportTarget(Vec3d.ZERO, new Vec3d(1, 1, 1), 45f, 60f);

                Entity teleported = QuiltDimensions.teleport(entity, world, target);

                if (teleported == null) throw new AssertionError("Entity didn't teleport");

                if (!teleported.world.getRegistryKey().equals(EtherealBondsWorldRegistry.ETHEREAL_PLANE))
                    throw new AssertionError("Target world not reached.");

                if (!teleported.getPos().equals(target.position))
                    throw new AssertionError("Target Position not reached.");
            }
        });
        LOGGER.info("Ethereal Bonds Registered!");
    }
}
