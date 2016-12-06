package OpenTechnology.render;

import net.minecraft.client.renderer.RenderGlobal;

/**
 * Created by Avaja on 05.12.2016.
 */
public interface Render {
    public void render(RenderGlobal renderGlobal, long delta);
    public long time();
    public boolean isDead();
}
