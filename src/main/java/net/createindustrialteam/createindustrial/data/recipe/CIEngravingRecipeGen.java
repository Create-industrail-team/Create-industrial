package net.createindustrialteam.createindustrial.data.recipe;

import net.createindustrialteam.createindustrial.data.recipe.CIRecipeProvider.I;
import net.createindustrialteam.createindustrial.CIItems;
import net.createindustrialteam.createindustrial.CreateIndustrial;
import net.createindustrialteam.createindustrial.data.recipe.gen.EngravingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public final class CIEngravingRecipeGen extends EngravingRecipeGen {

    GeneratedRecipe

            PRINTED_SILICON_WAFER = create("printed_silicon", b -> b.require(CIItems.PRINTED_SILICON_WAFER).output(I.printedSiliconWafer()));

    public CIEngravingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateIndustrial.MODID);
    }
}
