package net.createindustrialteam.createindustrial.blocks.laser_engraver;

import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.belt.BeltHelper;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour.TransportedResult;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;

import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import net.createindustrialteam.createindustrial.blocks.laser_engraver.EngravingBehaviour.Mode;

import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult.HOLD;
import static com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult.PASS;

public class BeltEngravingCallbacks {

    static ProcessingResult onItemReceived(TransportedItemStack transported,
                                                                   TransportedItemStackHandlerBehaviour hander, EngravingBehaviour behaviour) {
        if (behaviour.specifics.getKineticSpeed() == 0)
            return PASS;
        if (behaviour.running)
            return HOLD;
        if (!behaviour.specifics.tryProcessOnBelt(transported, null, true))
            return PASS;

        behaviour.start(Mode.BELT);
        return HOLD;
    }

    static ProcessingResult whenItemHeld(TransportedItemStack transported, TransportedItemStackHandlerBehaviour handler,
                                         EngravingBehaviour behaviour) {
        if (behaviour.specifics.getKineticSpeed() == 0)
            return PASS;
        if (!behaviour.running)
            return PASS;
        if (behaviour.runningTicks != PressingBehaviour.CYCLE)
            return HOLD;

        ArrayList<ItemStack> results = new ArrayList<>();
        if (!behaviour.specifics.tryProcessOnBelt(transported, results, false))
            return PASS;

        boolean bulk = transported.stack.getCount() == 1;

        transported.clearFanProcessingData();

        List<TransportedItemStack> collect = results.stream()
                .map(stack -> {
                    TransportedItemStack copy = transported.copy();
                    boolean centered = BeltHelper.isItemUpright(stack);
                    copy.stack = stack;
                    copy.locked = true;
                    copy.angle = centered ? 180 : Create.RANDOM.nextInt(360);
                    return copy;
                })
                .collect(Collectors.toList());

        if (bulk) {
            if (collect.isEmpty())
                handler.handleProcessingOnItem(transported, TransportedResult.removeItem());
            else
                handler.handleProcessingOnItem(transported, TransportedResult.convertTo(collect));

        } else {
            TransportedItemStack left = transported.copy();
            left.stack.shrink(1);

            if (collect.isEmpty())
                handler.handleProcessingOnItem(transported, TransportedResult.convertTo(left));
            else
                handler.handleProcessingOnItem(transported, TransportedResult.convertToAndLeaveHeld(collect, left));
        }

        behaviour.blockEntity.sendData();
        return HOLD;
    }

}
