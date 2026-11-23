package net.createindustrialteam.createindustrial.kinetics.laser_engraver;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import dev.engine_room.flywheel.api.backend.BackendManager;
import dev.engine_room.flywheel.api.visualization.VisualizationLevel;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createindustrialteam.createindustrial.ModPartialModels;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class LaserEngraverRenderer extends KineticBlockEntityRenderer<LaserEngraverBlockEntity> {
    // Add the constructor parameter for the lambda below. You may also use it to get some context
    // to be stored in local fields, such as the entity renderer dispatcher, if needed.
    public LaserEngraverRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRenderOffScreen(LaserEngraverBlockEntity be) {
        return true;
    }

    // This method is called every frame in order to render the block entity. Parameters are:
    // - blockEntity:   The block entity instance being rendered. Uses the generic type passed to the super interface.
    // - partialTick:   The amount of time, in fractions of a tick (0.0 to 1.0), that has passed since the last tick.
    // - poseStack:     The pose stack to render to.
    // - bufferSource:  The buffer source to get vertex buffers from.
    // - packedLight:   The light value of the block entity.
    // - packedOverlay: The current overlay value of the block entity, usually OverlayTexture.NO_OVERLAY.
    @Override
    protected void renderSafe(LaserEngraverBlockEntity blockEntity, float partialTick, PoseStack stack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        // Do the rendering here.
        //super.renderSafe(blockEntity, partialTick, stack, bufferSource, packedLight, packedOverlay);

        /*VertexConsumer consumer = bufferSource.getBuffer(RenderType.LIGHTNING);
        int renderedLaserAlpha = blockEntity.getEngravingBehaviour().getRenderedLaserAlpha(partialTick);
        SuperByteBuffer block = CachedBuffers.block(Blocks.BROWN_GLAZED_TERRACOTTA.defaultBlockState());
        float size = 0.25f * 0.12f;
        float scalar = (1 - size * 1.5f) * 0.5f;
        Quaternionf rotate = new Quaternionf(0.924, 0, 0.383, 0);

        block.color(255, 255, 255, 255)
                .translate(scalar, -14/16f, scalar)
                .scale(size, 1, size)
                .rotateCentered((float)Math.PI * 0.25f, Direction.Axis.Y)
                .renderInto(stack, consumer);*/

        LevelAccessor level = blockEntity.getLevel();
        if (VisualizationManager.supportsVisualization(blockEntity.getLevel()))
            return;

        BlockState blockState = blockEntity.getBlockState();

        SuperByteBuffer laserRender = CachedBuffers.partialFacing(ModPartialModels.LASER_ENGRAVER_LASER, blockState,
                blockState.getValue(HORIZONTAL_FACING));
        laserRender.translate(0, 0, 0)
                .light(packedLight)
                .renderInto(stack, bufferSource.getBuffer(RenderType.solid()));
    }

    @Override
    protected BlockState getRenderedBlockState(LaserEngraverBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }
}
