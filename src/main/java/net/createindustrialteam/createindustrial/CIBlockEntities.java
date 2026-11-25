package net.createindustrialteam.createindustrial;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.createindustrialteam.createindustrial.blocks.laser_engraver.LaserEngraverBlockEntity;
import net.createindustrialteam.createindustrial.blocks.laser_engraver.LaserEngraverRenderer;
import net.createindustrialteam.createindustrial.blocks.laser_engraver.LaserEngraverVisual;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CIBlockEntities {
    public static final CreateRegistrate REGISTRATE = CreateIndustrial.registrate();

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateIndustrial.MODID);

    public static final BlockEntityEntry<LaserEngraverBlockEntity> LASER_ENGRAVER_BLOCK = REGISTRATE
            .blockEntity("laser_engraver", LaserEngraverBlockEntity::new)
            .visual(() -> LaserEngraverVisual::new)
            .validBlocks(CIBlocks.LASER_ENGRAVER)
            .renderer(() -> LaserEngraverRenderer::new)
            .register();


    public static void register(IEventBus modEventBus){
    }
}
