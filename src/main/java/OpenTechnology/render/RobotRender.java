package OpenTechnology.render;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import li.cil.oc.api.event.RobotRenderEvent;
import li.cil.oc.api.internal.Robot;
import net.minecraft.client.Minecraft;

/**
 * Created by Avaja on 30.08.2016.
 */
public class RobotRender {

    @SubscribeEvent
    public void onRobotRender(RobotRenderEvent robotRenderEvent){
        if (Minecraft.getMinecraft().gameSettings.particleSetting == 0 && robotRenderEvent.agent != null){
            double x = robotRenderEvent.agent.xPosition();
            double y = robotRenderEvent.agent.yPosition();
            double z = robotRenderEvent.agent.zPosition();
            Robot robot = (Robot) robotRenderEvent.agent;
        }
    }
}
