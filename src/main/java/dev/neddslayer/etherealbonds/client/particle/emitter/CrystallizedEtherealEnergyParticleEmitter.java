package dev.neddslayer.etherealbonds.client.particle.emitter;

import com.sammy.lodestone.setup.LodestoneScreenParticles;
import com.sammy.lodestone.systems.rendering.particle.ParticleBuilders;
import com.sammy.lodestone.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.lodestone.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import java.awt.*;

import static net.minecraft.util.math.MathHelper.nextFloat;

public class CrystallizedEtherealEnergyParticleEmitter implements ItemParticleEmitter {
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) {
            return;
        }
        Color color = new Color(167, 19, 255);
        Color endColor = new Color(150, 0, 239);
        ParticleBuilders.create(LodestoneScreenParticles.TWINKLE)
            .setAlpha(0.07f, 0f)
            .setLifetime(10 + client.world.random.nextInt(10))
            .setScale(0.4f + client.world.random.nextFloat(), 0)
            .setColor(color, endColor)
            .setColorCoefficient(2f)
            .randomOffset(0.05f)
            .randomMotion(0.05f, 0.05f)
            .overrideRenderOrder(renderOrder)
            .centerOnStack(stack)
            .repeat(x, y, 1);

        ParticleBuilders.create(LodestoneScreenParticles.WISP)
            .setAlpha(0.01f, 0f)
            .setLifetime(20 + client.world.random.nextInt(8))
            .setSpin(nextFloat(client.world.random, 0.2f, 0.4f))
            .setScale(0.6f + client.world.random.nextFloat() * 0.4f, 0)
            .setColor(color, endColor)
            .setColorCoefficient(1.25f)
            .randomOffset(0.1f)
            .randomMotion(0.4f, 0.4f)
            .overrideRenderOrder(renderOrder)
            .centerOnStack(stack)
            .repeat(x, y, 1)
            .setLifetime(10 + client.world.random.nextInt(2))
            .setSpin(nextFloat(client.world.random, 0.05f, 0.1f))
            .setScale(0.8f + client.world.random.nextFloat() * 0.4f, 0f)
            .randomMotion(0.01f, 0.01f)
            .repeat(x, y, 1);
    }
}
