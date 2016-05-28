package OpenTechnology.proxy;

import OpenTechnology.OpenTechnology;
import OpenTechnology.Recipes;
import OpenTechnology.blocks.Blocks;
import OpenTechnology.driver.Drivers;
import OpenTechnology.events.CommonEvents;
import OpenTechnology.gui.GuiHandler;
import OpenTechnology.item.Items;
import OpenTechnology.packet.PacketPlayerPosition;
import OpenTechnology.tileentities.*;
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

        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("OpenTechnology");
        NetworkRegistry.INSTANCE.registerGuiHandler(OpenTechnology.instance, new GuiHandler());

        Blocks.init();
        Items.init();
        Drivers.init();
        Recipes.init();

        GameRegistry.registerTileEntity(TileEntityTeleporter.class, "TileEntityTeleporter");
        GameRegistry.registerTileEntity(TileEntityAdminChatBox.class, "TileEntityAdminChatBox");
        GameRegistry.registerTileEntity(TileEntityChatBox.class, "TileEntityChatBox");
        GameRegistry.registerTileEntity(TileEntityPlayerInventoryBinder.class, "TileEntityPlayerInventoryBinder");
        GameRegistry.registerTileEntity(TileEntityDigitizer.class, "TileEntityDigitizer");

        wrapper.registerMessage(PacketPlayerPosition.Handler.class, PacketPlayerPosition.class, 0, Side.CLIENT);
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
