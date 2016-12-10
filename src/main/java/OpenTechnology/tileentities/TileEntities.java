package OpenTechnology.tileentities;

import OpenTechnology.Config;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Avaja on 10.12.2016.
 */
public class TileEntities {

    public static void init(){
        if(Config.registerCreativeChatBox)
            GameRegistry.registerTileEntity(TileEntityCreativeChatBox.class, "TileEntityCreativeChatBox");

        if(Config.registerChatBox)
            GameRegistry.registerTileEntity(TileEntityChatBox.class, "TileEntityChatBox");

        if(Config.registerRadar)
            GameRegistry.registerTileEntity(TileEntityRadar.class, "TileEntityRadar");

        if(Config.registerLDA)
            GameRegistry.registerTileEntity(TileEntityLDA.class, "TileEntityLDA");

        if(Config.registerDecorativeCable)
            GameRegistry.registerTileEntity(TileEntityCableDecor.class, "TileEntityCableDecor");
    }
}
