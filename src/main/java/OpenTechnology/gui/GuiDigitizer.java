package OpenTechnology.gui;

import OpenTechnology.OpenTechnology;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Avaja on 28.05.2016.
 */
public class GuiDigitizer extends GuiContainer {
    private static ResourceLocation background = new ResourceLocation(OpenTechnology.MODID+":textures/gui/digitizer.png");

    public GuiDigitizer(Container container) {
        super(container);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, 256, 256);
    }
}
