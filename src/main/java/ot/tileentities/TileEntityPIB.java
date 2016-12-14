package ot.tileentities;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import ot.Config;
import ot.OpenTechnology;

import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Avaja on 10.12.2016.
 */
public class TileEntityPIB extends TileEntity implements Analyzable, Environment {

    private Node node = null;
    private boolean addToNetwork = false;

    private EntityPlayer target = null;
    private String name = null;

    public TileEntityPIB() {
        node = li.cil.oc.api.Network.newNode(this, Visibility.Network).withComponent("pib").withConnector().create();
    }

    @Override
    public void updateEntity() {
        if(!addToNetwork){
            addToNetwork = true;
            li.cil.oc.api.Network.joinOrCreateNetwork(this);
        }
    }

    @Callback(doc="")
    public Object[] isConnected(Context context, Arguments arguments) throws Exception{
        return new Object[]{isConnected()};
    }

    @Callback(doc="")
    public Object[] getTargetName(Context context, Arguments arguments) throws Exception{
        return new Object[]{name};
    }

    @Callback(doc="")
    public Object[] checkBinding(Context context, Arguments arguments) throws Exception{
        return new Object[]{name != null};
    }

    @Callback(doc="function(slot:int):table, get itemstack in player inventory slot.")
    public Object[] getStackInSlot(Context context, Arguments arguments) throws Exception{
        if(!isConnected())
            return new Object[]{false};

        int slot = arguments.checkInteger(0) - 1;
        ItemStack itemStack = target.inventory.getStackInSlot(slot);

       if(itemStack != null){
           Map<String, Object> entry = new HashMap<String, Object>();
           entry.put("name", Item.itemRegistry.getNameForObject(itemStack));
           entry.put("damage", itemStack.getItemDamage());
           entry.put("hasTag", itemStack.hasTagCompound());
           entry.put("size", itemStack.stackSize);
           entry.put("label", itemStack.getDisplayName());
           return new Object[]{entry};
       }
       return new Object[]{null};
    }

    @Callback(doc="function(side:int, pullSlot:int, pushSlot:int, count:int):boolean, pull stack of player inventory and push in target inventory.")
    public Object[] pullStackInSlot(Context context, Arguments arguments) throws Exception{
        if(!isConnected())
            return new Object[]{false};
        int side = arguments.checkInteger(0);
        int pullSlot = arguments.checkInteger(1) - 1;
        int pushSlot = arguments.checkInteger(2) - 1;
        int count = arguments.checkInteger(3);

        if(pullSlot < 0)
            return new Object[]{false, "pull slot < 0"};
        if(pullSlot > target.inventory.getSizeInventory())
            return new Object[]{false, "pull slot > target inventory size."};
        if(pushSlot < 0)
            return new Object[]{false, "push slot < 0"};
        if(count <= 0)
            return new Object[]{false, "count <= 0"};
        if(target.inventory.getStackInSlot(pullSlot) == null)
            return new Object[]{false, "pull stack equals nil"};

        ForgeDirection direction = ForgeDirection.getOrientation(side);
        TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
        if(tileEntity != null && tileEntity instanceof IInventory){
            IInventory inventory = (IInventory) tileEntity;
            if(pushSlot > inventory.getSizeInventory())
                return new Object[]{false};

            ItemStack pullStack = target.inventory.getStackInSlot(pullSlot);
            ItemStack pushStack = inventory.getStackInSlot(pushSlot);
            if(count > pullStack.stackSize)
                count = pullStack.stackSize;

            if(pushStack != null){
                if(pushStack.isItemEqual(pullStack)){
                    if(pushStack.getMaxStackSize() - pushStack.stackSize >= count && useEnergy(count)){
                        pushStack.stackSize += count;
                        target.inventory.setInventorySlotContents(pullSlot, null);
                    }else if(useEnergy(count)){
                        pullStack.stackSize -= pushStack.getMaxStackSize() - pushStack.stackSize;
                        pushStack.stackSize = pushStack.getMaxStackSize();
                    }
                    return new Object[]{true};
                }
            }else{
                if(pullStack.stackSize > count && useEnergy(count)){
                    ItemStack push = pullStack.copy();
                    pullStack.stackSize -= count;
                    push.stackSize = count;
                    inventory.setInventorySlotContents(pushSlot, push);
                }else if(useEnergy(count)){
                    target.inventory.setInventorySlotContents(pullSlot, null);
                    inventory.setInventorySlotContents(pushSlot, pullStack);
                }
                return new Object[]{true};
            }
        }
        return new Object[]{false};
    }

