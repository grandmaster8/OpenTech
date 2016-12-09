package OpenTechnology.item;

import OpenTechnology.OpenTechnology;

/**
 * Created by Avaja on 21.05.2016.
 */
public class ItemRadarUpgrade extends ItemNodeInformation {
    public ItemRadarUpgrade() {
        setCreativeTab(OpenTechnology.tab);
        setUnlocalizedName("OpenTechnology_RadarUpgrade");
        setTextureName(OpenTechnology.MODID+":UpgradeRadar");
    }
}
