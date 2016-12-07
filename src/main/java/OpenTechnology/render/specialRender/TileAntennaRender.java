package OpenTechnology.render.specialRender;

import OpenTechnology.OpenTechnology;
import OpenTechnology.render.model.ModelAntenna;
import OpenTechnology.tileentities.TileEntityLDA;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Avaja on 07.12.2016.
 */
public class TileAntennaRender extends TileEntitySpecialRenderer {

       private ModelAntenna model = new ModelAntenna();
       private ResourceLocation texture = new ResourceLocation(OpenTechnology.MODID + ":textures/models/antennaController.png");

        @Override
        public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float t) {
            TileEntityLDA tile = (TileEntityLDA) tileEntity;
            if(tile.isStructure()){
                GL11.glPushMatrix();
                GL11.glTranslatef((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f);
                GL11.glRotatef(180, 0, 0, 1);
                GL11.glTranslatef(0.0f, -1.0f, 0.0f);
                GL11.glPushMatrix();
                bindTexture(texture);
                model.render(null, 0F, 0F, 0f, 0f, 0f, 0.0625f);
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }
        }
}
