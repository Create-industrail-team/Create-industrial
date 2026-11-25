package net.createindustrialteam.createindustrial.blocks.laser_engraver;

import com.simibubi.create.api.packager.InventoryIdentifier;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import net.createindustrialteam.createindustrial.CIRecipeTypes;
import net.createindustrialteam.createindustrial.blocks.laser_engraver.EngravingBehaviour.*;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class LaserEngraverBlockEntity extends KineticBlockEntity implements EngraverBehaviourSpecifics {

    public EngravingBehaviour engravingBehaviour;

    public LaserEngraverBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).expandTowards(0, -1.125, 0)
                .expandTowards(0, 1, 0);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        engravingBehaviour = new EngravingBehaviour(this);
        behaviours.add(engravingBehaviour);
    }

    // TODO: at some point add an advancement to be awarded by this function, maybe some other function?
    public void onItemPressed(ItemStack result) {

    }

    public EngravingBehaviour getEngravingBehaviour() {
        return engravingBehaviour;
    }

    @Override
    public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate) {
        Optional<RecipeHolder<EngravingRecipe>> recipe = getRecipe(input.stack);
        if (!recipe.isPresent())
            return false;
        if (simulate)
            return true;

        List<ItemStack> outputs = RecipeApplier.applyRecipeOn(level,
                input.stack.copyWithCount(1), recipe.get().value(), true);

        for (ItemStack created : outputs) {
            if (!created.isEmpty()) {
                onItemPressed(created);
                break;
            }
        }

        outputList.addAll(outputs);
        return true;
    }

    @Override
    public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate) {
        ItemStack item = itemEntity.getItem();
        Optional<RecipeHolder<EngravingRecipe>> recipe = getRecipe(item);
        if (!recipe.isPresent())
            return false;
        if (simulate)
            return true;

        ItemStack itemCreated = ItemStack.EMPTY;
        if (item.getCount() == 1) {
            RecipeApplier.applyRecipeOn(itemEntity, recipe.get().value(), true);
            itemCreated = itemEntity.getItem()
                    .copy();
        } else {
            for (ItemStack result : RecipeApplier.applyRecipeOn(level, item.copyWithCount(1),
                    recipe.get().value(), true)) {
                if (itemCreated.isEmpty())
                    itemCreated = result.copy();
                ItemEntity created =
                        new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
                created.setDefaultPickUpDelay();
                created.setDeltaMovement(VecHelper.offsetRandomly(Vec3.ZERO, level.random, 0.05f));
                level.addFreshEntity(created);
            }
            item.shrink(1);
        }

        if (!itemCreated.isEmpty())
            onItemPressed(itemCreated);
        return true;
    }

    @Override
    public void onEngravingCompleted() {

    }

    public Optional<RecipeHolder<EngravingRecipe>> getRecipe(ItemStack item) {
        Optional<RecipeHolder<EngravingRecipe>> assemblyRecipe =
                SequencedAssemblyRecipe.getRecipe(level, item, CIRecipeTypes.ENGRAVING.getType(), EngravingRecipe.class);
        if (assemblyRecipe.isPresent())
            return assemblyRecipe;

        return CIRecipeTypes.ENGRAVING.find(new SingleRecipeInput(item), level);
    }

    @Override
    public int getParticleAmount() {
        return 0;
    }

    @Override
    public float getKineticSpeed() {
        return getSpeed();
    }
}
