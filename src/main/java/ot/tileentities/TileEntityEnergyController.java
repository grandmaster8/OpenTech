package ot.tileentities;

import ic2.api.energy.tile.IEnergyTile;
import ic2.api.tile.IEnergyStorage;
import li.cil.oc.api.Network;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Avaja on 27.01.2017.
 */
public class TileEntityEnergyController extends TileEntity implements Analyzable, Environment, IEnergyTile, IEnergyStorage {

    protected Node node;
    private boolean addToNetwork = false;
    private int energy = 0;

    public TileEntityEnergyController() {
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).create();
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
    }

    public String getComponentName() {
        return "energy_controller";
    }

    @Override
    public int getStored() {
        return energy;
    }

    @Override
    public void setStored(int energy) {
        this.energy = energy;
    }

    @Override
    public int addEnergy(int amount) {
        if(getCapacity() - getStored() >= amount){
            energy += amount;
            return 0;
        }else{
            int delta = getCapacity() - getStored() - amount;
            energy = getCapacity();
            return delta;
        }
    }

    @Override
    public int getCapacity() {
        return 100000;
    }

    @Override
    public int getOutput() {
        return 32;
    }

    @Override
    public double getOutputEnergyUnitsPerTick() {
        return 32 / 20;
    }

    @Override
    public boolean isTeleporterCompatible(ForgeDirection side) {
        return false;
    }
}
