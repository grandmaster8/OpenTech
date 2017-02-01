package ot.tileentities;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Avaja on 27.01.2017.
 */
public class TileEntityEnergyController extends TileEntity implements Analyzable, Environment, IEnergyTile, IEnergySource, IEnergySink {

    protected Node node;
    private boolean addToNetwork = false, addToEnergyNetwork = false;

    private int energyInputBuffer = 0,
            energyOutputBuffer = 0,
            inputBufferCapacity = 10000,
            outputBufferCapacity = 10000;

    public TileEntityEnergyController() {
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).create();
    }

    @Override
    public void updateEntity() {
        if(!addToNetwork){
            addToNetwork = true;
            Network.joinOrCreateNetwork(this);
        }

        if(!addToEnergyNetwork && !worldObj.isRemote){
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            addToEnergyNetwork = true;
        }

        //if(!worldObj.isRemote)
        //    System.out.println(energyInputBuffer + " " + energyOutputBuffer);
    }

    public int getEnergyInputBuffer() {
        return energyInputBuffer;
    }

    public int getEnergyOutputBuffer() {
        return energyOutputBuffer;
    }

    public void setEnergyInputBuffer(int energyInputBuffer) {
        this.energyInputBuffer = energyInputBuffer;
    }

    public void setEnergyOutputBuffer(int energyOutputBuffer) {
        this.energyOutputBuffer = energyOutputBuffer;
    }

    public int getInputBufferCapacity() {
        return inputBufferCapacity;
    }

    public int getOutputBufferCapacity() {
        return outputBufferCapacity;
    }

    public void setInputBufferCapacity(int inputBufferCapacity) {
        this.inputBufferCapacity = inputBufferCapacity;
    }

    public void setOutputBufferCapacity(int outputBufferCapacity) {
        this.outputBufferCapacity = outputBufferCapacity;
    }

    @Override
    public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return new Node[]{node};
    }

    @Override
    public Node node() {
        return node;
    }

    @Callback(doc="")
    public Object[] getEnergyInputBuffer(Context context, Arguments arguments) throws Exception{
        return new Object[]{energyInputBuffer};
    }

    @Callback(doc="")
    public Object[] getEnergyOutputBuffer(Context context, Arguments arguments) throws Exception{
        return new Object[]{energyOutputBuffer};
    }

    @Callback(doc="")
    public Object[] getInputBufferCapacity(Context context, Arguments arguments) throws Exception{
        return new Object[]{inputBufferCapacity};
    }

    @Callback(doc="")
    public Object[] getOutputBufferCapacity(Context context, Arguments arguments) throws Exception{
        return new Object[]{outputBufferCapacity};
    }

    @Callback(doc="")
    public Object[] transfer(Context context, Arguments arguments) throws Exception{
        int count = arguments.checkInteger(0);
        if(count > 0){
            if(count > energyInputBuffer)
                count = energyInputBuffer;

            if(outputBufferCapacity - energyOutputBuffer >= count){
                energyInputBuffer -= count;
                energyOutputBuffer += count;
            }else{
                return new Object[]{false};
            }
        }
        return new Object[]{true};
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
        MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
    }

    public String getComponentName() {
        return "energy_controller";
    }


    @Override
    public double getOfferedEnergy() {
        return Math.min(energyOutputBuffer, getDemandedEnergy());
    }

    @Override
    public void drawEnergy(double amount) {
        energyOutputBuffer -= amount;
    }

    @Override
    public int getSourceTier() {
        return 0;
    }

    @Override
    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        ForgeDirection direction2 = ForgeDirection.getOrientation(metadata);
        return direction == direction2;
    }

    @Override
    public double getDemandedEnergy() {
        return 128;
    }

    @Override
    public int getSinkTier() {
        return 2;
    }

    @Override
    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        if(energyInputBuffer < inputBufferCapacity){
            energyInputBuffer += amount;
            if(energyInputBuffer > inputBufferCapacity)
                energyInputBuffer = inputBufferCapacity;
            return 0;
        }else{
            return amount;
        }
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        ForgeDirection direction2 = ForgeDirection.getOrientation(metadata);
        return direction != direction2;
    }
}
