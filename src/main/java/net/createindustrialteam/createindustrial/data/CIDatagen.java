package net.createindustrialteam.createindustrial.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import net.createindustrialteam.createindustrial.CreateIndustrial;
import net.createindustrialteam.createindustrial.data.recipe.CIRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class CIDatagen {
    public static void gatherDataHighPriority(GatherDataEvent event ) {
        System.out.println("AHHHHH");
        if (event.getMods().contains(CreateIndustrial.MODID))
            addExtraRegistrateData();
    }

    public static void gatherData(GatherDataEvent event) {
        System.out.println("Attempt Create Industrial Datagen");

        if (!event.getMods().contains(CreateIndustrial.MODID))
            return;

        System.out.println("Create Industrial: Datagen");

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeServer()) {
            System.out.println("Create Industrial: Load Recipes");

            CIRecipeProvider.registerAllProcessing(generator, output, lookupProvider);
        }
    }

    private static void addExtraRegistrateData() {

    }

    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/create/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find defualt lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
        }
    }
}
