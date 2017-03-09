package ot.item;

import ot.OpenTechnology;

/**
 * Created by Avaja on 09.03.2017.
 */
public class ItemTurretUpgrade extends ItemNodeInformation {

    public ItemTurretUpgrade() {
        setCreativeTab(OpenTechnology.tab);
        setUnlocalizedName("OpenTechnology_turretUpgrade");
        setTextureName(OpenTechnology.MODID+":turret");
    }
}
