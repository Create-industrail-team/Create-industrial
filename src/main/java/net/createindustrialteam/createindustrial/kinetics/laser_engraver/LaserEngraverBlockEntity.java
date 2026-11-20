package net.createindustrialteam.createindustrial.kinetics.laser_engraver;

import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static net.createindustrialteam.createindustrial.blocks.ModBlockEntities.*;

public class LaserEngraverBlockEntity extends BasinOperatingBlockEntity {
    public LaserEngraverBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected boolean isRunning() {
        return false;
    }

    @Override
    protected void onBasinRemoved() {

    }

    @Override
    protected boolean matchStaticFilters(RecipeHolder<? extends Recipe<?>> recipe) {
        return false;
    }

    @Override
    protected Object getRecipeCacheKey() {
        return null;
    }
}
