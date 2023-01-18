package dev.neddslayer.etherealbonds.client.model;

import dev.neddslayer.etherealbonds.entity.StrandedTravelerEntity;
import dev.neddslayer.etherealbonds.util.helper.IdentifierHelper;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class StrandedTravelerModel extends GeoModel<StrandedTravelerEntity> {
    @Override
    public Identifier getModelResource(StrandedTravelerEntity animatable) {
        return IdentifierHelper.geo("stranded_traveler");
    }

    @Override
    public Identifier getTextureResource(StrandedTravelerEntity animatable) {
        return IdentifierHelper.textureEntity("stranded_traveler_" + animatable.getSkinVariant());
    }

    @Override
    public Identifier getAnimationResource(StrandedTravelerEntity animatable) {
        return IdentifierHelper.animation("stranded_traveler");
    }
}
