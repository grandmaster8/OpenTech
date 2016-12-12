package ot.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import ot.Config;
import ot.blocks.antenna.BlockAntenna;
import ot.blocks.antenna.BlockCell;
import ot.blocks.antenna.BlockLDA;
import ot.item.ItemBlockCableDecor;

/**
 * Created by Avaja on 05.05.2016.
 */
public class Blocks {
    public static Block creativeChatbox, chatbox, radar, lda, antennaCell, antenna, pib;
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

        if (Config.registerCreativeChatBox){
            GameRegistry.registerBlock(creativeChatbox, "OT_AdminChatBox");
        }else {
            System.out.println("CreativeChatBox off.");
        }

        if (Config.registerChatBox){
            GameRegistry.registerBlock(chatbox, "OT_ChatBox");
        }else{
            System.out.println("ChatBox off.");
        }

        if (Config.registerRadar){
            GameRegistry.registerBlock(radar, "OT_Radar");
        }else{
            System.out.println("Radar off.");
        }

        if(Config.registerLDA){
            GameRegistry.registerBlock(lda, "OT_LDA");
            GameRegistry.registerBlock(antennaCell, "OT_CELL");
            GameRegistry.registerBlock(antenna, "OT_ANTENNA");
        }else{
            System.out.println("LDA off.");
        }

        if(Config.registerDecorativeCable){
            GameRegistry.registerBlock(cableDecor, ItemBlockCableDecor.class, "OT_CableDecor");
        }else{
            System.out.println("Decorative Cable off.");
        }

        if(Config.registerPIB){
            GameRegistry.registerBlock(pib, "OT_PIB");
        }
    }
}
