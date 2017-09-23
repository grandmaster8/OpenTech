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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import ot.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class TileEntityPIM extends TileEntity implements Analyzable, Environment, SidedEnvironment,IInventory {

    private Node node = null;
    private boolean addToNetwork = false;
    private EntityPlayer player;

    public TileEntityPIM() {
        node = li.cil.oc.api.Network.newNode(this, Visibility.Network).withComponent("pib").withConnector().create();
    }

    @Override
    public void updateEntity() {
        if(!addToNetwork){
            addToNetwork = true;
            li.cil.oc.api.Network.joinOrCreateNetwork(this);
        }
        if (!worldObj.isRemote) {
            if(!checkPlayer()){
                setPlayer(null);
            }
        }
    }

    private boolean isPlayerValid(EntityPlayer player) {
        if (player == null) return false;
        int playerX = MathHelper.floor_double(player.posX);
        int playerY = MathHelper.floor_double(player.posY + 0.5D);
        int playerZ = MathHelper.floor_double(player.posZ);
        return playerX >= xCoord && playerX <= (xCoord + 1) && playerY == yCoord && playerZ >= zCoord && playerZ <= (zCoord + 1);
    }

    private boolean checkPlayer(){
        return isPlayerValid(player) && Utils.findPlayer(player.getDisplayName()) != null;
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

    private boolean useEnergy(int count){
        try{
            Connector connector = (Connector) node();
            return connector.tryChangeBuffer(count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Callback(doc="function(side:int, pullSlot:int, pushSlot:int, count:int):boolean, pull stack of player inventory and push in target inventory.")
    public Object[] pullStackInSlot(Context context, Arguments arguments) throws Exception{
        if(checkPlayer())
            return new Object[]{false};
        int side = arguments.checkInteger(0);
        int pullSlot = arguments.checkInteger(1) - 1;
        int pushSlot = arguments.checkInteger(2) - 1;
        int count = arguments.checkInteger(3);

        if(pullSlot < 0)
            return new Object[]{false, "pull slot < 0"};
        if(pullSlot > player.inventory.getSizeInventory())
            return new Object[]{false, "pull slot > target inventory size"};
        if(pushSlot < 0)
            return new Object[]{false, "push slot < 0"};
        if(count <= 0)
            return new Object[]{false, "count <= 0"};
        if(player.inventory.getStackInSlot(pullSlot) == null)
            return new Object[]{false, "pull stack equals nil"};

        ForgeDirection direction = ForgeDirection.getOrientation(side);
        TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
        if(tileEntity != null && tileEntity instanceof IInventory){
            IInventory inventory = (IInventory) tileEntity;
            if(pushSlot > inventory.getSizeInventory())
                return new Object[]{false};

            ItemStack pullStack = player.inventory.getStackInSlot(pullSlot);
            ItemStack pushStack = inventory.getStackInSlot(pushSlot);
            if(count > pullStack.stackSize)
                count = pullStack.stackSize;

            if(pushStack != null){
                if(pushStack.isItemEqual(pullStack)){
                    if(pushStack.getMaxStackSize() - pushStack.stackSize >= count && useEnergy(count)){
                        pushStack.stackSize += count;
                        player.inventory.setInventorySlotContents(pullSlot, null);
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
                    player.inventory.setInventorySlotContents(pullSlot, null);
                    inventory.setInventorySlotContents(pushSlot, pullStack);
                }
                return new Object[]{true};
            }
        }
        return new Object[]{false};
    }

    @Callback(doc="function(slot:int):table, get itemstack in player inventory slot.")
    public Object[] getStackInSlot(Context context, Arguments arguments) throws Exception{
        if(checkPlayer())
            return new Object[]{false};

        int slot = arguments.checkInteger(0) - 1;
        ItemStack itemStack = player.inventory.getStackInSlot(slot);

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

    @Callback
    public Object[] getAllStacks(Context context, Arguments arguments) throws Exception {
        //if(checkPlayer())
        return new Object[]{};
    }

    @Callback(doc="function(side:int, pullSlot:int, pushSlot:int, count:int):boolean, pull stack of target inventory and push in player inventory.")
    public Object[] pushStackInSlot(Context context, Arguments arguments) throws Exception{
        if(checkPlayer())
            return new Object[]{false};
        int side = arguments.checkInteger(0);
        int pullSlot = arguments.checkInteger(1) - 1;
        int pushSlot = arguments.checkInteger(2) - 1;
        int count = arguments.checkInteger(3);

        if(pushSlot < 0)
            return new Object[]{false, "push slot < 0"};
        if(pushSlot > player.inventory.getSizeInventory())
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

            ItemStack pushStack = player.inventory.getStackInSlot(pushSlot);
            ItemStack pullStack = inventory.getStackInSlot(pullSlot);
            if(count > pullStack.stackSize)
                count = pullStack.stackSize;

            if(pushStack != null){
                if(pushStack.isItemEqual(pullStack)){
                    if(pushStack.getMaxStackSize() - pushStack.stackSize >= count && useEnergy(count)){
                        pullStack.stackSize -= count;
                        pushStack.stackSize += count;
                        player.inventory.setInventorySlotContents(pullSlot, null);
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
                    player.inventory.setInventorySlotContents(pushSlot, push);
                }else if(useEnergy(count)){
                    player.inventory.setInventorySlotContents(pullSlot, null);
                    inventory.setInventorySlotContents(pushSlot, pullStack);
                }
                return new Object[]{true};
            }
        }
        return new Object[]{false};
    }

    @Override
    public void onChunkUnload() {
        if (node != null) node.remove();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (node != null && node.host() == this) {
            node.load(nbt.getCompoundTag("oc:node"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (node != null && node.host() == this) {
            final NBTTagCompound nodeNbt = new NBTTagCompound();
            node.save(nodeNbt);
            nbt.setTag("oc:node", nodeNbt);
        }
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
        worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.1D, zCoord + 0.5D, "random.click", 0.3F, 0.6F);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, player == null ? 0 : 1, 3);
    }

    public boolean hasPlayer() {
        if (worldObj == null) return false;
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1;
    }

    @Override
    public int getSizeInventory() {
        if(player != null)
            player.inventory.getSizeInventory();
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if(player != null)
            return player.inventory.getStackInSlot(slot);
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        if(player != null)
            return player.inventory.decrStackSize(slot, count);
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if(player != null)
            player.inventory.setInventorySlotContents(slot, stack);
    }

    @Override
    public String getInventoryName() {
        if(player != null)
            return player.inventory.getInventoryName();
        return "pim";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        if(player != null)
            return player.inventory.getInventoryStackLimit();
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public Node sidedNode(ForgeDirection side) {
        if(ForgeDirection.DOWN == side)
            return node();
        return null;
    }

    @Override
    public boolean canConnect(ForgeDirection side) {
        return ForgeDirection.DOWN == side;
    }
}
