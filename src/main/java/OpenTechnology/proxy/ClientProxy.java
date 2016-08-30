package OpenTechnology.proxy;

import OpenTechnology.packet.PacketTeleporter;
import OpenTechnology.render.RenderTileEntityRadar;
import OpenTechnology.render.RobotRender;
import OpenTechnology.tileentities.TileEntityRadar;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;

public class ClientProxy extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);

		wrapper.registerMessage(PacketTeleporter.HandlerClient.class, PacketTeleporter.class, 1, Side.CLIENT);

		MinecraftForge.EVENT_BUS.register(new RobotRender());

        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadar.class, new RenderTileEntityRadar());

	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
	

}
