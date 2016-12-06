package OpenTechnology.blocks;

import OpenTechnology.Config;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

/**
 * Created by Avaja on 05.05.2016.
 */
public class Blocks {
    public static Block adminchatbox, chatbox, radar;

    public static void init(){
        adminchatbox = new BlockAdminChatBox();
        chatbox = new BlockChatBox();
        radar = new BlockRadar();

        if (Config.registerAdminChatBox){
            GameRegistry.registerBlock(adminchatbox, "OT_AdminChatBox");
        }else {
            System.out.println("AdminChatBox off.");
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
        //GameRegistry.registerBlock(digitizer, "OT_Digitizer");
    }
}
