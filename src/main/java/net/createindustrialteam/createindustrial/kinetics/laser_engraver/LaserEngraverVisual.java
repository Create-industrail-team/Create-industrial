package net.createindustrialteam.createindustrial.kinetics.laser_engraver;

import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlock;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createindustrialteam.createindustrial.ModPartialModels;
import net.createindustrialteam.createindustrial.blocks.blockstates.LaserEngraver;
import net.createmod.catnip.math.AngleHelper;
import org.joml.Quaternionf;

import java.util.function.Consumer;

public class LaserEngraverVisual extends ShaftVisual<LaserEngraverBlockEntity> implements SimpleDynamicVisual {

    private final OrientedInstance engraverLaser;

    public LaserEngraverVisual(VisualizationContext context, LaserEngraverBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        engraverLaser = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(ModPartialModels.LASER_ENGRAVER_LASER))
                .createInstance();

        Quaternionf q = Axis.YP
                .rotationDegrees(AngleHelper.horizontalAngle(blockState.getValue(LaserEngraver.HORIZONTAL_FACING)));

        engraverLaser.rotation(q);

        engraverLaser.position(getVisualPosition()).setChanged();
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {

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
