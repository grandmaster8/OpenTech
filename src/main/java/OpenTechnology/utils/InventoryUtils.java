package OpenTechnology.utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Created by NEO on 21.02.2016.
 */
public class InventoryUtils {

    public static void writeItemStacks(String name, NBTTagCompound compound, ItemStack[] stacks){
        if(compound != null && stacks != null){
            NBTTagList list = new NBTTagList();
            for(int i = 0; i < stacks.length; i++){
                NBTTagCompound tagCompound = new NBTTagCompound();
                if(stacks[i] != null){
                    tagCompound.setInteger("id", Item.getIdFromItem(stacks[i].getItem()));
                    tagCompound.setInteger("stacksize", stacks[i].stackSize);
                    tagCompound.setInteger("damage", stacks[i].getItemDamage());
                    if(stacks[i].getTagCompound() != null){
                        tagCompound.setTag("nbt", stacks[i].getTagCompound());
                    }
                }else{
                    tagCompound.setInteger("id", -1);
                }
                list.appendTag(tagCompound);
            }
            compound.setTag(name, list);
        }
    }

    public static ItemStack[] readItemStacks(String name, NBTTagCompound compound){
        if(compound != null && compound.hasKey(name)){
            NBTTagList list = (NBTTagList) compound.getTag(name);
            ItemStack[] itemStacks = new ItemStack[list.tagCount()];
            for(int i = 0; i < list.tagCount(); i++){
                NBTTagCompound tagCompound = list.getCompoundTagAt(i);
                if(tagCompound != null){
                    int id = tagCompound.getInteger("id");
                    if(id >= 0 && tagCompound.hasKey("stacksize") && tagCompound.hasKey("damage")){
                        itemStacks[i] = new ItemStack(Item.getItemById(id), tagCompound.getInteger("stacksize"), tagCompound.getInteger("damage"));
                        if(tagCompound.hasKey("nbt")){
                            itemStacks[i].setTagCompound(tagCompound.getCompoundTag("nbt"));
                        }
                    }
                }
            }
            return itemStacks;
        }
        return null;
    }

    public static void rewriteItemStack(String name, NBTTagCompound compound, int stack, ItemStack itemStack){
        if(compound != null && compound.hasKey(name)){
            NBTTagList list = (NBTTagList) compound.getTag(name);
            NBTTagCompound tagCompound = list.getCompoundTagAt(stack);
            if (tagCompound != null){
                if(itemStack != null){
                    tagCompound.setInteger("id", Item.getIdFromItem(itemStack.getItem()));
                    tagCompound.setInteger("stacksize", itemStack.stackSize);
                    tagCompound.setInteger("damage", itemStack.getItemDamage());
                    if(itemStack.getTagCompound() != null){
                        tagCompound.setTag("nbt", itemStack.getTagCompound());
                    }
                }else{
                    tagCompound.setInteger("id", -1);
                }
            }
        }
    }

    public static ItemStack checkItemStack(ItemStack stack){
        if(stack != null){
            if(stack.stackSize <= 0){
                return null;
            }else if(stack.getItem() == null){
                return null;
            }else{
                return stack;
            }
        }else{
            return null;
        }
    }

    public static boolean addItemStackInInventory(IInventory inventory, ItemStack stack){
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack tmp = inventory.getStackInSlot(i);
            if(tmp != null && stack != null && tmp.isItemEqual(stack) && stack.isStackable() && tmp.isStackable()){
                if(64 - tmp.stackSize >= stack.stackSize){
                    inventory.getStackInSlot(i).stackSize += stack.stackSize;
                    return true;
                }else{
                    stack.stackSize -= (64 - tmp.stackSize);
                    inventory.getStackInSlot(i).stackSize = 64;
                }
            }else if(tmp == null){
                inventory.setInventorySlotContents(i, stack);
                return true;
            }
        }
        return false;
    }

    public static boolean addItemStackInInventory(ItemStack[] istacks, ItemStack stack){
        ItemStack[] stacks = istacks.clone();
        for(int i = 0; i < stacks.length; i++) {
            ItemStack tmp = stacks[i];
            if(tmp != null && stack != null && tmp.isItemEqual(stack) && stack.isStackable() && tmp.isStackable()){
                if(tmp.getMaxStackSize() - tmp.stackSize >= stack.stackSize){
                    stacks[i].stackSize += stack.stackSize;
                    return true;
                }else{
                    stack.stackSize -= (tmp.getMaxStackSize() - tmp.stackSize);
                    stacks[i].stackSize = stacks[i].getMaxStackSize();
                }
            }else if(tmp == null){
                stacks[i] = stack;
                return true;
            }
        }
        return false;
    }
}
