package OpenTechnology.events;

import OpenTechnology.blocks.Blocks;
import OpenTechnology.system.SparksSystem;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Avaja on 07.12.2016.
 */
public class FMLEvents {

    public static boolean isWrench = false, detectWrench = false;

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent tickEvent){
        SparksSystem.updateAll();

        if(Minecraft.getMinecraft().thePlayer != null){
            ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
            Minecraft minecraft = Minecraft.getMinecraft();

            EntityPlayer player = minecraft.thePlayer;


            if(itemStack != null){
                if(itemStack.getItem() == OpenTechnology.item.Items.wrench && !detectWrench){
                    isWrench = !isWrench;
                    detectWrench = true;
                    isWrench = true;
                    Blocks.cableDecor.setLightOpacity(0);

                    minecraft.renderGlobal.markBlockForRenderUpdate((int)player.posX, (int)player.posY, (int)player.posZ);
                }else if(itemStack.getItem() != OpenTechnology.item.Items.wrench){
                    isWrench = false;
                    detectWrench = false;

                    Blocks.cableDecor.setLightOpacity(255);

                    minecraft.renderGlobal.markBlockForRenderUpdate((int)player.posX, (int)player.posY, (int)player.posZ);
                }
            }else if(detectWrench && isWrench){
                isWrench = false;
                detectWrench = false;

                Blocks.cableDecor.setLightOpacity(255);

                minecraft.renderGlobal.markBlockForRenderUpdate((int)player.posX, (int)player.posY, (int)player.posZ);
            }
        }

    }
}
