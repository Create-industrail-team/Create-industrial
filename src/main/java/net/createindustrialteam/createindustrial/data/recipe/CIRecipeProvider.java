package net.createindustrialteam.createindustrial.data.recipe;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import net.createindustrialteam.createindustrial.CIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CIRecipeProvider extends RecipeProvider {
    static final List<ProcessingRecipeGen<?, ?, ?>> GENERATORS = new ArrayList<>();

    public CIRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
    }

    public static void registerAllProcessing(DataGenerator gen, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        GENERATORS.add(new CIEngravingRecipeGen(output, registries));

        gen.addProvider(true, new DataProvider() {
            @Override
            public String getName() {
                return "Create Industrial's Processing Recipes";
            }

            @Override
            public CompletableFuture<?> run(CachedOutput dc) {
                return CompletableFuture.allOf(GENERATORS.stream()
                        .map(gen -> gen.run(dc))
                        .toArray(CompletableFuture[]::new));
            }
        });
    }

    protected static class I {
        static ItemLike siliconWafer() {
            return CIItems.SILICON_WAFER.get();
        }

        static ItemLike printedSiliconWafer() {
            return CIItems.PRINTED_SILICON_WAFER.get();
        }
    }
}
