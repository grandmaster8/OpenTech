package ot.driver;

import cpw.mods.fml.common.Loader;
import li.cil.oc.api.API;
import li.cil.oc.api.driver.Item;
import ot.Config;

/**
 * Created by Avaja on 07.05.2016.
 */
public class Drivers {
    public static Item driverTeslaUpgrade, driverRadarUpgrade, driverNRUpgrade;

    public static void init(){
        driverTeslaUpgrade = new DriverTeslaUpgrade();
        driverRadarUpgrade = new DriverRadarUpgrade();
        driverNRUpgrade = new DriverNRUpgrade();

        if(Config.registerTeslaUpgrade)
            API.driver.add(driverTeslaUpgrade);

        if(Config.registerRadarUpgrade)
            API.driver.add(driverRadarUpgrade);

        if(Config.registerNR && Loader.isModLoaded("IC2"))
            API.driver.add(driverNRUpgrade);
    }
}