    @Callback(doc="function(side:int, pullSlot:int, pushSlot:int, count:int):boolean, pull stack of target inventory and push in player inventory.")
    public Object[] pushStackInSlot(Context context, Arguments arguments) throws Exception{
        if(!isConnected())
            return new Object[]{false};
        int side = arguments.checkInteger(0);
        int pullSlot = arguments.checkInteger(1) - 1;
        int pushSlot = arguments.checkInteger(2) - 1;
        int count = arguments.checkInteger(3);

        if(pushSlot < 0)
            return new Object[]{false, "push slot < 0"};
        if(pushSlot > target.inventory.getSizeInventory())
            return new Object[]{false, "push slot > target inventory size"};
        if(pullSlot < 0)
            return new Object[]{false, "pull slot < 0"};
        if(count <= 0)
            return new Object[]{false, "count <= 0"};

        ForgeDirection direction = ForgeDirection.getOrientation(side);
        TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
        if(tileEntity != null && tileEntity instanceof IInventory){
            IInventory inventory = (IInventory) tileEntity;
            if(pullSlot > inventory.getSizeInventory())
                return new Object[]{false, "pull slot > target inventory size"};

            if(inventory.getStackInSlot(pullSlot) == null)
                return new Object[]{false};

            ItemStack pushStack = target.inventory.getStackInSlot(pushSlot);
            ItemStack pullStack = inventory.getStackInSlot(pullSlot);
            if(count > pullStack.stackSize)
                count = pullStack.stackSize;

            if(pushStack != null){
                if(pushStack.isItemEqual(pullStack)){
                    if(pushStack.getMaxStackSize() - pushStack.stackSize >= count && useEnergy(count)){
                        pullStack.stackSize -= count;
                        pushStack.stackSize += count;
                        target.inventory.setInventorySlotContents(pullSlot, null);
                    }else if(useEnergy(count)){
                        pullStack.stackSize -= pushStack.getMaxStackSize() - pushStack.stackSize;
                        pushStack.stackSize = pushStack.getMaxStackSize();
                    }
                    if(pullStack.stackSize <= 0)
                        inventory.setInventorySlotContents(pullSlot, null);

                    return new Object[]{true};
                }
            }else{
                if(pullStack.stackSize > count && useEnergy(count)){
                    ItemStack push = pullStack.copy();
                    pullStack.stackSize -= count;
                    push.stackSize = count;
                    target.inventory.setInventorySlotContents(pushSlot, push);
                }else if(useEnergy(count)){
                    target.inventory.setInventorySlotContents(pullSlot, null);
                    inventory.setInventorySlotContents(pushSlot, pullStack);
                }
                return new Object[]{true};
            }
        }
        return new Object[]{false};
    }

    private boolean useEnergy(int itemCount){
       try{
           Connector connector = (Connector) node();
           double count = 0;
           double distance = calcDistance();
           try {
               OpenTechnology.SCRIPT_ENGINE.put("distance", distance);
               OpenTechnology.SCRIPT_ENGINE.put("itemCount", itemCount);
               count = (Double) OpenTechnology.SCRIPT_ENGINE.eval(Config.pibUsingEnergy);
           } catch (ScriptException e) {
               e.printStackTrace();
           }

           if(target.worldObj.provider.dimensionId != worldObj.provider.dimensionId)
               count += Config.pibUsingEnergyDimension;

           return connector.tryChangeBuffer(-count);
       }catch (Exception e){
           e.printStackTrace();
       }
       return false;
    }

    private double calcDistance(){
        if(isConnected()){
            return target.getDistance(xCoord, yCoord, zCoord);
        }
        return 0;
    }

    @Override
    public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return new Node[]{node};
    }

    public boolean isConnected(){
        return target != null;
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
    public void onChunkUnload() {
        if (node != null) node.remove();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if(nbt.hasKey("target")){
            name = nbt.getString("target");
            checkPlayer();
        }
        if (node != null && node.host() == this) {
            node.load(nbt.getCompoundTag("oc:node"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if(target != null){
            nbt.setString("target", target.getCommandSenderName());
        }
        if (node != null && node.host() == this) {
            final NBTTagCompound nodeNbt = new NBTTagCompound();
            node.save(nodeNbt);
            nbt.setTag("oc:node", nodeNbt);
        }
    }

    public void playerClicked(EntityPlayer entityPlayer) {
        if(target == null){
            target = entityPlayer;
            node.sendToReachable("computer.signal", "player_binding", node.address(), entityPlayer.getCommandSenderName());
        }
        else{
            if(entityPlayer == target){
                target = null;
                node.sendToReachable("computer.signal", "player_unbinding", node.address(), entityPlayer.getCommandSenderName());
            }
        }
    }

    private void checkPlayer(){
        if(name != null && !name.isEmpty()){
            ServerConfigurationManager configurationManager = MinecraftServer.getServer().getConfigurationManager();
            EntityPlayer player = configurationManager.func_152612_a(name);
            if(player != null)
                target = player;
        }
    }

    public void checkPlayer(EntityPlayer entityPlayer){
        if(entityPlayer != null){
            if(entityPlayer.getCommandSenderName().equals(name))
                target = entityPlayer;
        }
    }
}
