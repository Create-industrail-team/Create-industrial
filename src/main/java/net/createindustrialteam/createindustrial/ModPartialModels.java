package net.createindustrialteam.createindustrial;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.Create;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class ModPartialModels {

    public static final PartialModel

            LASER_ENGRAVER_LASER = block("laser_engraver/laser");

    // Taken from AllPartialModels in Create mod
    private static PartialModel block(String path) {
        return PartialModel.of(CreateIndustrial.asResource("block/" + path));
    }

    public static void init() {
        // init static fields
    }

}
