package dev.neddslayer.etherealbonds.client.render;

import dev.neddslayer.etherealbonds.client.model.WaspModel;
import dev.neddslayer.etherealbonds.entity.WaspEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WaspRenderer extends GeoEntityRenderer<WaspEntity> {
    public WaspRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new WaspModel());
    }
}
