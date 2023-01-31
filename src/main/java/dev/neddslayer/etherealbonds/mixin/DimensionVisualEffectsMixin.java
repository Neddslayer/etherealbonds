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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionVisualEffects.class)
public class DimensionVisualEffectsMixin {


    @Shadow
    @Final
    private static Object2ObjectMap<Identifier, DimensionVisualEffects> BY_IDENTIFIER;
    private static final Object2ObjectMap<Identifier, DimensionVisualEffects> BY_IDENTIFIER_CUSTOM =
        (Object2ObjectMap<Identifier, DimensionVisualEffects>) Util.make(new Object2ObjectArrayMap(), (map) -> {
        DimensionVisualEffects.Overworld overworld = new DimensionVisualEffects.Overworld();
        map.defaultReturnValue(overworld);
        map.put(DimensionTypes.OVERWORLD_ID, overworld);
        map.put(DimensionTypes.THE_NETHER_ID, new DimensionVisualEffects.Nether());
        map.put(DimensionTypes.THE_END_ID, new DimensionVisualEffects.End());
        map.put(EtherealPlaneVisualEffect.PLANE_ID, new EtherealPlaneVisualEffect());
    });

    //@Overwrite
    //public static DimensionVisualEffects byDimensionType(DimensionType dimensionType) {
    //    return (DimensionVisualEffects)BY_IDENTIFIER.get(dimensionType.effectsLocation());
    //}

    //byDimensionType(Lnet/minecraft/world/DimensionType)Lnet/minecraft/client/render/DimensionVisualEffects

    @Inject(at = @At("RETURN"), method = "byDimensionType", cancellable = true)
    private static void overrideType(DimensionType dimensionType, CallbackInfoReturnable<DimensionVisualEffects> ci) {
        if (!BY_IDENTIFIER.containsKey(dimensionType.effectsLocation())) {
            ci.setReturnValue(BY_IDENTIFIER_CUSTOM.get(dimensionType.effectsLocation()));
        }
    }

}
