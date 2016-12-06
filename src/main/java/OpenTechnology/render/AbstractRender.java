package OpenTechnology.render;

import net.minecraft.client.renderer.RenderGlobal;

/**
 * Created by Avaja on 05.12.2016.
 */
public class AbstractRender implements Render {

    private long time, deltaTime, startTime;
    protected boolean isDead;

    public void calcDeltaTime(long nowTime) {
        deltaTime = nowTime - startTime;
    }

    public AbstractRender(long time, long startTime) {
        this.time = time;
        this.startTime = startTime;
    }

    @Override
    public void render(RenderGlobal renderGlobal, long delta) {}

    public long getDeltaTime() {
        return deltaTime;
    }

    @Override
    public long time() {
        return time;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }
}
