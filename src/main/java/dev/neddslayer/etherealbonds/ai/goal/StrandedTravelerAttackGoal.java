package dev.neddslayer.etherealbonds.ai.goal;

import dev.neddslayer.etherealbonds.entity.StrandedTravelerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;

public class StrandedTravelerAttackGoal extends Goal {
    private final StrandedTravelerEntity entity;
    private double speed;
    private int attackTime = -1;
    private int statecheck;


    public StrandedTravelerAttackGoal(StrandedTravelerEntity entity, double speed, int state) {
        this.speed = speed;
        this.entity = entity;
        this.statecheck = state;
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
        this.entity.setAttackingState(0);
        this.entity.setHasTarget(false);
        this.attackTime = -1;
    }

    public void tick() {
        LivingEntity livingentity = this.entity.getTarget();
        if (livingentity != null) {
            boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
            this.attackTime++;
            this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
            final Box aabb2 = new Box(this.entity.getBlockPos()).expand(2D);
            if (inLineOfSight) {
                this.entity.getNavigation().startMovingTo(livingentity, this.speed);
                this.entity.setHasTarget(true);
                if (this.attackTime == 1) {
                    this.entity.setAttackingState(statecheck);
                }
                if (this.attackTime == 4) {
                    double d = this.entity.m_bvsfgiaw(livingentity);
                    this.entity.getCommandSenderWorld().getOtherEntities(this.entity, aabb2).forEach(e -> {
                        if ((e instanceof LivingEntity)) {
                            this.attack(livingentity, d);
                        }
                    });
                }
                if (this.attackTime >= 8) {
                    this.attackTime = -5;
                    this.entity.setAttackingState(0);
                }
            }
        }
    }
    protected void attack(LivingEntity target, double squaredDistance) {
        double d = this.getSquaredMaxAttackDistance(target);
        if (squaredDistance <= d) {
            this.entity.swingHand(Hand.MAIN_HAND);
            this.entity.tryAttack(target);
        }

    }


    protected double getSquaredMaxAttackDistance(LivingEntity entity) {
        return (double)(this.entity.getWidth() * 3.0F * this.entity.getWidth() * 3.0F + entity.getWidth());
    }
}
