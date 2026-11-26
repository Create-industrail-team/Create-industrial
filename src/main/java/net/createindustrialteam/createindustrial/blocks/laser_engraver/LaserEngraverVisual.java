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

    public LaserEngraverVisual(VisualizationContext context, LaserEngraverBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        engraverLaser = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(CIPartialModels.LASER_ENGRAVER_LASER))
                .createInstance();

        transformModels(partialTick);
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        transformModels(ctx.partialTick());
    }

    private void transformModels(float pt) {
        float renderedLaserScale = getRenderedLaserScale(pt);
        float renderedLaserWidth = getRenderedLaserWidth(pt);

        if (getRunning()) {
            engraverLaser.setIdentityTransform()
                    .translate(getVisualPosition())
                    .translate(1/2f, 0.125f, 1/2f)
                    .scale(renderedLaserWidth, renderedLaserScale, renderedLaserWidth)
                    .setChanged();
            engraverLaser.setVisible(true);
        } else {
            engraverLaser.setVisible(false);
        }
    }

    private float getRenderedLaserScale(float pt) {
        EngravingBehaviour engravingBehaviour = blockEntity.getEngravingBehaviour();
        return engravingBehaviour.mode.laserOffset;
    }

    private float getRenderedLaserWidth(float pt) {
        EngravingBehaviour engravingBehaviour = blockEntity.getEngravingBehaviour();
        return engravingBehaviour.getRenderedLaserWidth(pt);
    }

    private boolean getRunning() {
        EngravingBehaviour engravingBehaviour = blockEntity.getEngravingBehaviour();
        return engravingBehaviour.running;
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
