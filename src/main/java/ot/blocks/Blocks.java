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
        if (Config.registerCreativeChatBox){
            creativeChatbox = new BlockCreativeChatBox();
            registerBlock(creativeChatbox, "OT_CreativeChatBox");
        }else {
            System.out.println("CreativeChatBox disable");
        }

        if (Config.registerChatBox){
            chatbox = new BlockChatBox();
            registerBlock(chatbox, "OT_ChatBox");
        }else{
            System.out.println("ChatBox disable");
        }

        if (Config.registerRadar){
            radar = new BlockRadar();
            registerBlock(radar, "OT_Radar");
        }else{
            System.out.println("Radar disable");
        }

        if(Config.registerLDA){
            lda = new BlockLDA();
            antenna = new BlockAntenna();
            antennaCell = new BlockCell();
            registerBlock(lda, "OT_LDA");
            registerBlock(antennaCell, "OT_CELL");
            registerBlock(antenna, "OT_ANTENNA");
        }else{
            System.out.println("LDA disable");
        }

        if(Config.registerDecorativeCable){
            cableDecor = new BlockCableDecor();
            GameRegistry.registerBlock(cableDecor, ItemBlockCableDecor.class, "OT_CableDecor");
        }else{
            System.out.println("Decorative Cable disable");
        }

        if(Config.registerPIB){
            pib = new BlockPIBinder();
            registerBlock(pib, "OT_PIB");
        }else{
            System.out.println("pib disable");
        }

        if(Config.registerWorldInterface){
            worldInterface = new BlockWorldInterface();
            registerBlock(worldInterface, "OT_WorldInterface");
        }else{
            System.out.println("world interface disable");
        }

        if(Config.registerPIM) {
            pim = new BlockPIM();
            registerBlock(pim, "OT_PIM");
        }else{
            System.out.println("pim disable");
        }

        if(Loader.isModLoaded("IC2")){
            ic2();
        }
    }

    private static void ic2(){
        if(Config.registerEnergyController){
            energyController = new BlockEnergyController();
            GameRegistry.registerBlock(energyController, "OT_ENERGY_CONTROLLER");
        }else{
            System.out.println("EnergyController disable");
        }
    }

    private static void registerBlock(Block block, String name){
        GameRegistry.registerBlock(block, ItemOTBlock.class, name);
    }
}
