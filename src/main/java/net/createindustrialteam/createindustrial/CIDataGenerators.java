
package net.createindustrialteam.createindustrial;

import net.createindustrialteam.createindustrial.data.CIBlockDataGen;
import net.minecraft.data.DataGenerator;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class CIDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();

        generator.addProvider(
                event.includeClient(),
                new CIBlockDataGen(
                        generator.getPackOutput(),
                        event.getExistingFileHelper()
                )
        );
    }
}
