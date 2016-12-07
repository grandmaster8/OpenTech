package OpenTechnology.blocks;

import OpenTechnology.Config;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

/**
 * Created by Avaja on 05.05.2016.
 */
public class Blocks {
    public static Block creativeChatbox, chatbox, radar, lda;

    public static void init(){
        creativeChatbox = new BlockCreativeChatBox();
        chatbox = new BlockChatBox();
        radar = new BlockRadar();
        lda = new BlockLDA();

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
        }else{
            System.out.println("LDA off.");
        }
    }
}
