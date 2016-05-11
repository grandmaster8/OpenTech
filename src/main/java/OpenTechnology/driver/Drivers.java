package OpenTechnology.driver;

import li.cil.oc.api.API;
import li.cil.oc.api.Driver;
import li.cil.oc.api.driver.Item;

/**
 * Created by Avaja on 07.05.2016.
 */
public class Drivers {
    public static Item driverTeslaUpgrade, driverTesseractUpgrade;

    public static void init(){
        driverTeslaUpgrade = new DriverTeslaUpgrade();
        driverTesseractUpgrade = new DriverTesseractUpgrade();

        API.driver.add(driverTeslaUpgrade);
        API.driver.add(driverTesseractUpgrade);
    }
}
