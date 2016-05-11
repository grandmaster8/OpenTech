package OpenTechnology.events;

import OpenTechnology.system.ChatBoxEventSystem;
import OpenTechnology.tileentities.TileEntityPlayerInventoryBinder;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.event.ServerChatEvent;

/**
 * Created by Avaja on 05.05.2016.
 */
public class Events {

    @SubscribeEvent
    public void chatMessage(ServerChatEvent event){
        if (ChatBoxEventSystem.eventMessage(event.player, event.message))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void loggedIn(PlayerEvent.PlayerLoggedInEvent event){
        for (TileEntityPlayerInventoryBinder binder : TileEntityPlayerInventoryBinder.binders){
            binder.addPlayer(event.player);
        }
    }
}
