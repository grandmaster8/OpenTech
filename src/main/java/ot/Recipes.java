package ot;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IC2Items;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ot.blocks.Blocks;
import ot.recipes.RecipeItemCableDecor;

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

        if(Config.registerTeslaUpgrade){
            addRecipe(new ItemStack(ot.item.Items.tesla),
                    "ccc",
                    "pbp",
                    "ccc",
                    'c', IC2Items.getItem("ironCableItem"),
                    'p', li.cil.oc.api.Items.get("chip3").createItemStack(1),
                    'b', IC2Items.getItem("hvTransformer")
            );
        }

        if(Config.registerNR){
            ItemStack vent = IC2Items.getItem("reactorVentDiamond");
            vent.setItemDamage(1);
            ItemStack lead = IC2Items.getItem("leadBlock");
            lead.setItemDamage(4);
            ItemStack reflector = IC2Items.getItem("reactorReflectorThick");
            reflector.setItemDamage(1);
            addRecipe(new ItemStack(ot.item.Items.nr),
                    "vvv",
                    "oro",
                    "lcl",
                    'v', vent,
                    'r', IC2Items.getItem("nuclearReactor"),
                    'o', reflector,
                    'l', lead,
                    'c', li.cil.oc.api.Items.get("cpu3").createItemStack(1)
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

        if(Config.registerTeslaUpgrade){
            addRecipe(new ItemStack(ot.item.Items.tesla),
                    "ccc",
                    "pbp",
                    "ccc",
                    'c', li.cil.oc.api.Items.get("cable").createItemStack(1),
                    'p', li.cil.oc.api.Items.get("chip3").createItemStack(1),
                    'b', li.cil.oc.api.Items.get("batteryUpgrade3").createItemStack(1)
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

        addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.antennaCell)),
                    "www",
                "ccc",
                "mbm",
                'w', li.cil.oc.api.Items.get("wlanCard").createItemStack(1),
                'c', li.cil.oc.api.Items.get("cpu3").createItemStack(1),
                'm', li.cil.oc.api.Items.get("ram6").createItemStack(1),
                'b', li.cil.oc.api.Items.get("componentBus3").createItemStack(1)
                );

       if(Config.registerDecorativeCable){
           GameRegistry.addRecipe(new RecipeItemCableDecor());
       }

        if(Config.registerRadarUpgrade){
            addRecipe(new ItemStack(ot.item.Items.radar),
                    "odo",
                    "mcm",
                    "xxx",
                    'd', li.cil.oc.api.Items.get("motionSensor").createItemStack(1),
                    'm', li.cil.oc.api.Items.get("ram3").createItemStack(1),
                    'c', li.cil.oc.api.Items.get("cpu2").createItemStack(1)
            );
        }

       if(Config.registerRadar){
           addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.radar)),
                   "idi",
                   "mcm",
                   "iii",
                   'd', li.cil.oc.api.Items.get("motionSensor").createItemStack(1),
                   'm', li.cil.oc.api.Items.get("ram3").createItemStack(1),
                   'c', li.cil.oc.api.Items.get("cpu2").createItemStack(1),
                   'i', new ItemStack(Items.iron_ingot)
           );
       }

       if(Config.registerScanner){
           addRecipe(new ItemStack(ot.item.Items.scanner),
                   "cbc",
                   "iui",
                   "cbc",
                   'c', li.cil.oc.api.Items.get("chip2").createItemStack(1),
                   'b', li.cil.oc.api.Items.get("batteryUpgrade3").createItemStack(1),
                   'u', li.cil.oc.api.Items.get("cpu2").createItemStack(1),
                   'i', Items.iron_ingot
           );
       }
    }

    private  static void addRecipe(ItemStack out, Object... stacks){
        GameRegistry.addRecipe(out, stacks);
    }
}
