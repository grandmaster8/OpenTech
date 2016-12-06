package OpenTechnology.proxy;

import OpenTechnology.OpenTechnology;
import OpenTechnology.Recipes;
import OpenTechnology.blocks.Blocks;
import OpenTechnology.driver.Drivers;
import OpenTechnology.events.CommonEvents;
import OpenTechnology.events.FMLEvents;
import OpenTechnology.item.Items;
import OpenTechnology.network.SparkPacket;
import OpenTechnology.network.SparkPacketHandler;
import OpenTechnology.tileentities.TileEntityChatBox;
import OpenTechnology.tileentities.TileEntityCreativeChatBox;
import OpenTechnology.tileentities.TileEntityRadar;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    public static SimpleNetworkWrapper wrapper;

	public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
        FMLCommonHandler.instance().bus().register(new FMLEvents());

        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(OpenTechnology.MODID);
        wrapper.registerMessage(new SparkPacketHandler(), SparkPacket.class, 0, Side.CLIENT);

        Blocks.init();
        Items.init();
        Drivers.init();
        Recipes.init();

        GameRegistry.registerTileEntity(TileEntityCreativeChatBox.class, "TileEntityCreativeChatBox");
        GameRegistry.registerTileEntity(TileEntityChatBox.class, "TileEntityChatBox");
        GameRegistry.registerTileEntity(TileEntityRadar.class, "TileEntityRadar");


    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
