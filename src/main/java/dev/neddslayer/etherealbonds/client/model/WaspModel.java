package dev.neddslayer.etherealbonds.client.model;

import dev.neddslayer.etherealbonds.entity.WaspEntity;
import dev.neddslayer.etherealbonds.util.helper.IdentifierHelper;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class WaspModel extends GeoModel<WaspEntity> {
    @Override
    public Identifier getModelResource(WaspEntity animatable) {
        return IdentifierHelper.geo("wasp");
    }

    @Override
    public Identifier getTextureResource(WaspEntity animatable) {
        return IdentifierHelper.textureEntity("wasp");
    }

    @Override
    public Identifier getAnimationResource(WaspEntity animatable) {
        return IdentifierHelper.animation("wasp");
    }
}
