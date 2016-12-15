package ot.item;

import ot.OpenTechnology;

/**
 * Created by Avaja on 15.12.2016.
 */
public class ItemNRUpgrade extends ItemNodeInformation {

    public ItemNRUpgrade() {
        setCreativeTab(OpenTechnology.tab);
        setUnlocalizedName("OpenTechnology_nrUpgrade");
        setTextureName(OpenTechnology.MODID+":UpgradeReactor");
    }
}
