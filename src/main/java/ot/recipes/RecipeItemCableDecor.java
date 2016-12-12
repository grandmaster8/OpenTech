package ot.recipes;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import ot.blocks.Blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avaja on 09.12.2016.
 */
public class RecipeItemCableDecor implements IRecipe {

    private Item chamelium;

    private List<Item> blackList;

    public RecipeItemCableDecor() {
        chamelium = li.cil.oc.api.Items.get("chamelium").item();
        blackList = new ArrayList<Item>();

        blackList.add(Item.getItemFromBlock(li.cil.oc.api.Items.get("cable").block()));
    }

    private boolean isBlackList(Item item){
        return blackList.contains(item);
    }

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        boolean b1 = false, b2 = false;
        for(int slot = 0; slot < inventoryCrafting.getSizeInventory(); slot++){
            if(inventoryCrafting.getStackInSlot(slot) != null){
                Item item = inventoryCrafting.getStackInSlot(slot).getItem();
                if(!b1 && item == chamelium)
                    b1 = true;

                if(b1 && !b2 && item instanceof ItemBlock){
                    ItemBlock itemBlock = (ItemBlock) item;
                    return itemBlock.field_150939_a.isOpaqueCube();
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        boolean b1 = false, b2 = false;
        int chameliumSize = 0;

        for(int slot = 0; slot < inventoryCrafting.getSizeInventory(); slot++){
            if(inventoryCrafting.getStackInSlot(slot) != null){
                Item item = inventoryCrafting.getStackInSlot(slot).getItem();
                if(!b1 && item == chamelium){
                    b1 = true;
                    chameliumSize = inventoryCrafting.getStackInSlot(slot).stackSize;
                }

                if(b1 && !b2 && item instanceof ItemBlock && !isBlackList(item)){
                    ItemBlock itemBlock = (ItemBlock) item;
                    if(itemBlock.field_150939_a.isOpaqueCube())
                        return new ItemStack(Blocks.cableDecor, chameliumSize, Block.getIdFromBlock(itemBlock.field_150939_a));
                }
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
