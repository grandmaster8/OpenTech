package OpenTechnology.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

/**
 * Created by Avaja on 05.05.2016.
 */
public class Blocks {
    public static Block teleporter, chatboxadmin, chatbox, inventorybinder;

    public static void init(){
        teleporter = new BlockTeleporter();
        chatboxadmin = new BlockAdminChatBox();
        chatbox = new BlockChatBox();
        inventorybinder = new BlockPlayerInventoryBinder();

        GameRegistry.registerBlock(teleporter, "OT_Teleporter");
        GameRegistry.registerBlock(chatboxadmin, "OT_ChatBoxAdmin");
        GameRegistry.registerBlock(chatbox, "OT_ChatBox");
        GameRegistry.registerBlock(inventorybinder, "OT_PlayerInventoryBinder");
    }
}
