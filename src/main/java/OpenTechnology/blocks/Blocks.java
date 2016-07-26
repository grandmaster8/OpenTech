package OpenTechnology.blocks;

import OpenTechnology.Config;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

/**
 * Created by Avaja on 05.05.2016.
 */
public class Blocks {
    public static Block teleporter, adminchatbox, chatbox, inventorybinder, digitizer;

    public static void init(){
        teleporter = new BlockTeleporter();
        adminchatbox = new BlockAdminChatBox();
        chatbox = new BlockChatBox();
        inventorybinder = new BlockPlayerInventoryBinder();
        digitizer = new BlockDigitizer();

        if (Config.registerTeleporter){
            GameRegistry.registerBlock(teleporter, "OT_Teleporter");
        }else{
            System.out.println("Teleporter off.");
        }

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

        if (Config.registerPlayerInventoryBinder){
            GameRegistry.registerBlock(inventorybinder, "OT_PlayerInventoryBinder");
        }else{
            System.out.println("PlayerInventoryBinder");
        }
        //GameRegistry.registerBlock(digitizer, "OT_Digitizer");
    }
}
