package dev.neddslayer.etherealbonds.client.model;

import dev.neddslayer.etherealbonds.entity.EtherealPortalEntity;
import dev.neddslayer.etherealbonds.util.helper.IdentifierHelper;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class EtherealPortalModel extends GeoModel<EtherealPortalEntity> {
    @Override
    public Identifier getModelResource(EtherealPortalEntity animatable) {
        return IdentifierHelper.geo("ethereal_portal");
    }

    @Override
    public Identifier getTextureResource(EtherealPortalEntity animatable) {
        return IdentifierHelper.textureEntity("ethereal_portal");
    }

    @Override
    public Identifier getAnimationResource(EtherealPortalEntity animatable) {
        return IdentifierHelper.animation("ethereal_portal");
    }
}
