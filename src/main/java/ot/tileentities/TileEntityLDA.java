package ot.tileentities;

import li.cil.oc.api.API;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import ot.Config;
import ot.blocks.Blocks;
import ot.system.LDAS;

/**
 * Created by Avaja on 07.12.2016.
 */
public class TileEntityLDA extends TileEntity  implements Analyzable, Environment, SidedComponent {

    protected Node node;
    private boolean addToNetwork = false;

    private int distance, channel, transmitTime;
    private boolean isStructure, isTransmit;

    public TileEntityLDA() {
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).withConnector().create();
        distance = Config.ldaMaxDistance;
        isStructure = false;
        isTransmit = false;

        LDAS.addLDA(this);
    }

    @Override
    public void updateEntity() {
        if(isTransmit){
            transmitTime--;
            if(transmitTime <= 0)
                isTransmit = false;
        }
        if(!addToNetwork){
            addToNetwork = true;
            Network.joinOrCreateNetwork(this);
        }
        if(!isStructure){
            checkStructure();
        }
    }



    private void checkStructure(){
        for(int i = 1; i <= 14; i++){
            Block block = worldObj.getBlock(xCoord, yCoord + i, zCoord);
            if(block != Blocks.antenna)
                return;
        }
        if(worldObj.getBlock(xCoord, yCoord + 15, zCoord) == Blocks.antennaCell){
            isStructure = true;
            for(int i = 0; i <= 15; i++)
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + i, zCoord, 1, 3);
        }
    }

    public void destroyStructure(){
        for(int i = 0; i <= 15; i++)
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord + i, zCoord, 0, 3);
        isStructure = false;
    }

    @Callback(doc="get max distance for data transmission.")
    public Object[] getMaxDistance(Context context, Arguments arguments) throws Exception{
        if(!isStructure) return new Object[]{false};
        return new Object[]{Config.ldaMaxDistance};
    }

    @Callback(doc="get current distance.")
    public Object[] getDistance(Context context, Arguments arguments) throws Exception{
        if(!isStructure) return new Object[]{false};
        return new Object[]{distance};
    }

    @Callback(doc="get real antenna distance considering height.")
    public Object[] getRealDistance(Context context, Arguments arguments) throws Exception{
        if(!isStructure) return new Object[]{false};
        return new Object[]{distance * (yCoord + 16) / 256f};
    }

    @Callback(doc="get current working channel.")
    public Object[] getChannel(Context context, Arguments arguments) throws Exception{
        if(!isStructure) return new Object[]{false};
        return new Object[]{channel};
    }

    @Callback(doc="check transmit.")
    public Object[] isTransmit(Context context, Arguments arguments) throws Exception{
        if(!isStructure) return new Object[]{false};
        return new Object[]{isTransmit};
    }

    @Callback(doc="function(channel:int), open channel.")
    public Object[] open(Context context, Arguments arguments) throws Exception{
        if(!isStructure) return new Object[]{false};
        int channel = arguments.checkInteger(0);
        if(channel >= 0 && channel <= Math.pow(2, 16)){
            context.pause(3);
            this.channel = channel;
            return new Object[]{true};
        }
        return new Object[]{false};
    }

    @Callback(doc="function(distance:int), set current distance.")
    public Object[] setDistance(Context context, Arguments arguments) throws Exception{
        if(!isStructure) return new Object[]{false};
        int dist = arguments.checkInteger(0);
        if(dist >= 0 && dist < Config.ldaMaxDistance){
            distance = dist;
            context.pause(3);
            return new Object[]{true};
        }
        return new Object[]{false};
    }

    @Callback(doc="get max packet length.")
    public Object[] maxPacketSize(){
        return new Object[]{Config.ldaMaxPacketSize};
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 2, yCoord + 16, zCoord + 2);
    }

    @Callback(doc="function(data:string), broadcast data.")
    public Object[] broadcast(Context context, Arguments arguments) throws Exception{
        Connector connector = (Connector) node;
        if(distance > 0 && isStructure && (connector.tryChangeBuffer(Config.ldaEnergyUsage) || !API.isPowerEnabled)){
            if(isTransmit)
                return new Object[]{false};

            String data = arguments.checkString(0);
            if(data.length() > Config.ldaMaxPacketSize)
                return new Object[]{false};

            LDAS.broadcastMessage(this, data);
            isTransmit = true;
            transmitTime = 60;
            return new Object[]{true};
        }
        return new Object[]{false};
    }

    @Callback(doc="function(address:string, data:string), sending data to a specific address.")
    public Object[] send(Context context, Arguments arguments) throws Exception{
        Connector connector = (Connector) node;
        if(distance > 0 && isStructure && (connector.tryChangeBuffer(Config.ldaEnergyUsage) || !API.isPowerEnabled)){
            if(isTransmit)
                return new Object[]{false};

            String address = arguments.checkString(0);
            String data = arguments.checkString(1);
            if(data.length() > Config.ldaMaxPacketSize)
                return new Object[]{false};

            LDAS.sendMessage(this, address, data);
            isTransmit = true;
            transmitTime = 60;
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

    @Override
    public boolean canConnectNode(ForgeDirection side) {
        return ForgeDirection.DOWN == side;
    }
}
