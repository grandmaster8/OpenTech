package ot.driver;

import li.cil.oc.api.API;
import li.cil.oc.api.driver.Item;
import ot.Config;

/**
 * Created by Avaja on 07.05.2016.
 */
public class Drivers {
    public static Item driverTeslaUpgrade, driverRadarUpgrade;

    public static void init(){
        driverTeslaUpgrade = new DriverTeslaUpgrade();
        driverRadarUpgrade = new DriverRadarUpgrade();

        if(Config.registerTeslaUpgrade)
            API.driver.add(driverTeslaUpgrade);

        if(Config.registerRadarUpgrade)
            API.driver.add(driverRadarUpgrade);
    }
}
