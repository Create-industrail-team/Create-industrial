package net.createindustrialteam.createindustrial.data.recipe.gen;

import com.simibubi.create.api.data.recipe.DatagenMod;
import com.simibubi.create.api.data.recipe.StandardProcessingRecipeGen;
import net.createindustrialteam.createindustrial.CIRecipeTypes;
import net.createindustrialteam.createindustrial.blocks.laser_engraver.EngravingRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class EngravingRecipeGen extends StandardProcessingRecipeGen<EngravingRecipe> {
    protected GeneratedRecipe moddedCompacting(DatagenMod mod, String input, String output) {
        return create("compat/" + mod.getId() + "/" + output, b -> b.require(mod, input)
                .output(mod, output)
                .whenModLoaded(mod.getId()));
    }

    protected GeneratedRecipe moddedPaths(DatagenMod mod, String... blocks) {
        for (String block : blocks) {
            moddedCompacting(mod, block, block + "_path");
        }
        return null;
    }

    protected EngravingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }

    @Override
    protected CIRecipeTypes getRecipeType() {
        return CIRecipeTypes.ENGRAVING;
    }
}
