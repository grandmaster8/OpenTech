package OpenTechnology.tileentities;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Avaja on 28.05.2016.
 */
public class TileEntityDigitizer extends TileEntity implements Environment, SimpleComponent, Analyzable, IInventory {

    private boolean addToNetwork = false;

    private ItemStack stack;
    protected Node node;

    public TileEntityDigitizer() {
        node = li.cil.oc.api.Network.newNode(this, Visibility.Network).withComponent(getComponentName()).withConnector().create();
    }

    @Override
    public void updateEntity() {
        if(!addToNetwork){
            addToNetwork = true;
            li.cil.oc.api.Network.joinOrCreateNetwork(this);
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if (node != null) node.remove();
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        // Make sure to remove the node from its network when its environment,
        // meaning this tile entity, gets unloaded.
        if (node != null) node.remove();
    }

    @Override
    public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return new Node[]{node};
    }

    @Callback
    public Object[] get(Context context, Arguments arguments) throws Exception{
        return new Object[]{};
    }

    @Override
    public String getComponentName() {
        return "digitizer";
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if(slot == 0) return stack;
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        if (stack != null)
            return stack.splitStack(count);
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if(slot == 0) this.stack = stack;
    }

    @Override
    public String getInventoryName() {
        return "digitizer";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
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
