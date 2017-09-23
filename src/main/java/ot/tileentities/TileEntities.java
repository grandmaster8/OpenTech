package ot.tileentities;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ot.Config;
import ot.tileentities.ic.TileEntityEnergyController;

/**
 * Created by Avaja on 10.12.2016.
 */
public class TileEntities {

    public static void init(){
        if(Config.registerCreativeChatBox)
            GameRegistry.registerTileEntity(TileEntityCreativeChatBox.class, "OT_TileEntityCreativeChatBox");

        if(Config.registerChatBox)
            GameRegistry.registerTileEntity(TileEntityChatBox.class, "OT_TileEntityChatBox");

        if(Config.registerRadar)
            GameRegistry.registerTileEntity(TileEntityRadar.class, "OT_TileEntityRadar");

        if(Config.registerLDA)
            GameRegistry.registerTileEntity(TileEntityLDA.class, "OT_TileEntityLDA");

        if(Config.registerDecorativeCable)
            GameRegistry.registerTileEntity(TileEntityCableDecor.class, "OT_TileEntityCableDecor");

        if(Config.registerPIB)
            GameRegistry.registerTileEntity(TileEntityPIB.class, "OT_TileEntityPIB");

        if(Config.registerPIM)
            GameRegistry.registerTileEntity(TileEntityPIM.class, "TileEntityPIM");
        
        if(Loader.isModLoaded("IC2")){
            ic2();
        }

        GameRegistry.registerTileEntity(TileEntityWorldInterface.class, "OT_TileEntityWorldInterface");
    }

    private static void ic2(){
        if(Config.registerEnergyController)
            GameRegistry.registerTileEntity(TileEntityEnergyController.class, "TileEntityEnergyController");
    }
}
