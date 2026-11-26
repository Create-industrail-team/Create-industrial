package net.createindustrialteam.createindustrial.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import net.createindustrialteam.createindustrial.CIPartialModels;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;

public class AnimatedLaserEngraver extends AnimatedKinetics {

    @Override
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
        PoseStack matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 200);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));
        int scale = 24;

        blockElement(shaft(Direction.Axis.Z))
                .rotateBlock(0, 0, getCurrentAngle())
                .scale(scale)
                .render(graphics);

        blockElement(AllBlocks.MECHANICAL_PRESS.getDefaultState())
                .scale(scale)
                .render(graphics);

        blockElement(CIPartialModels.LASER_ENGRAVER_LASER)
                .atLocal(0, -getAnimatedHeadOffset(), 0)
                .scale(scale)
                .render(graphics);

        matrixStack.popPose();
    }

    private float getAnimatedHeadOffset() {
        float cycle = (AnimationTickHolder.getRenderTime() - offset * 8) % 30;
        if (cycle < 10) {
            float progress = cycle / 10;
            return -(progress * progress * progress);
        }
        if (cycle < 15)
            return -1;
        if (cycle < 20)
            return -1 + (1 - ((20 - cycle) / 5));
        return 0;
    }
}
