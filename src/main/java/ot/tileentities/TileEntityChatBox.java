package ot.tileentities;

import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import ot.Config;
import ot.system.ChatBoxEventSystem;

import java.util.List;

/**
 * Created by Avaja on 06.05.2016.
 */
public class TileEntityChatBox extends TileEntity implements Analyzable, Environment {

    protected Node node;
    private boolean addToNetwork = false;

    private int radius;

    public TileEntityChatBox() {
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).create();
        ChatBoxEventSystem.add(this);
        radius = Config.chatboxMaxRadius;
    }

    @Override
    public void updateEntity() {
        if(!addToNetwork){
            addToNetwork = true;
            Network.joinOrCreateNetwork(this);
        }
    }

    @Override
    public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return new Node[]{node};
    }

    public int getRadius() {
        return radius;
    }

    public String getComponentName() {
        return "chatbox";
    }

    public void eventMessage(EntityPlayer player, String message){
        if (node != null)
            node.sendToReachable("computer.signal", "chat_message", player.getDisplayName(), message);
    }

    public void eventCommand(EntityPlayer player, String message){
        if (node != null)
            node.sendToReachable("computer.signal", "chat_command", player.getDisplayName(), message);
    }

    @Callback(doc="function(message:string); say some text")
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
    public Object[] setRadius(Context context, Arguments arguments) throws Exception{
        int tmp = arguments.checkInteger(0);
        if (tmp > Config.chatboxMaxRadius) tmp = Config.chatboxMaxRadius;
        if(tmp < 0) tmp = 0;
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
    public void invalidate() {
        super.invalidate();
        // Make sure to remove the node from its network when its environment,
        // meaning this tile entity, gets unloaded.
        if (node != null) node.remove();
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        if (node != null) node.remove();
        ChatBoxEventSystem.remove(this);
    }

    // ----------------------------------------------------------------------- //

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        radius = nbt.getInteger("radius");
        if (radius <= 0) radius = Config.chatboxMaxRadius;

        if (node != null && node.host() == this) {
            node.load(nbt.getCompoundTag("oc:node"));
        }
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("radius", radius);

        if (node != null && node.host() == this) {
            final NBTTagCompound nodeNbt = new NBTTagCompound();
            node.save(nodeNbt);
            nbt.setTag("oc:node", nodeNbt);
        }
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public void onConnect(Node node) {

    }

    @Override
    public void onDisconnect(Node node) {

    }

    @Override
    public void onMessage(Message message) {

    }
}
