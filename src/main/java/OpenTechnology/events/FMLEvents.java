package OpenTechnology.events;

import OpenTechnology.system.SparksSystem;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * Created by Avaja on 07.12.2016.
 */
public class FMLEvents {
    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent tickEvent){
        SparksSystem.updateAll();
    }
}
