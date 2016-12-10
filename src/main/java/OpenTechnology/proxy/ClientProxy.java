package OpenTechnology.proxy;

import OpenTechnology.events.ClientFMLEvents;
import OpenTechnology.render.RenderCableDecorHandler;
import OpenTechnology.render.RenderingAntennaHandler;
import OpenTechnology.render.RenderingRadarHandler;
import OpenTechnology.render.specialRender.RadarRender;
import OpenTechnology.render.specialRender.TileAntennaRender;
import OpenTechnology.tileentities.TileEntityLDA;
import OpenTechnology.tileentities.TileEntityRadar;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

	public static int radarRenderingId = RenderingRegistry.getNextAvailableRenderId();
	public static int LDARenderingId = RenderingRegistry.getNextAvailableRenderId();
	public static int CableDecorRenderingId = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadar.class, new RadarRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLDA.class, new TileAntennaRender());


		RenderingRegistry.registerBlockHandler(radarRenderingId, new RenderingRadarHandler());
		RenderingRegistry.registerBlockHandler(LDARenderingId, new RenderingAntennaHandler());
		RenderingRegistry.registerBlockHandler(CableDecorRenderingId, new RenderCableDecorHandler());
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		FMLCommonHandler.instance().bus().register(new ClientFMLEvents());
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
	

}
