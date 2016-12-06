package OpenTechnology.driver;

import li.cil.oc.api.API;
import li.cil.oc.api.driver.Item;

/**
 * Created by Avaja on 07.05.2016.
 */
public class Drivers {
    public static Item driverTeslaUpgrade, driverRadarUpgrade;

    public static void init(){
        driverTeslaUpgrade = new DriverTeslaUpgrade();
        driverRadarUpgrade = new DriverRadarUpgrade();

        API.driver.add(driverTeslaUpgrade);
        API.driver.add(driverRadarUpgrade);
    }
}
