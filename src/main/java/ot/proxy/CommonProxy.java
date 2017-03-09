package ot.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import li.cil.oc.Settings;
import li.cil.oc.Settings$;
import net.minecraftforge.common.MinecraftForge;
import ot.Config;
import ot.GuiHandler;
import ot.OpenTechnology;
import ot.Recipes;
import ot.blocks.Blocks;
import ot.driver.Drivers;
import ot.entity.EntityEnergyBolt;
import ot.events.CommonEvents;
import ot.events.FMLEvents;
import ot.item.Items;
import ot.network.SparkPacket;
import ot.network.SparkPacketHandler;
import ot.tileentities.TileEntities;

import java.lang.reflect.Field;

public class CommonProxy {
    public static SimpleNetworkWrapper wrapper;

    public static boolean isWrench = false;

	public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
        FMLCommonHandler.instance().bus().register(new FMLEvents());
        NetworkRegistry.INSTANCE.registerGuiHandler(OpenTechnology.instance, new GuiHandler());

        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(OpenTechnology.MODID);
        wrapper.registerMessage(new SparkPacketHandler(), SparkPacket.class, 0, Side.CLIENT);

        Blocks.init();
        Items.init();
        Drivers.init();
        Recipes.init();
        TileEntities.init();

        if(Config.registerTurretUpgrade)
            EntityRegistry.registerModEntity(EntityEnergyBolt.class, "ot_energybolt", 0, OpenTechnology.instance, 64, 20, true);
	}

    private void powerOn(){
        try {
            Field field = Settings.class.getDeclaredField("ignorePower");
            field.setAccessible(true);
            field.set(Settings$.MODULE$.get(), false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void postInit(FMLPostInitializationEvent e) {
        powerOn();
    }
}
