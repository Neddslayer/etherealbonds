package dev.neddslayer.etherealbonds;

import com.sammy.lodestone.handlers.ScreenParticleHandler;
import dev.neddslayer.etherealbonds.client.particle.EtherealBondsAmbientParticle;
import dev.neddslayer.etherealbonds.client.render.EtherealPortalRenderer;
import dev.neddslayer.etherealbonds.client.render.StrandedTravelerRenderer;
import dev.neddslayer.etherealbonds.client.render.WaspRenderer;
import dev.neddslayer.etherealbonds.init.EtherealBondsEntityRegistry;
import dev.neddslayer.etherealbonds.init.EtherealBondsParticleEmitterRegistry;
import dev.neddslayer.etherealbonds.init.EtherealBondsParticleRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import static dev.neddslayer.etherealbonds.init.EtherealBondsParticleEmitterRegistry.PARTICLE_EMITTER;

public class EtherealBondsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        EntityRendererRegistry.register(EtherealBondsEntityRegistry.ETHEREAL_PORTAL, EtherealPortalRenderer::new);
        EntityRendererRegistry.register(EtherealBondsEntityRegistry.STRANDED_TRAVELER, StrandedTravelerRenderer::new);
        EntityRendererRegistry.register(EtherealBondsEntityRegistry.WASP, WaspRenderer::new);
        EtherealBondsParticleEmitterRegistry.init();
        PARTICLE_EMITTER.forEach(ScreenParticleHandler::registerItemParticleEmitter);
        ParticleFactoryRegistry.getInstance().register(EtherealBondsParticleRegistry.ETHEREAL_PLANE_AMBIENT_PARTICLE, EtherealBondsAmbientParticle.Factory::new);
    }
}
