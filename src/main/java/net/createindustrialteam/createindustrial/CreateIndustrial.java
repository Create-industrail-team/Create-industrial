package net.createindustrialteam.createindustrial;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.createindustrialteam.createindustrial.blocks.ModBlockDataGen;
import net.createindustrialteam.createindustrial.blocks.ModBlockEntities;
import net.createindustrialteam.createindustrial.blocks.ModBlocks;
import net.createindustrialteam.createindustrial.items.ModCreativeModTabs;
import net.createindustrialteam.createindustrial.items.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import org.slf4j.Logger;

@Mod(CreateIndustrial.MODID)
public class CreateIndustrial {
    public static final String MODID = "createindustrial";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    public CreateIndustrial(IEventBus eventBus, ModContainer modContainer) {
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::addCreative);
        eventBus.register(ModDataGenerators.class);

        REGISTRATE.registerEventListeners(eventBus);

        ModBlocks.register(eventBus);
        ModItems.register(eventBus);
        ModCreativeModTabs.register(eventBus);
        ModBlockEntities.register(eventBus);


        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}