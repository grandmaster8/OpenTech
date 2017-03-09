package ot.events;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import ot.Config;
import ot.blocks.Blocks;
import ot.proxy.CommonProxy;
import ot.system.SparksSystem;
import ot.utils.CheckVersion;

/**
 * Created by Avaja on 10.12.2016.
 */
public class ClientFMLEvents {

    public static boolean detectWrench = false;

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent tickEvent){

        SparksSystem.updateAll();

        if(Config.registerDecorativeCable){
            Minecraft minecraft = Minecraft.getMinecraft();

            if(minecraft.thePlayer != null){
                ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
                EntityPlayer player = minecraft.thePlayer;


                if(itemStack != null){
                    if(itemStack.getItem() == ot.item.Items.wrench && !detectWrench){
                        CommonProxy.isWrench = !CommonProxy.isWrench;
                        detectWrench = true;
                        CommonProxy.isWrench = true;
                        Blocks.cableDecor.setLightOpacity(0);

                        minecraft.renderGlobal.markBlockForRenderUpdate((int)player.posX, (int)player.posY, (int)player.posZ);
                    }else if(itemStack.getItem() != ot.item.Items.wrench){
                        CommonProxy.isWrench = false;
                        detectWrench = false;

                        Blocks.cableDecor.setLightOpacity(255);

                        minecraft.renderGlobal.markBlockForRenderUpdate((int)player.posX, (int)player.posY, (int)player.posZ);
                    }
                }else if(detectWrench && CommonProxy.isWrench){
                    CommonProxy.isWrench = false;
                    detectWrench = false;

                    Blocks.cableDecor.setLightOpacity(255);

                    minecraft.renderGlobal.markBlockForRenderUpdate((int)player.posX, (int)player.posY, (int)player.posZ);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent loggedInEvent){
        CheckVersion.check(loggedInEvent.player);
    }
}
