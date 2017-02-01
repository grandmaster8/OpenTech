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
            GameRegistry.registerTileEntity(TileEntityCreativeChatBox.class, "TileEntityCreativeChatBox");

        if(Config.registerChatBox)
            GameRegistry.registerTileEntity(TileEntityChatBox.class, "TileEntityChatBox");

        if(Config.registerRadar)
            GameRegistry.registerTileEntity(TileEntityRadar.class, "TileEntityRadar");

        if(Config.registerLDA)
            GameRegistry.registerTileEntity(TileEntityLDA.class, "TileEntityLDA");

        if(Config.registerDecorativeCable)
            GameRegistry.registerTileEntity(TileEntityCableDecor.class, "TileEntityCableDecor");

        if(Config.registerPIB)
            GameRegistry.registerTileEntity(TileEntityPIB.class, "TileEntityPIB");
        
        if(Loader.isModLoaded("IC2")){
            ic2();
        }
    }

    private static void ic2(){
        if(Config.registerEnergyController)
            GameRegistry.registerTileEntity(TileEntityEnergyController.class, "TileEntityEnergyController");
    }
}
