package dev.neddslayer.etherealbonds.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.lodestone.setup.LodestoneParticles;
import com.sammy.lodestone.systems.rendering.particle.Easing;
import com.sammy.lodestone.systems.rendering.particle.ParticleBuilders;
import com.sammy.lodestone.systems.rendering.particle.type.LodestoneParticleType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class EtherealBondsAmbientParticle extends Particle {

    public EtherealBondsAmbientParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {

    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.NO_RENDER;
    }

    public void tick() {
        ParticleBuilders.create(LodestoneParticles.TWINKLE_PARTICLE)
            .setColor(128, 0, 128, 255, 230, 0, 230, 0)
            .setScale(0.2f, 1, 0.2f)
            .setScaleEasing(Easing.EXPO_OUT, Easing.QUAD_IN)
            .setAlpha(0.15f, 1, 0.15f)
            .setAlphaEasing(Easing.EXPO_OUT, Easing.QUAD_IN)
            .randomMotion(0.1)
            .enableNoClip()
            .spawn(this.world, this.x, this.y, this.z);
        this.markDead();
    }


    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new EtherealBondsAmbientParticle(clientWorld, d, e, f, g, h, i);
        }
    }
}
