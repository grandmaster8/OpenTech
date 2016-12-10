package OpenTechnology;

import OpenTechnology.blocks.Blocks;
import OpenTechnology.recipes.RecipeItemCableDecor;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IC2Items;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Avaja on 26.05.2016.
 */
public class Recipes {

    public static void init(){
        common();
        if(Loader.isModLoaded("IC2")){
            ic2();
        }else{
            vanilla();
        }
    }

    public static void ic2(){
        if(Config.registerLDA){
            addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.antenna)),
                    "ici",
                    "ici",
                    "ici",
                    'i', IC2Items.getItem("denseplateadviron"),
                    'c', li.cil.oc.api.Items.get("cable").createItemStack(1)
            );
        }
    }

    public static void vanilla(){
        if(Config.registerLDA){
            addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.antenna)),
                    "ici",
                    "ici",
                    "ici",
                    'i', new ItemStack(Item.getItemFromBlock(net.minecraft.init.Blocks.iron_block)),
                    'c', li.cil.oc.api.Items.get("cable").createItemStack(1)
            );
        }
    }

    public static void common(){
        if(Config.registerChatBox)
            addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.chatbox)), "www", "pcp", "www", 'c', new ItemStack(Item.getItemFromBlock(net.minecraft.init.Blocks.gold_block)), 'p', new ItemStack(Items.ender_pearl), 'w', new ItemStack(Items.iron_ingot));

        if(Config.registerLDA){
            addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.lda)),
                    "ici",
                    "mbm",
                    "ipi",

                    'i', new ItemStack(Item.getItemFromBlock(net.minecraft.init.Blocks.iron_block)),
                    'c', li.cil.oc.api.Items.get("cable").createItemStack(1) ,
                    'b', li.cil.oc.api.Items.get("componentBus3").createItemStack(1),
                    'm', li.cil.oc.api.Items.get("ram6").createItemStack(1),
                    'p', li.cil.oc.api.Items.get("cpu3").createItemStack(1)
            );
        }

        /*addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.antennaCell)),
                    "",
                "",
                "",
                 );*/

       if(Config.registerDecorativeCable){
           GameRegistry.addRecipe(new RecipeItemCableDecor());
       }

    }

    public static void addRecipe(ItemStack out, Object... stacks){
        GameRegistry.addRecipe(out, stacks);
    }
}
