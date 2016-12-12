package ot.item;

import ot.OpenTechnology;

/**
 * Created by Avaja on 07.05.2016.
 */
public class ItemTeslaUpgrade extends ItemNodeInformation  {

    public ItemTeslaUpgrade() {
        setCreativeTab(OpenTechnology.tab);
        setUnlocalizedName("OpenTechnology_teslaUpgrade");
        setTextureName(OpenTechnology.MODID+":UpgradeTesla");
    }
}
