package ot.item;

import ot.OpenTechnology;

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
