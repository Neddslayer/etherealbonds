package dev.neddslayer.etherealbonds.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.neddslayer.etherealbonds.client.model.StrandedTravelerModel;
import dev.neddslayer.etherealbonds.entity.EtherealPortalEntity;
import dev.neddslayer.etherealbonds.entity.StrandedTravelerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class StrandedTravelerRenderer extends GeoEntityRenderer<StrandedTravelerEntity> {
    public StrandedTravelerRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new StrandedTravelerModel());
    }

    @Override
    public void preRender(MatrixStack poseStack, StrandedTravelerEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.scale(0.95f,0.95f,0.95f);
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
