package ot.blocks;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import ot.Config;
import ot.blocks.antenna.BlockAntenna;
import ot.blocks.antenna.BlockCell;
import ot.blocks.antenna.BlockLDA;
import ot.blocks.ic.BlockEnergyController;
import ot.item.ItemBlockCableDecor;
import ot.item.ItemOTBlock;

/**
 * Created by Avaja on 05.05.2016.
 */
public class Blocks {
    public static Block creativeChatbox, chatbox, radar, lda, antennaCell, antenna, pib, energyController, worldInterface, pim;
    public static BlockCableDecor cableDecor;

    public static void init(){
        creativeChatbox = new BlockCreativeChatBox();
        chatbox = new BlockChatBox();
        radar = new BlockRadar();
        lda = new BlockLDA();
        antennaCell = new BlockCell();
        antenna = new BlockAntenna();
        pib = new BlockPIBinder();
        cableDecor = new BlockCableDecor();
        energyController = new BlockEnergyController();
        worldInterface = new BlockWorldInterface();
        pim = new BlockPIM();

        if (Config.registerCreativeChatBox){
            registerBlock(creativeChatbox, "OT_CreativeChatBox");
        }else {
            System.out.println("CreativeChatBox disable");
        }

        if (Config.registerChatBox){
            registerBlock(chatbox, "OT_ChatBox");
        }else{
            System.out.println("ChatBox disable");
        }

        if (Config.registerRadar){
            registerBlock(radar, "OT_Radar");
        }else{
            System.out.println("Radar disable");
        }

        if(Config.registerLDA){
            registerBlock(lda, "OT_LDA");
            registerBlock(antennaCell, "OT_CELL");
            registerBlock(antenna, "OT_ANTENNA");
        }else{
            System.out.println("LDA disable");
        }

        if(Config.registerDecorativeCable){
            GameRegistry.registerBlock(cableDecor, ItemBlockCableDecor.class, "OT_CableDecor");
        }else{
            System.out.println("Decorative Cable disable");
        }

        if(Config.registerPIB){
            registerBlock(pib, "OT_PIB");
        }else{
            System.out.println("pib disable");
        }

        if(Config.registerWorldInterface){
            registerBlock(worldInterface, "OT_WorldInterface");
        }else{
            System.out.println("world interface disable");
        }

        if(Config.registerPIM)
            registerBlock(pim, "OT_PIM");

        if(Loader.isModLoaded("IC2")){
            ic2();
        }
    }

    private static void ic2(){
        if(Config.registerEnergyController){
            GameRegistry.registerBlock(energyController, "OT_ENERGY_CONTROLLER");
        }else{
            System.out.println("EnergyController disable");
        }
    }

    private static void registerBlock(Block block, String name){
        GameRegistry.registerBlock(block, ItemOTBlock.class, name);
    }
}
