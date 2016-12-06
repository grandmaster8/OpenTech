package OpenTechnology.proxy;

import OpenTechnology.Recipes;
import OpenTechnology.blocks.Blocks;
import OpenTechnology.driver.Drivers;
import OpenTechnology.events.CommonEvents;
import OpenTechnology.item.Items;
import OpenTechnology.tileentities.TileEntityAdminChatBox;
import OpenTechnology.tileentities.TileEntityChatBox;
import OpenTechnology.tileentities.TileEntityRadar;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    public static SimpleNetworkWrapper wrapper;

	public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new CommonEvents());

        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("OpenTechnology");

        Blocks.init();
        Items.init();
        Drivers.init();
        Recipes.init();

        GameRegistry.registerTileEntity(TileEntityAdminChatBox.class, "TileEntityAdminChatBox");
        GameRegistry.registerTileEntity(TileEntityChatBox.class, "TileEntityChatBox");
        GameRegistry.registerTileEntity(TileEntityRadar.class, "TileEntityRadar");


    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
