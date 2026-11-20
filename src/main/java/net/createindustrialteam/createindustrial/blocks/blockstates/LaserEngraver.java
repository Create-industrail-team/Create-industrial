package net.createindustrialteam.createindustrial.blocks.blockstates;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.createindustrialteam.createindustrial.blocks.ModBlockEntities;
import net.createindustrialteam.createindustrial.kinetics.laser_engraver.LaserEngraverBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LaserEngraver extends HorizontalKineticBlock implements IBE<LaserEngraverBlockEntity> {

    public LaserEngraver(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext
                && ((EntityCollisionContext) context).getEntity() instanceof Player)
            return AllShapes.CASING_14PX.get(Direction.DOWN);

        return AllShapes.MECHANICAL_PROCESSOR_SHAPE;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING)
                .getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(HORIZONTAL_FACING)
                .getAxis();
    }

    @Override
    public Class<LaserEngraverBlockEntity> getBlockEntityClass() {
        return LaserEngraverBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends LaserEngraverBlockEntity> getBlockEntityType() {
        return ModBlockEntities.LASER_ENGRAVER_BLOCK.get();
    }
}
