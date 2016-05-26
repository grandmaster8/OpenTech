package OpenTechnology.tileentities;

import li.cil.oc.api.network.Environment;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Avaja on 26.05.2016.
 */
public class BasicTileEnvironment extends TileEntity implements Environment {
    protected Node node;
    private boolean addToNetwork = false;

    protected void setNode(Node node){
        this.node = node;
    }

    @Override
    public void updateEntity() {
        if (!addToNetwork){
            addToNetwork = true;
            li.cil.oc.api.Network.joinOrCreateNetwork(this);
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

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (node != null && node.host() == this) {
            node.load(compound.getCompoundTag("oc:node"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
            if (node != null && node.host() == this) {
                final NBTTagCompound nodeNbt = new NBTTagCompound();
                node.save(nodeNbt);
                compound.setTag("oc:node", nodeNbt);
            }
    }
}
