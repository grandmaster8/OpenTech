package ot.system;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import ot.OpenTechnology;
import ot.tileentities.TileEntityChatBox;
import ot.tileentities.TileEntityCreativeChatBox;

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
        OpenTechnology.PROFILER.start("chatbox_remove");
        for (int i = 0; i < adminChatBoxs.size(); i++){
            if (adminChatBoxs.get(i) == tile) {
                adminChatBoxs.remove(i);
            }
        }
        OpenTechnology.PROFILER.end();
    }

    public static void remove(TileEntityChatBox tile){
        OpenTechnology.PROFILER.start("chatbox_remove");
        for (int i = 0; i < chatBoxes.size(); i++){
            if (chatBoxes.get(i) == tile)
                chatBoxes.remove(i);
        }
        OpenTechnology.PROFILER.end();
    }

    public static boolean eventMessage(EntityPlayer player, String message){
        OpenTechnology.PROFILER.start("handle_message");
        if (message.startsWith("#")){
            eventCommand(player, message);
            OpenTechnology.PROFILER.end();
            return true;
        }else{
            for (TileEntityChatBox box : chatBoxes){
                box.eventMessage(player, message);
            }

            for (TileEntityCreativeChatBox box : adminChatBoxs){
                box.eventMessage(player, message);
            }
        }
        OpenTechnology.PROFILER.end();
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

    public static void eventDeath(EntityPlayer player, DamageSource damageSource){
        OpenTechnology.PROFILER.start("event_death");
        for (TileEntityCreativeChatBox box : adminChatBoxs){
            box.eventDeath(player, damageSource);
        }
        OpenTechnology.PROFILER.end();
    }

    public static void eventLoggedIn(EntityPlayer player) {
        OpenTechnology.PROFILER.start("event_loggedIn");
        for (TileEntityCreativeChatBox box : adminChatBoxs){
            box.eventLoggedIn(player);
        }
        OpenTechnology.PROFILER.end();
    }

    public static void eventLoggedOut(EntityPlayer player) {
        OpenTechnology.PROFILER.start("event_loggedOut");
        for (TileEntityCreativeChatBox box : adminChatBoxs){
            box.eventLoggedOut(player);
        }
        OpenTechnology.PROFILER.end();
    }
}
