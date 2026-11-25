package net.createindustrialteam.createindustrial.compat.jei;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.createindustrialteam.createindustrial.CIBlocks;
import net.createindustrialteam.createindustrial.CIRecipeTypes;
import net.createindustrialteam.createindustrial.CreateIndustrial;
import net.createindustrialteam.createindustrial.blocks.laser_engraver.EngravingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class CreateIndustrialJEI implements IModPlugin {

    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CreateIndustrial.MODID, "jei_plugin");

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ID;
    }

    public IIngredientManager ingredientManager;
    static final List<CreateRecipeCategory<?>> ALL = new ArrayList<>();

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        ALL.clear();

        builder(EngravingRecipe.class)
                .addTypedRecipes(CIRecipeTypes.ENGRAVING)
                .catalyst(CIBlocks.LASER_ENGRAVER::get)
                .itemIcon(CIBlocks.LASER_ENGRAVER.get())
                .emptyBackground(177, 53)
                .build("engraving", EngravingCategory::new);

        ALL.forEach(registration::addRecipeCategories);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();
        ALL.forEach(c -> c.registerRecipes(registration));
    }

    private <T extends Recipe<?>> CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
        return new CategoryBuilder<>(recipeClass);
    }

    private static class CategoryBuilder<T extends Recipe<?>> extends CreateRecipeCategory.Builder<T> {
        public CategoryBuilder(Class<? extends T> recipeClass) {
            super(recipeClass);
        }

        @Override
        public CreateRecipeCategory<T> build(ResourceLocation id, CreateRecipeCategory.Factory<T> factory) {
            CreateRecipeCategory<T> category = super.build(id, factory);
            ALL.add(category);
            return category;
        }

        @Override
        public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
            return build(CreateIndustrial.asResource(name), factory);
        }
    }
}
