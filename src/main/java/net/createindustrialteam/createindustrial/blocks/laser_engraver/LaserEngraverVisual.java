package net.createindustrialteam.createindustrial.blocks.laser_engraver;

import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createindustrialteam.createindustrial.CIPartialModels;
import net.createmod.catnip.math.AngleHelper;
import org.joml.Quaternionf;

import java.util.function.Consumer;

public class LaserEngraverVisual extends ShaftVisual<LaserEngraverBlockEntity> implements SimpleDynamicVisual {

    // Issue: The laser model is *slightly* off centre, not important fix
    private final TransformedInstance engraverLaser;
    Quaternionf rotation;

    public LaserEngraverVisual(VisualizationContext context, LaserEngraverBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        engraverLaser = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(CIPartialModels.LASER_ENGRAVER_LASER))
                .createInstance();

        rotation = Axis.YP
                .rotationDegrees(AngleHelper.horizontalAngle(blockState.getValue(LaserEngraverBlock.HORIZONTAL_FACING)));

        transformModels(partialTick);
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        transformModels(ctx.partialTick());
    }

    private void transformModels(float pt) {
        float renderedLaserScale = getRenderedLaserScale(pt);
        int renderedLaserAlpha = getRenderedLaserAlpha(pt);

        engraverLaser.setIdentityTransform()
                .translate(getVisualPosition())
                .translate(0, 0.125f, 0)
                .scale(1, renderedLaserScale * renderedLaserAlpha / 255, 1)
                .rotateAround(rotation, 0.5f, 0.5f, 0.5f)
                .color(255, 255, 255, renderedLaserAlpha)
                .setChanged();
    }

    private float getRenderedLaserScale(float pt) {
        EngravingBehaviour engravingBehaviour = blockEntity.getEngravingBehaviour();
        return engravingBehaviour.mode.laserOffset;
    }

    private int getRenderedLaserAlpha(float pt) {
        EngravingBehaviour engravingBehaviour = blockEntity.getEngravingBehaviour();
        return engravingBehaviour.getRenderedLaserAlpha(pt);
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);
        relight(engraverLaser);
    }

    @Override
    protected void _delete() {
        super._delete();
        engraverLaser.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        consumer.accept(engraverLaser);
    }
}
