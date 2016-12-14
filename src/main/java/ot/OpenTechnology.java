package ot;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.util.DamageSource;
import org.apache.logging.log4j.Logger;
import ot.command.OTCommand;
import ot.proxy.CommonProxy;

@Mod(modid= OpenTechnology.MODID, name= OpenTechnology.MODID, version= OpenTechnology.VERSION)
public class OpenTechnology {
	public final static String MODID = "OpenTechnology";
	public final static String VERSION = "0.5.7a-dev";

	public static Logger logger;
	public static CreativeTab tab = new CreativeTab();


	public static DamageSource electricDamage = new DamageSource("electricDamage");

	@Mod.Instance
	public static OpenTechnology instance;

	@SidedProxy(clientSide = "ot.proxy.ClientProxy", serverSide = "ot.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();
		Config.init(e.getSuggestedConfigurationFile());
	    proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	    proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	    proxy.postInit(e);
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		System.out.println("Register " + MODID + " command.");
		event.registerServerCommand(new OTCommand());
	}
}
