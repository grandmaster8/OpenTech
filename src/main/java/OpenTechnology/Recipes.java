package OpenTechnology;

import OpenTechnology.blocks.Blocks;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Avaja on 26.05.2016.
 */
public class Recipes {

    public static void init(){
        addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.chatbox)), "www", "pcp", "www", 'c', new ItemStack(Item.getItemFromBlock(net.minecraft.init.Blocks.gold_block)), 'p', new ItemStack(Items.ender_pearl), 'w', new ItemStack(Items.iron_ingot));
    }

    public static void addRecipe(ItemStack out, Object... stacks){
        GameRegistry.addRecipe(out, stacks);
    }
}
