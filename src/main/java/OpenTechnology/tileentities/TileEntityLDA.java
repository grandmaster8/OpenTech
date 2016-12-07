package OpenTechnology.tileentities;

import OpenTechnology.Config;
import OpenTechnology.system.LDAS;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

/**
 * Created by Avaja on 07.12.2016.
 */
public class TileEntityLDA extends TileEntity  implements Analyzable, Environment {

    protected Node node;
    private boolean addToNetwork = false;

    private int distance, channel;
    private boolean isStructure;

    public TileEntityLDA() {
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).create();
        distance = Config.ldaMaxDistance;
        isStructure = false;
    }

    @Override
    public void updateEntity() {
        if(!addToNetwork){
            addToNetwork = true;
            Network.joinOrCreateNetwork(this);
        }
    }

    @Callback(doc="get max distance for data transmission.")
    public Object[] getMaxDistance(Context context, Arguments arguments) throws Exception{
        return new Object[]{Config.ldaMaxDistance};
    }

    @Callback(doc="get current distance.")
    public Object[] getDistance(Context context, Arguments arguments) throws Exception{
        return new Object[]{distance};
    }

    @Callback(doc="get real antenna distance considering height.")
    public Object[] getRealDistance(Context context, Arguments arguments) throws Exception{
        return new Object[]{distance * yCoord / 256};
    }

    @Callback(doc="open channel.")
    public Object[] open(Context context, Arguments arguments) throws Exception{
        int channel = arguments.checkInteger(0);
        if(channel >= 0 && channel <= Math.pow(2, 16)){
            context.pause(3);
            this.channel = channel;
            return new Object[]{true};
        }
        return new Object[]{false};
    }

    @Callback(doc="set current distance.")
    public Object[] setDistance(Context context, Arguments arguments) throws Exception{
        int dist = arguments.checkInteger(0);
        if(dist >= 0 && dist < Config.ldaMaxDistance){
            distance = dist;
            return new Object[]{true};
        }
        return new Object[]{false};
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 2, yCoord + 16, zCoord + 2);
    }

    @Callback(doc="broadcast data.")
    public Object[] broadcast(Context context, Arguments arguments) throws Exception{
        if(distance > 0){
            String data = arguments.checkString(0);
            LDAS.sendMessage(this, data);
            return new Object[]{true};
        }
        return new Object[]{false};
    }

    public void receiveMessage(TileEntityLDA sender, int distance, String message){
        node.sendToReachable("computer.signal", "ld_message", sender.node().address(), distance, message);
    }

    public int getDistance() {
        return distance;
    }

    public int getChannel() {
        return channel;
    }

    public boolean isStructure() {
        return isStructure;
    }

    private String getComponentName() {
        return "lda";
    }

    public int getFacing() {
        return (worldObj == null) ? 0 : this.getBlockMetadata();
    }

    @Override
    public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return new Node[]{node};
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

    @Override
    public void invalidate() {
        super.invalidate();
        if (node != null) node.remove();
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        if (node != null) node.remove();
        LDAS.removeLDA(this);
    }

    // ----------------------------------------------------------------------- //

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        distance = nbt.getInteger("distance");
        channel = nbt.getInteger("channel");
        if (node != null && node.host() == this) {
            node.load(nbt.getCompoundTag("oc:node"));
        }
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("distance", distance);
        nbt.setInteger("channel", channel);
        if (node != null && node.host() == this) {
            final NBTTagCompound nodeNbt = new NBTTagCompound();
            node.save(nodeNbt);
            nbt.setTag("oc:node", nodeNbt);
        }
    }
}
