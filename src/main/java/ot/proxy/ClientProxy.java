package ot.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import ot.events.ClientEvents;
import ot.events.ClientFMLEvents;
import ot.render.BlockPIMRender;
import ot.render.RenderCableDecorHandler;
import ot.render.RenderingAntennaHandler;
import ot.render.RenderingRadarHandler;
import ot.render.specialRender.RadarRender;
import ot.render.specialRender.TileAntennaRender;
import ot.tileentities.TileEntityLDA;
import ot.tileentities.TileEntityRadar;

public class ClientProxy extends CommonProxy{

	public static int radarRenderingId = RenderingRegistry.getNextAvailableRenderId();
	public static int LDARenderingId = RenderingRegistry.getNextAvailableRenderId();
	public static int CableDecorRenderingId = RenderingRegistry.getNextAvailableRenderId();
	public static int PIMRenderingId = RenderingRegistry.getNextAvailableRenderId();


	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadar.class, new RadarRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLDA.class, new TileAntennaRender());

		RenderingRegistry.registerBlockHandler(radarRenderingId, new RenderingRadarHandler());
		RenderingRegistry.registerBlockHandler(LDARenderingId, new RenderingAntennaHandler());
		RenderingRegistry.registerBlockHandler(CableDecorRenderingId, new RenderCableDecorHandler());
		RenderingRegistry.registerBlockHandler(PIMRenderingId, new BlockPIMRender());

		EnumHelper.addRarity("unreal", EnumChatFormatting.OBFUSCATED, "unreal");
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		MinecraftForge.EVENT_BUS.register(new ClientEvents());
		FMLCommonHandler.instance().bus().register(new ClientFMLEvents());
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
	

}
