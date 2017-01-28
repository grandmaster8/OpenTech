package ot.tileentities;

import li.cil.oc.api.Network;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Avaja on 27.01.2017.
 */
public class TileEntityEnergyController extends TileEntity implements Analyzable, Environment {

    protected Node node;
    private boolean addToNetwork = false;

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
}
