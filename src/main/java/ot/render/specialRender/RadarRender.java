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
        GL11.glPushMatrix();
        this.radarModel.renderSensor(radar.getYaw());
        //ItemStack sensorCardStack = radar.getSensorCardStack();
        int placing = (radar.getFacing()+1) * 90;
        GL11.glPopMatrix();
        GL11.glRotatef(placing, 0, 1, 0);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();

    }
}
