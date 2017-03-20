package ot.tileentities;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import ot.utils.Utils;

import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by Avaja on 20.03.2017.
 */
public class TileEntityWorldInterface extends TileEntity implements Analyzable, Environment, SidedComponent {

    protected Node node;
    private boolean addToNetwork = false;

    public TileEntityWorldInterface() {
        node = li.cil.oc.api.Network.newNode(this, Visibility.Network).withComponent(getComponentName()).create();
    }

    @Override
    public void updateEntity() {
        if(!addToNetwork){
            addToNetwork = true;
            li.cil.oc.api.Network.joinOrCreateNetwork(this);
        }
    }

    @Callback
    public Object[] getStackInSlot(Context context, Arguments arguments) throws Exception{
        String name = arguments.checkString(1);
        int slot = arguments.checkInteger(2) + 1;
        Iterator iterator = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
        while (iterator.hasNext())
        {
            EntityPlayerMP player = (EntityPlayerMP)iterator.next();
            if(player.getCommandSenderName().equals(name)){
                if(slot >= 0 && slot < player.inventory.getSizeInventory()){
                    ItemStack itemStack = player.inventory.getStackInSlot(slot);
                    if(itemStack != null){
                        HashMap<Object, Object> values = new HashMap<Object, Object>();
                        values.put("name", Utils.getForgeName(itemStack.getItem()));
                        values.put("damage", itemStack.getItemDamage());
                        values.put("maxDamage", itemStack.getMaxDamage());
                        values.put("size", itemStack.stackSize);
                        values.put("label", itemStack.getItem().getItemStackDisplayName(itemStack));
                        values.put("hasTag", itemStack.hasTagCompound());
                        return new Object[]{values};
                    }else{
                        return new Object[]{null};
                    }
                }else{
                    return new Object[]{false, "invalid slot"};
                }
            }

        }
        return new Object[]{false, "player not found"};
    }

    private String getComponentName() {
        return "world_interface";
    }

    @Override
    public boolean canConnectNode(ForgeDirection side) {
        return false;
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

    // ----------------------------------------------------------------------- //

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (node != null && node.host() == this) {
            node.load(nbt.getCompoundTag("oc:node"));
        }
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (node != null && node.host() == this) {
            final NBTTagCompound nodeNbt = new NBTTagCompound();
            node.save(nodeNbt);
            nbt.setTag("oc:node", nodeNbt);
        }
    }
}
