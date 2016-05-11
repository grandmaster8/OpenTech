package OpenTechnology.tileentities;

import OpenTechnology.Config;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Analyzable;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.SimpleComponent;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.TileEntityEnvironment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

/**
 * Created by Avaja on 06.05.2016.
 */
public class TileEntityChatBox extends TileEntityEnvironment implements SimpleComponent, Analyzable {

    private int radius;

    public TileEntityChatBox() {
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).create();
    }

    @Override
    public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return new Node[]{node};
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public String getComponentName() {
        return "chatbox";
    }

    public void eventMessage(EntityPlayer player, String message){
        if (this.node() != null)
            this.node().sendToReachable("computer.signal", "chat_message", player.getDisplayName(), message);
    }

    public void eventCommand(EntityPlayer player, String message){
        if (this.node() != null)
            this.node().sendToReachable("computer.signal", "chat_command", player.getDisplayName(), message);
    }

    @Callback
    public Object[] say(Context context, Arguments arguments) throws Exception{

        String message = arguments.checkString(0);

        if (message.length() > Config.maxMessageLength)
            message = message.substring(0, Config.maxMessageLength);

        System.out.println(String.format("say: x=%d, y=%d, z=%d", xCoord, yCoord, zCoord));

        List<EntityPlayer> players = worldObj.playerEntities;
        for (EntityPlayer player : players){
            if (player.getDistance(this.xCoord, this.yCoord, this.zCoord) <= radius){
                player.addChatMessage(new ChatComponentText(message));
            }
        }
        return new Object[]{};
    }

    @Callback
    public Object[] sayColored(Context context, Arguments arguments) throws Exception{

        String message = arguments.checkString(0);

        if (message.length() > Config.maxMessageLength)
            message = message.substring(0, Config.maxMessageLength);

        System.out.println(String.format("say: x=%d, y=%d, z=%d", xCoord, yCoord, zCoord));

        message = message.replace(Config.prefixChat.charAt(0), (char) 167);

        List<EntityPlayer> players = worldObj.playerEntities;
        for (EntityPlayer player : players){
            if (player.getDistance(this.xCoord, this.yCoord, this.zCoord) <= radius){
                player.addChatMessage(new ChatComponentText(message));
            }
        }
        return new Object[]{};
    }

    @Callback
    public Object[] setRadius(Context context, Arguments arguments) throws Exception{
        int tmp = arguments.checkInteger(0);
        if (tmp > Config.chatboxMaxRadius) tmp = Config.chatboxMaxRadius;
        radius = tmp;
        return new Object[]{};
    }

    @Callback
    public Object[] getRadius(Context context, Arguments arguments) throws Exception{
        return new Object[]{radius};
    }

    @Callback
    public Object[] getMaxRadius(Context context, Arguments arguments) throws Exception{
        return new Object[]{Config.chatboxMaxRadius};
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        radius = nbt.getInteger("radius");
        if (radius <= 0) radius = Config.chatboxMaxRadius;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("radius", radius);
    }
}
