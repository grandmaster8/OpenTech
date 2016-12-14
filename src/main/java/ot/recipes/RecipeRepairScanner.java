package ot.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import ot.item.Items;

/**
 * Created by Avaja on 14.12.2016.
 */
public class RecipeRepairScanner implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        boolean b1 = false;
        for(int i = 0; i < inventoryCrafting.getSizeInventory(); i++){
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if(!b1 && itemStack != null && itemStack.getItem() == Items.scanner && itemStack.getItemDamage() > 0){
                b1 = true;
            }
            if(b1 && itemStack != null && itemStack.getItem().equals(net.minecraft.init.Items.gold_nugget)){
                return true;
            }

            if(b1 && itemStack != null && itemStack.getItem().equals(net.minecraft.init.Items.gold_ingot)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        boolean b1 = false;
        ItemStack scanner = null;
        for(int i = 0; i < inventoryCrafting.getSizeInventory(); i++){
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if(!b1 && itemStack != null && itemStack.getItem() == Items.scanner){
                b1 = true;
                scanner = itemStack;
            }
            if(b1 && itemStack != null && itemStack.getItem().equals(net.minecraft.init.Items.gold_nugget)){
                ItemStack copy = scanner.copy();
                copy.setItemDamage(scanner.getItemDamage() - 1);
                return copy;
            }

            if(b1 && itemStack != null && itemStack.getItem().equals(net.minecraft.init.Items.gold_ingot)){
                ItemStack copy = scanner.copy();
                if(scanner.getItemDamage() >= 9){
                    copy.setItemDamage(scanner.getItemDamage() - 9);
                }else{
                    copy.setItemDamage(0);
                }
                return copy;
            }
        }
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 1;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
