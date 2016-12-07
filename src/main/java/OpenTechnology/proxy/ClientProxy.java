package OpenTechnology.proxy;

import OpenTechnology.render.RenderingHandler;
import OpenTechnology.render.specialRender.RadarRender;
import OpenTechnology.tileentities.TileEntityRadar;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

	public static int renderingId = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadar.class, new RadarRender());
		//MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.radar), );
		RenderingRegistry.registerBlockHandler(renderingId, new RenderingHandler());
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
	

}
