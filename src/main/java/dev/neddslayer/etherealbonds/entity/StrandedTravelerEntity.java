package dev.neddslayer.etherealbonds.entity;

import dev.neddslayer.etherealbonds.EtherealBonds;
import dev.neddslayer.etherealbonds.ai.goal.StrandedTravelerAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class StrandedTravelerEntity extends HostileEntity implements GeoEntity {

    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final TrackedData<Integer> STATE = DataTracker.registerData(StrandedTravelerEntity.class,
        TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> HAS_TARGET = DataTracker.registerData(StrandedTravelerEntity.class,
        TrackedDataHandlerRegistry.BOOLEAN);

    private final int skinVariant;

    public StrandedTravelerEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.skinVariant = this.getRandom().nextInt(8);
    }

    public int getSkinVariant() {
        return this.skinVariant;
    }

    protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(2, new StrandedTravelerAttackGoal(this, 0.4, 1));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 0.175));
        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge(ZombifiedPiglinEntity.class));
        this.targetSelector.add(2, new TargetGoal<>(this, PlayerEntity.class, true));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(STATE, 0);
        this.getDataTracker().startTracking(HAS_TARGET, false);
    }


    public int getAttackingState() {
        return this.dataTracker.get(STATE);
    }

    public void setAttackingState(int time) {
        this.dataTracker.set(STATE, time);
    }

    public boolean getHasTarget() {
        return this.dataTracker.get(HAS_TARGET);
    }

    public void setHasTarget(boolean time) {
    this.dataTracker.set(HAS_TARGET, time);
    }

    public static DefaultAttributeContainer.Builder createHostileAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    private <E extends GeoAnimatable> PlayState controllerPredicate(@NotNull AnimationState<E> state) {
        if (this.prevX == this.getX() && this.prevY == this.getY() && this.prevZ == this.getZ() && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
            state.getController().setAnimation(RawAnimation.begin().then("misc.idle2", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.dead || this.getHealth() < 0.01 || this.isDead()) {
            state.getController().setAnimation(RawAnimation.begin().then("misc.death", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        } else {
            if (this.getHasTarget()) {
                state.getController().setAnimation(RawAnimation.begin().then("walk.target", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            } else {
                state.getController().setAnimation(RawAnimation.begin().then("walk.wander", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
            new AnimationController<>(this, "controller", 5, this::controllerPredicate)
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
