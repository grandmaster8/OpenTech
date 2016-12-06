package OpenTechnology.render.effects;

import OpenTechnology.render.AbstractRender;
import li.cil.oc.api.internal.Robot;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * Created by Avaja on 05.12.2016.
 */
public class TeslaLightningEffect extends AbstractRender {

    private Robot robot;
    private Entity target;

    public TeslaLightningEffect(Robot robot, Entity target) {
        super(1000, System.currentTimeMillis());

        this.robot = robot;
        this.target = target;
    }

    @Override
    public void render(RenderGlobal renderGlobal, long delta) {
        GL11.glBegin(GL11.GL_LINES);

        GL11.glEnd();
    }
}
