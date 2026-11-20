package net.createindustrialteam.createindustrial.blocks;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.createindustrialteam.createindustrial.CreateIndustrial;
import net.createindustrialteam.createindustrial.kinetics.laser_engraver.LaserEngraverBlockEntity;
import net.createindustrialteam.createindustrial.kinetics.laser_engraver.LaserEngraverRenderer;
import net.createindustrialteam.createindustrial.kinetics.laser_engraver.LaserEngraverVisual;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateIndustrial.MODID);

    /*public static final Supplier<BlockEntityType<LaserEngraverBlockEntity>> LASER_ENGRAVER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "laser_engraver_entity",
            () -> BlockEntityType.Builder.of(
                    LaserEngraverBlockEntity::new,
                    ModBlocks.LASER_ENGRAVER.get()
            )
            .build(null)
    );*/

    public static final BlockEntityEntry<LaserEngraverBlockEntity> LASER_ENGRAVER_BLOCK = CreateIndustrial.REGISTRATE
            .blockEntity("laser_engraver", LaserEngraverBlockEntity::new)
            .visual(() -> LaserEngraverVisual::new)
            .validBlocks(ModBlocks.LASER_ENGRAVER)
            .renderer(() -> LaserEngraverRenderer::new)
            .register();

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        /*event.registerBlockEntityRenderer(
                // The block entity type to register the renderer for
                ModBlockEntities.LASER_ENGRAVER_BLOCK_ENTITY.get(),
                // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer
                LaserEngraverRenderer::new);*/
    }


    public static void register(IEventBus modEventBus){
        //BLOCK_ENTITY_TYPES.register(modEventBus);
        //modEventBus.register(ModBlockEntities.class);
    }
}
