package OpenTechnology.render;

import OpenTechnology.OpenTechnology;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * Created by Avaja on 04.08.2016.
 */
public class RenderTileEntityRadar extends TileEntitySpecialRenderer {

    private IModelCustom model;

    private ResourceLocation modelLocation;

    public RenderTileEntityRadar() {
        modelLocation = new ResourceLocation(OpenTechnology.MODID + ":" + "textures/model/test.obj");
        model = AdvancedModelLoader.loadModel(modelLocation);
    }

    public void renderModelAt(TileEntity tileEntity, double x, double y, double z, float f){
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5F,(float) y + 1.5F,(float) z + 0.5F);
        GL11.glRotatef(180, 0.0F, 0.0F, 1F);

        GL11.glPushMatrix();

        model.renderAll();
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        renderModelAt(tileEntity, x, y, z, f);
    }
}
