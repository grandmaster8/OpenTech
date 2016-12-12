package ot.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import ot.system.ChatBoxEventSystem;
import ot.system.PIBS;

/**
 * Created by Avaja on 05.05.2016.
 */
public class CommonEvents {

    @SubscribeEvent
    public void chatMessage(ServerChatEvent event){
        if (ChatBoxEventSystem.eventMessage(event.player, event.message))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void playerDeath(LivingDeathEvent event){
        if (event.entityLiving instanceof EntityPlayer){
            ChatBoxEventSystem.eventDeath((EntityPlayer) event.entityLiving);
        }
    }

    @SubscribeEvent
    public void playerLogging(PlayerEvent.PlayerLoggedInEvent loggedInEvent){
        ChatBoxEventSystem.eventLogging(loggedInEvent.player);
        PIBS.checkPlayer(loggedInEvent.player);
    }
}
