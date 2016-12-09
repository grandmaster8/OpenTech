package OpenTechnology.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Created by Avaja on 07.05.2016.
 */
public class Items {
    public static Item tesla, radar, wrench;

    public static void init(){

        tesla = new ItemTeslaUpgrade();
        radar = new ItemRadarUpgrade();
        wrench = li.cil.oc.api.Items.get("wrench").item();

        GameRegistry.registerItem(tesla, "ot_teslaUpgrade");
        GameRegistry.registerItem(radar, "ot_radarUpgrade");
    }
}
