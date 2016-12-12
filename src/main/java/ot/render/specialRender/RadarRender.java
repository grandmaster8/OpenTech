package ot.render.specialRender;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ot.OpenTechnology;
import ot.render.model.RadarModel;
import ot.tileentities.TileEntityRadar;

/**
 * Created by Avaja on 07.12.2016.
 */
public class RadarRender extends TileEntitySpecialRenderer {

    private RadarModel radarModel;

    public RadarRender() {
        radarModel = new RadarModel();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick) {

        TileEntityRadar radar = (TileEntityRadar) tileEntity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        this.bindTexture(new ResourceLocation(OpenTechnology.MODID + ":textures/models/radar.png"));
        int rotation = (int) radar.getRotation();
        GL11.glPushMatrix();
        this.radarModel.renderSensor(rotation);
        //ItemStack sensorCardStack = radar.getSensorCardStack();
        int placing = (radar.getFacing()+1) * 90;
        GL11.glPopMatrix();
        GL11.glRotatef(placing, 0, 1, 0);
        /*if (sensorCardStack != null && sensorCardStack.getItem() instanceof ItemSensorCard) {

            GL11.glScalef(0.02f, 0.02f, 0.02f);
            GL11.glRotatef(90.0F, 1, 0, 0);
            GL11.glTranslatef(-8.0f, 4.0f, 12.0f);
            renderItem = ((RenderItem) RenderManager.instance.getEntityClassRenderObject(EntityItem.class));
            TextureManager re = Minecraft.getMinecraft().renderEngine;
            FontRenderer fr = this.func_147498_b();
            renderItem.renderItemIntoGUI(fr, re, sensorCardStack, 0, 0);

        }*/

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();

    }
}
