package dev.neddslayer.etherealbonds.entity;

import dev.neddslayer.etherealbonds.init.EtherealBondsWorldRegistry;
import eu.midnightdust.lib.config.MidnightConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import static dev.neddslayer.etherealbonds.EtherealBonds.LOGGER;

import javax.annotation.Nullable;
import java.util.List;

public class EtherealPortalEntity extends Entity implements GeoEntity {

    private EtherealPortalGeometry geometry;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean invalid;

    private boolean wasStable = false;
    private boolean wasUnstable = false;

    public boolean isUnstable() {
        return unstable;
    }


    private boolean unstable = false;
    private boolean shouldBeUnstable = false;

    public EtherealPortalEntity(EntityType<?> variant, World world) {
        super(variant, world);
        this.noClip = true;
        this.initGeometry(RandomGenerator.createThreaded().nextLong());
    }

    private void initGeometry(long seed) {
        this.geometry = new EtherealPortalGeometry(seed, this.getWidth(), this.getHeight());
    }




    @Override
    protected void initDataTracker() {

    }


    @Override
    public void tick() {
        if (this.invalid) {
            this.discard();
            return;
        }

        super.tick();

        if (this.world instanceof ServerWorld) {

            ServerPlayerEntity p = (ServerPlayerEntity) this.world.getClosestPlayer(this, 1);
            if (p != null) {
                ServerWorld serverWorld = (ServerWorld) this.world;
                MinecraftServer minecraftServer = serverWorld.getServer();
                RegistryKey<World> registryKey = this.world.getRegistryKey() == EtherealBondsWorldRegistry.ETHEREAL_PLANE ? World.OVERWORLD : EtherealBondsWorldRegistry.ETHEREAL_PLANE;
                ServerWorld serverWorld2 = minecraftServer.getWorld(registryKey);
                if (serverWorld2 != null && !p.hasVehicle() && p.world.getRegistryKey() != registryKey) {
                    p.moveToWorld(serverWorld2);
                }
            }
            if (!isMidnightDimension(this.world)) {
                this.updateOverworldBehavior();
            }
        }

    }

    private void updateOverworldBehavior() {
        this.shouldBeUnstable = this.world.isDay() || this.world.getLightLevel(LightType.BLOCK, this.getBlockPos()) > 5;

        if (this.isUnstable()) {
            this.pullEntities();
        }
    }

    private void pullEntities() {
        Box pullBounds = this.getBoundingBox().expand(20.0F);
        List<Entity> entities = this.world.getEntitiesByClass(Entity.class, pullBounds, EntityPredicates.EXCEPT_SPECTATOR);
        for (Entity entity : entities) {
            if (!(entity instanceof EtherealPortalEntity)) {
                this.pullEntity(entity);
            }
        }
    }

    public void pullEntity(Entity entity) {

        double deltaX = this.getX() - entity.getX();
        double deltaY = (this.getY() + this.getHeight()) - (entity.getY() + entity.getHeight() / 2.0F);
        double deltaZ = this.getZ() - entity.getZ();

        double distanceSq = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

        //LOGGER.info("Pulling entity: " + entity.getEntityName());

        if (distanceSq > 0.0) {
            double distance = Math.sqrt(distanceSq);
            double intensity = MathHelper.clamp(6f / distanceSq, 0.0F, 1.0F);

            double velocityX = entity.getVelocity().x + (deltaX / distance) * intensity;
            double velocityY = entity.getVelocity().y + (deltaY / distance) * intensity;
            double velocityZ = entity.getVelocity().z + (deltaZ / distance) * intensity;

            Vec3d velocity = new Vec3d(velocityX, velocityY, velocityZ);
            if (velocity.lengthSquared() > 1.2*1.2) {
                velocity.normalize();
                velocity.multiply(1.2);
            }
            if (distance < 1.5) {
                velocity.multiply(0.4);
            }

            if (!world.isClient) {
                entity.setVelocity(velocity);
                if (entity instanceof PlayerEntity p)
                    p.velocityModified = true;
                entity.velocityDirty = true;
            }


            entity.fallDistance = 0.0F;
        }
    }

    public static boolean isMidnightDimension(@Nullable World world) {
        return world != null && world.getDimensionKey() != DimensionTypes.OVERWORLD;
    }


    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.age = nbt.getInt("age");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("age", this.age);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
            new AnimationController<>(this, "livingController", 2, state -> {
                if (this.unstable && this.shouldBeUnstable)
                    return state.setAndContinue(RawAnimation.begin().thenLoop("unstable.idle"));
                else if (!this.unstable && !this.shouldBeUnstable)
                    return state.setAndContinue(RawAnimation.begin().thenLoop("stable.idle"));
                else if (this.wasStable && !this.wasUnstable && this.unstable)
                    return state.setAndContinue(RawAnimation.begin().thenPlay("unstable.to_stable"));
                else if (!this.wasStable && this.wasUnstable && !this.unstable)
                    return state.setAndContinue(RawAnimation.begin().thenPlay("stable.to_unstable"));
                else {
                    LOGGER.warn("no object states were met! playing default unstable idle! (this should not happen)");
                    return state.setAndContinue(RawAnimation.begin().thenLoop("unstable.idle"));
                }
            }).setCustomInstructionKeyframeHandler(event -> {
                String instruction = event.getKeyframeData().getInstructions();
                if (instruction.equals("setStable"))
                    this.unstable = false;
                else if (instruction.equals("setUnstable"))
                    this.unstable = true;
            })
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
