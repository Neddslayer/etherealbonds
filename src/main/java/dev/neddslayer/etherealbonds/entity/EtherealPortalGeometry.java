package dev.neddslayer.etherealbonds.entity;

import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class EtherealPortalGeometry {

    private static final int POINT_COUNT = 12;
    private static final float DISPLACEMENT_SCALE = 0.4F;

    private final long seed;
    private final float width;
    private final float height;

    public EtherealPortalGeometry(long seed, float width, float height) {
        this.seed = seed;
        this.width = width;
        this.height = height;
    }

    public Vector2f[] computePath(float time) {
        Random random = new Random(this.seed);
        Vector2f[] ring = new Vector2f[POINT_COUNT];

        float idleSpeed = 0.08F;
        float idleIntensity = 0.1F * time;

        float displacementAnimation = Math.min(2.0F * time, 1.0F);

        float sizeX = (this.width / 2.0F) * time;
        float sizeY = (this.height / 2.0F) * 0.5F * (time + 1.0F);

        // Generates a circle around (0; 0), stretching it horizontally and displacing each vertex by a random amount

        float tau = (float) (Math.PI * 2.0F);
        for (int i = 0; i < POINT_COUNT; i++) {
            float angle = i * tau / POINT_COUNT;

            float idleAnimation = MathHelper.sin(time * idleSpeed + i * 10) * idleIntensity;
            float displacement = (random.nextFloat() * 2.0F - 1.0F) + idleAnimation;
            float scaledDisplacement = displacement * DISPLACEMENT_SCALE * displacementAnimation;

            float pointX = -MathHelper.sin(angle) * (sizeX + scaledDisplacement);
            float pointY = MathHelper.cos(angle) * (sizeY + scaledDisplacement);

            ring[i] = new Vector2f(pointX, pointY);
        }

        return ring;
    }

    public long getSeed() {
        return this.seed;
    }



}
