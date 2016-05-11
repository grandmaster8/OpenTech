package OpenTechnology.system;

import OpenTechnology.tileentities.TileEntityAdminChatBox;
import OpenTechnology.tileentities.TileEntityChatBox;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;

/**
 * Created by Avaja on 05.05.2016.
 */
public class ChatBoxEventSystem {
    public static ArrayList<TileEntityAdminChatBox> adminChatBoxs = new ArrayList<TileEntityAdminChatBox>();
    public static ArrayList<TileEntityChatBox> chatBoxes = new ArrayList<TileEntityChatBox>();

    public static void add(TileEntityAdminChatBox tile){
        adminChatBoxs.add(tile);
    }

    public static void add(TileEntityChatBox tile){
        chatBoxes.add(tile);
    }

    public static void remove(TileEntityAdminChatBox tile){
        adminChatBoxs.remove(tile);
    }

    public static void remove(TileEntityChatBox tile){
        chatBoxes.remove(tile);
    }

    public static boolean eventMessage(EntityPlayer player, String message){

        if (message.startsWith("#")){
            eventCommand(player, message);
            return true;
        }else{
            for (TileEntityChatBox box : chatBoxes){
                if (player.getDistance(box.xCoord, box.yCoord, box.zCoord) <= box.getRadius()){
                    box.eventMessage(player, message);
                }
            }

            for (TileEntityAdminChatBox box : adminChatBoxs){
                box.eventMessage(player, message);
            }
        }
        return false;
    }

    private static void eventCommand(EntityPlayer player, String message){
        message = message.substring(1);

        for (TileEntityChatBox box : chatBoxes){
            if (player.getDistance(box.xCoord, box.yCoord, box.zCoord) <= box.getRadius()){
                box.eventCommand(player, message);
            }
        }

        for (TileEntityAdminChatBox box : adminChatBoxs){
            box.eventCommand(player, message);
        }
    }
}
