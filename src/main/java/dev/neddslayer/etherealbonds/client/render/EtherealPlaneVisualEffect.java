package dev.neddslayer.etherealbonds.client.render;

import dev.neddslayer.etherealbonds.EtherealBonds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionVisualEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;


public class EtherealPlaneVisualEffect extends DimensionVisualEffects {

    public static Identifier PLANE_ID = new Identifier(EtherealBonds.MODID, "ethereal_plane");

    public EtherealPlaneVisualEffect() {
        super(Float.NaN, true, DimensionVisualEffects.SkyType.NONE, false, false);
    }

    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color;
    }

    public boolean useThickFog(int camX, int camY) {
        return true;
    }

}
