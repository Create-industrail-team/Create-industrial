package net.createindustrialteam.createindustrial.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import net.minecraft.client.gui.GuiGraphics;

public class EngravingAssemblySubCategory extends SequencedAssemblySubCategory {

    AnimatedLaserEngraver engraver;

    public EngravingAssemblySubCategory() {
        super(25);
        engraver = new AnimatedLaserEngraver();
    }

    // TODO implement
    @Override
    public void draw(SequencedRecipe<?> recipe, GuiGraphics graphics, double mouseX, double mouseY, int index) {
        PoseStack ms = graphics.pose();
    }
}
