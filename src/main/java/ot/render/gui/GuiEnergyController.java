package ot.render.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ot.OpenTechnology;
import ot.tileentities.TileEntityEnergyController;

/**
 * Created by Avaja on 31.01.2017.
 */
public class GuiEnergyController extends GuiContainer {

    private static ResourceLocation texture = new ResourceLocation(OpenTechnology.MODID + ":textures/gui/energyController.png");
    private TileEntityEnergyController energyController;

/*    private boolean renderInfo = false;
    private String info;
    private int x = 0, y = 0;*/

    public GuiEnergyController(Container container, TileEntityEnergyController energyController) {
        super(container);
        this.energyController = energyController;
    }

/*    @Override
    public void drawScreen(int x, int y, float p_73863_3_) {
        super.drawScreen(x, y, p_73863_3_);

        int sx = (this.width - this.xSize) / 2;
        int sy = (this.height - this.ySize) / 2 + 5;

        if(x >= sx + 75 && x <= sx + 55 + 52 && y >= sy + 20 && y <= sy + 36){
            renderInfo = true;
            this.x = x;
            this.y = y;

        }else{
            renderInfo = false;
        }

    }*/

    @Override
    protected void drawGuiContainerBackgroundLayer(float f1, int i1, int i2) {
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.getTextureManager().bindTexture(texture);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2 + 5;
        drawTexturedModalRect(x, y, 0, 0, 176, 176);

        int inputBuffer = energyController.getEnergyInputBuffer();
        //23

        int width = (int) (inputBuffer / ((float)energyController.getInputBufferCapacity()) * 24f);
        drawTexturedModalRect(x + 79, y + 20, 176, 14, width, 16);

        double scale = 0.85;

        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, 1);
        mc.fontRenderer.drawString(inputBuffer + "/" + energyController.getInputBufferCapacity(), (int) ((x + 110) / scale), (int) ((y + 26) / scale), 0x404040);
        GL11.glPopMatrix();
        GL11.glPopAttrib();

        int outputBuffer = energyController.getEnergyOutputBuffer();
        width = (int) (outputBuffer / ((float)energyController.getOutputBufferCapacity()) * 24f);
        drawTexturedModalRect(x + 79, y + 38, 176, 14, width, 16);

        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, 1);
        mc.fontRenderer.drawString(outputBuffer + "/" + energyController.getOutputBufferCapacity(), (int) ((x + 110) / scale), (int) ((y + 44) / scale), 0x404040);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
}
