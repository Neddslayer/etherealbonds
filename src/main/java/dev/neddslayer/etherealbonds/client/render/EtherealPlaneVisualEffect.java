package dev.neddslayer.etherealbonds.client.render;

import dev.neddslayer.etherealbonds.EtherealBonds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionVisualEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;


public class EtherealPlaneVisualEffect extends DimensionVisualEffects {

    public static Identifier PLANE_ID = new Identifier(EtherealBonds.MODID, "ethereal_plane");

    public EtherealPlaneVisualEffect() {
        super(Float.NaN, false, DimensionVisualEffects.SkyType.NONE, false, true);
    }

    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color;
    }

    public float @Nullable [] getFogColorOverride(float skyAngle, float tickDelta) {
        return null;
    }

    public boolean useThickFog(int camX, int camY) {
        return true;
    }

}
