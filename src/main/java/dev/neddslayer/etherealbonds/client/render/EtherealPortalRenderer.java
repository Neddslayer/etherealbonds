package dev.neddslayer.etherealbonds.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.*;
import dev.neddslayer.etherealbonds.EtherealBonds;
import dev.neddslayer.etherealbonds.client.model.EtherealPortalModel;
import dev.neddslayer.etherealbonds.entity.EtherealPortalEntity;
import dev.neddslayer.etherealbonds.entity.EtherealPortalGeometry;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLX;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import static dev.neddslayer.etherealbonds.EtherealBonds.LOGGER;

import java.io.IOException;

public class EtherealPortalRenderer extends GeoEntityRenderer<EtherealPortalEntity> {

    public EtherealPortalRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new EtherealPortalModel());
    }

    @Override
    public void preRender(MatrixStack poseStack, EtherealPortalEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        packedLight = 99999999;

        if (animatable.age > 0.0F) {
            GlStateManager._disableCull();

            GlStateManager._blendFunc(2, 6);
            GlStateManager._blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value);

            GlStateManager._enableCull();
        }

        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(EtherealPortalEntity entityLivingBaseIn) {
        return 0.0F;
    }

}
