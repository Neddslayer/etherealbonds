package dev.neddslayer.etherealbonds;

import dev.neddslayer.etherealbonds.init.EtherealBondsEntityRegistry;
import dev.neddslayer.etherealbonds.init.EtherealBondsItemRegistry;
import dev.neddslayer.etherealbonds.init.EtherealBondsWorldRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class EtherealBonds implements ModInitializer {

    public static final String MODID = "etherealbonds";
    public static final Logger LOGGER = LogManager.getLogger("Ethereal Bonds");

    @Override
    public void onInitialize(ModContainer mod) {
        EtherealBondsItemRegistry.init();
        EtherealBondsEntityRegistry.init();
        EtherealBondsWorldRegistry.init();
    }
}
