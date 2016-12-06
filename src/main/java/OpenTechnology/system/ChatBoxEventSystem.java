package OpenTechnology.system;

import OpenTechnology.tileentities.TileEntityCreativeChatBox;
import OpenTechnology.tileentities.TileEntityChatBox;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;

/**
 * Created by Avaja on 05.05.2016.
 */
public class ChatBoxEventSystem {
    private static ArrayList<TileEntityCreativeChatBox> adminChatBoxs = new ArrayList<TileEntityCreativeChatBox>();
    private static ArrayList<TileEntityChatBox> chatBoxes = new ArrayList<TileEntityChatBox>();

    public static void add(TileEntityCreativeChatBox tile){
        adminChatBoxs.add(tile);
    }

    public static void add(TileEntityChatBox tile){
        chatBoxes.add(tile);
    }

    public static void remove(TileEntityCreativeChatBox tile){
        for (int i = 0; i < adminChatBoxs.size(); i++){
            if (adminChatBoxs.get(i) == tile)
                adminChatBoxs.remove(i);
        }
    }

    public static void remove(TileEntityChatBox tile){
        for (int i = 0; i < chatBoxes.size(); i++){
            if (chatBoxes.get(i) == tile)
                chatBoxes.remove(i);
        }
    }

    public static boolean eventMessage(EntityPlayer player, String message){

        if (message.startsWith("#")){
            eventCommand(player, message);
            return true;
        }else{
            for (TileEntityChatBox box : chatBoxes){
                box.eventMessage(player, message);
            }

            for (TileEntityCreativeChatBox box : adminChatBoxs){
                box.eventMessage(player, message);
            }
        }
        return false;
    }

    private static void eventCommand(EntityPlayer player, String message){
        message = message.substring(1);

        for (TileEntityChatBox box : chatBoxes){
            box.eventCommand(player, message);
        }

        for (TileEntityCreativeChatBox box : adminChatBoxs){
            box.eventCommand(player, message);
        }
    }

    public static void eventDeath(EntityPlayer player){
        for (TileEntityCreativeChatBox box : adminChatBoxs){
            box.eventDeath(player);
        }
    }

    public static void eventLogging(EntityPlayer player) {
        for (TileEntityCreativeChatBox box : adminChatBoxs){
            box.eventLogging(player);
        }
    }
}
