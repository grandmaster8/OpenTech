package ot.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import ot.system.ChatBoxEventSystem;

/**
 * Created by Avaja on 07.12.2016.
 */
public class FMLEvents {
    @SubscribeEvent
    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent loggedInEvent){
        ChatBoxEventSystem.eventLoggedIn(loggedInEvent.player);
    }

    @SubscribeEvent
    public void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent playerLoggedOutEvent){
        ChatBoxEventSystem.eventLoggedOut(playerLoggedOutEvent.player);
    }
}
