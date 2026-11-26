package net.createindustrialteam.createindustrial.blocks.laser_engraver;

import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.content.processing.sequenced.IAssemblyRecipe;
import com.simibubi.create.foundation.utility.CreateLang;
import net.createindustrialteam.createindustrial.CIBlocks;
import net.createindustrialteam.createindustrial.CIRecipeTypes;
import net.createindustrialteam.createindustrial.compat.jei.EngravingAssemblySubCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class EngravingRecipe extends StandardProcessingRecipe<SingleRecipeInput> implements IAssemblyRecipe {

    public EngravingRecipe(ProcessingRecipeParams params) {
        super(CIRecipeTypes.ENGRAVING, params);
    }

    @Override
    public boolean matches(SingleRecipeInput inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return ingredients.get(0)
                .test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 2;
    }

    @Override
    public void addAssemblyIngredients(List<Ingredient> list) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Component getDescriptionForAssembly() {
        return CreateLang.translateDirect("recipe.assembly.engraving");
    }

    @Override
    public void addRequiredMachines(Set<ItemLike> list) {
        list.add(CIBlocks.LASER_ENGRAVER.get());
    }

    @Override
    public Supplier<Supplier<SequencedAssemblySubCategory>> getJEISubCategory() {
        return () -> EngravingAssemblySubCategory::new;
    }
}
