package ot.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Avaja on 15.12.2016.
 */
public class RenderTeslaSpark extends Render {

    @Override
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        glBegin(GL_LINES);
        glTranslated(entity.posX, entity.posY, entity.posZ);
        glVertex3d(0, 0, 0);
        glVertex3d(0, 1000, 0);
        glEnd();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
