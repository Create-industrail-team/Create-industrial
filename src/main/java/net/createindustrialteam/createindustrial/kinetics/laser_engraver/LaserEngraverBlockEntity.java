package net.createindustrialteam.createindustrial.kinetics.laser_engraver;

import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.createindustrialteam.createindustrial.kinetics.laser_engraver.EngravingBehaviour.*;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class LaserEngraverBlockEntity extends KineticBlockEntity implements EngraverBehaviourSpecifics {
    EngravingBehaviour engravingBehaviour;

    public LaserEngraverBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        engravingBehaviour = new EngravingBehaviour(this);
        behaviours.add(engravingBehaviour);
    }

    public EngravingBehaviour getEngravingBehaviour() {
        return engravingBehaviour;
    }

    @Override
    public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate) {
        return false;
    }

    @Override
    public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate) {
        return false;
    }

    @Override
    public boolean canProcessInBulk() {
        return false;
    }

    @Override
    public void onPressingCompleted() {

    }

    @Override
    public int getParticleAmount() {
        return 0;
    }

    @Override
    public float getKineticSpeed() {
        return 0;
    }
}
