package dev.neddslayer.etherealbonds.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoWaterTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.checkerframework.checker.units.qual.A;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;

public class WaspEntity extends HostileEntity implements GeoEntity, Flutterer {

    public static final TrackedData<Integer> STATE = DataTracker.registerData(WaspEntity.class,
        TrackedDataHandlerRegistry.INTEGER);

    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public WaspEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createWaspAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6000000238418579)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new WaspAttackGoal(this, 1.399999976158142, true));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(STATE, 0);
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    void startMovingTo(BlockPos pos) {
        Vec3d vec3d = Vec3d.ofBottomCenter(pos);
        int i = 0;
        BlockPos blockPos = this.getBlockPos();
        int j = (int)vec3d.y - blockPos.getY();
        if (j > 2) {
            i = 4;
        } else if (j < -2) {
            i = -4;
        }

        int k = 6;
        int l = 8;
        int m = blockPos.getManhattanDistance(pos);
        if (m < 15) {
            k = m / 2;
            l = m / 2;
        }

        Vec3d vec3d2 = NoWaterTargeting.find(this, k, l, i, vec3d, 0.3141592741012573);
        if (vec3d2 != null) {
            this.navigation.setRangeMultiplier(0.5F);
            this.navigation.startMovingTo(vec3d2.x, vec3d2.y, vec3d2.z, 1.0);
        }
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public void setState(int state) {
        this.dataTracker.set(STATE, state);
    }

    public int getState() {
        return this.dataTracker.get(STATE);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, 5, state -> state.setAndContinue(RawAnimation.begin().thenLoop("misc.idle"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    class WaspAttackGoal extends Goal {
        protected final WaspEntity entity;
        private final double speed;
        private final boolean pauseWhenMobIdle;
        private int attackTime = -1;

        public WaspAttackGoal(WaspEntity entity, double speed, boolean pauseWhenMobIdle) {
            this.entity = entity;
            this.speed = speed;
            this.pauseWhenMobIdle = pauseWhenMobIdle;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            return this.entity.getTarget() != null;
        }

        public boolean shouldContinue() {
            return this.canStart();
        }

        public void start() {
            super.start();
            this.entity.setAttacking(true);
        }

        public void stop() {
            super.stop();
            this.entity.setAttacking(false);
            this.entity.setState(0);
            this.attackTime = -1;
        }

        public void tick() {
            LivingEntity livingentity = this.entity.getTarget();
            if (livingentity != null) {
                boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
                this.attackTime++;
                this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
                final Box aabb2 = new Box(this.entity.getBlockPos()).expand(1.25D);
                if (inLineOfSight) {
                    this.entity.getNavigation().startMovingTo(livingentity, this.speed);
                    if (this.attackTime == 1) {
                        if (this.entity.m_bvsfgiaw(livingentity) <= this.getSquaredMaxAttackDistance(livingentity)){
                            this.entity.setState(1);
                        }
                    }
                    if (this.attackTime == 4) {
                        this.entity.getCommandSenderWorld().getOtherEntities(this.entity, aabb2).forEach(e -> {
                            if ((e instanceof LivingEntity l)) {
                                if (this.entity.m_bvsfgiaw(l) <= this.getSquaredMaxAttackDistance(l)){
                                    this.entity.tryAttack(livingentity);
                                    livingentity.timeUntilRegen = 0;
                                }
                            }
                        });
                    }
                    if (this.attackTime >= 8) {
                        this.attackTime = -5;
                        this.entity.setState(0);
                    }
                }
            }
        }
        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (double)(this.entity.getWidth() * 2.0F * this.entity.getWidth() * 2.0F + entity.getWidth());
        }
    }

}
