package ot.events;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeEventFactory;
import ot.Config;
import ot.blocks.Blocks;
import ot.proxy.CommonProxy;
import ot.system.SparksSystem;
import ot.utils.CheckVersion;

/**
 * Created by Avaja on 10.12.2016.
 */
public class ClientFMLEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent loggedInEvent){
        CheckVersion.check(loggedInEvent.player);
    }
}
