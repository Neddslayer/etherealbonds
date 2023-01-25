package dev.neddslayer.etherealbonds.mixin;

import dev.neddslayer.etherealbonds.client.render.EtherealPlaneVisualEffect;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.render.DimensionVisualEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DimensionVisualEffects.class)
public class DimensionVisualEffectsMixin {


    private static final Object2ObjectMap<Identifier, DimensionVisualEffects> BY_IDENTIFIER =
        (Object2ObjectMap<Identifier, DimensionVisualEffects>) Util.make(new Object2ObjectArrayMap(), (map) -> {
        DimensionVisualEffects.Overworld overworld = new DimensionVisualEffects.Overworld();
        map.defaultReturnValue(overworld);
        map.put(DimensionTypes.OVERWORLD_ID, overworld);
        map.put(DimensionTypes.THE_NETHER_ID, new DimensionVisualEffects.Nether());
        map.put(DimensionTypes.THE_END_ID, new DimensionVisualEffects.End());
        map.put(EtherealPlaneVisualEffect.PLANE_ID, new EtherealPlaneVisualEffect());
    });

    /**
     * @author me
     * @reason because javadocs needed it
     */
    @Overwrite
    public static DimensionVisualEffects byDimensionType(DimensionType dimensionType) {
        return (DimensionVisualEffects)BY_IDENTIFIER.get(dimensionType.effectsLocation());
    }

}
