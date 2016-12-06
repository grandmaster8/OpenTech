package OpenTechnology.render;

import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avaja on 05.12.2016.
 */
public class WorldRender {
    private static List<AbstractRender> renders = new ArrayList<AbstractRender>();

    public static void render(RenderWorldLastEvent worldLastEvent){
        long nowTime = System.currentTimeMillis();
        for(int i = 0; i < renders.size(); i++){
            AbstractRender abstractRender = renders.get(i);
            if(abstractRender.isDead() || abstractRender.getDeltaTime() >= abstractRender.getTime())
                renders.remove(i);

            abstractRender.calcDeltaTime(nowTime);
            abstractRender.render(worldLastEvent.context, abstractRender.getDeltaTime());
        }
    }

    public static void addRender(AbstractRender abstractRender){
        renders.add(abstractRender);
    }
}
