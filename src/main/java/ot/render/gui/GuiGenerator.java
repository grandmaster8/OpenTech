package ot.render.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import ot.OpenTechnology;

/**
 * Created by Avaja on 09.03.2017.
 */
public class GuiGenerator extends GuiContainer {

    private static ResourceLocation texture = new ResourceLocation(OpenTechnology.MODID + ":textures/gui/generator.png");
    private static float progress = 0;

    public GuiGenerator(Container container) {
        super(container);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        progress -= 0.5f;
        if(progress <= 0)
            progress = 100;
        mc.renderEngine.bindTexture(texture);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2 + 5;
        drawTexturedModalRect(x, y, 0, 0, 176, 176);

        float per = 65 / 100.0f;
        int pixel = (int) (progress * per);
        drawTexturedModalRect(x + 71, y + 12 + 65 - pixel, 176, 65 - pixel, 34, 65 - (65 - (int)(progress * per)));
    }
}
