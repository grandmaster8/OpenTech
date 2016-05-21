package OpenTechnology.events;

import OpenTechnology.system.ChatBoxEventSystem;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.ServerChatEvent;

/**
 * Created by Avaja on 05.05.2016.
 */
public class CommonEvents {

    @SubscribeEvent
    public void chatMessage(ServerChatEvent event){
        if (ChatBoxEventSystem.eventMessage(event.player, event.message))
            event.setCanceled(true);
    }
}
