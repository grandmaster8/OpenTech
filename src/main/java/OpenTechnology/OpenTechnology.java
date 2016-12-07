package OpenTechnology;

import OpenTechnology.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.DamageSource;
import org.apache.logging.log4j.Logger;

@Mod(modid= OpenTechnology.MODID, name= OpenTechnology.MODID, version= OpenTechnology.VERSION)
public class OpenTechnology {
	public final static String MODID = "OpenTechnology";
	public final static String VERSION = "0.4.1a-dev";

	public static Logger logger;
	public static CreativeTab tab = new CreativeTab();


	public static DamageSource electricDamage = new DamageSource("electricDamage");

	@Mod.Instance
	public static OpenTechnology instance;

	@SidedProxy(clientSide = "OpenTechnology.proxy.ClientProxy", serverSide = "OpenTechnology.proxy.ServerProxy")
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
}
