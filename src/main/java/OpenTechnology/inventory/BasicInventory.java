package OpenTechnology.inventory;

import OpenTechnology.utils.InventoryUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Avaja on 11.05.2016.
 */
public abstract class BasicInventory implements IInventory {
    protected ItemStack[] inventory;
    private String inventoryName;

    public BasicInventory(String name, int size) {
        inventory = new ItemStack[size];
        inventoryName = name;
    }

    @Override
    public String getInventoryName() {
        return inventoryName;
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
    public void markDirty() {

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
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return false;
    }


    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot >= 0 && slot < inventory.length){
            inventory[slot] = stack;
        }
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot >= 0 && slot < inventory.length){
            return inventory[slot];
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        if (slot >= 0 && slot < inventory.length){
            ItemStack ret = inventory[slot].splitStack(count);
            if (inventory[slot].stackSize <= 0)
                inventory[slot] = null;
            return ret;
        }
        return null;
    }

    public void save(NBTTagCompound compound){
        try {
            InventoryUtils.writeItemStacks("inventory", compound, inventory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(NBTTagCompound compound, int size){
        try {
            inventory = InventoryUtils.readItemStacks("inventory", compound);
            if (inventory == null || inventory.length < size)
                inventory = new ItemStack[size];
        } catch (Exception e) {
            inventory = new ItemStack[size];
            e.printStackTrace();
        }
    }
}
