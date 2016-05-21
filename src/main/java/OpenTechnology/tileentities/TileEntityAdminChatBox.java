package OpenTechnology.tileentities;

import OpenTechnology.Config;
import OpenTechnology.system.ChatBoxEventSystem;
import com.mojang.realmsclient.gui.ChatFormatting;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import li.cil.oc.api.prefab.TileEntityEnvironment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Avaja on 05.05.2016.
 */
public class TileEntityAdminChatBox extends TileEntityEnvironment implements SimpleComponent, Analyzable {

    public TileEntityAdminChatBox() {
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).create();
        ChatBoxEventSystem.add(this);
    }

    public void eventMessage(EntityPlayer player, String message){
        if (this.node() != null)
            this.node().sendToReachable("computer.signal", "chat_message", player.getDisplayName(), message);
    }

    public void eventCommand(EntityPlayer player, String message){
        if(this.node() != null)
            this.node().sendToReachable("computer.signal", "chat_command", player.getDisplayName(), message);
    }

    @Override
    public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return new Node[]{node};
    }

    @Callback(doc="function(message:string); say some text")
    public Object[] say(Context context, Arguments arguments) throws Exception{

        System.out.println(String.format("say: x=%d, y=%d, z=%d", xCoord, yCoord, zCoord));

        for (WorldServer world : MinecraftServer.getServer().worldServers){
            List<EntityPlayer> playerList = world.playerEntities;
            for (EntityPlayer entityPlayer : playerList){
                entityPlayer.addChatMessage(new ChatComponentText(String.format("%s", arguments.checkString(0))));
            }
        }
        return new Object[]{};
    }

    @Callback(doc="function(message:string); formatting function, @ replace §. It works like, say.")
    public Object[] sayColored(Context context, Arguments arguments) throws Exception{

        System.out.println(String.format("say: x=%d, y=%d, z=%d", xCoord, yCoord, zCoord));

        String message = arguments.checkString(0).replace(Config.prefixChat.charAt(0), (char) 167);

        for (WorldServer world : MinecraftServer.getServer().worldServers){
            List<EntityPlayer> playerList = world.playerEntities;
            for (EntityPlayer entityPlayer : playerList){
                entityPlayer.addChatMessage(new ChatComponentText(message));

            }
        }
        return new Object[]{};
    }

    @Callback(doc="function(name:string, message:string); ")
    public Object[] tell(Context context, Arguments arguments) throws Exception{
        String name = arguments.checkString(0);
        String message = arguments.checkString(1);

        for (WorldServer world : MinecraftServer.getServer().worldServers){
            List<EntityPlayer> playerList = world.playerEntities;
            for (EntityPlayer entityPlayer : playerList){
                if (entityPlayer.getDisplayName().equals(name))
                    entityPlayer.addChatMessage(new ChatComponentText(message));
            }
        }
        return new Object[]{};
    }

    @Override
    public String getComponentName() {
        return "admin_chatbox";
    }
}
