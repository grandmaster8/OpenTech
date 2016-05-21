package OpenTechnology.item;

import OpenTechnology.OpenTechnology;
import net.minecraft.item.Item;

/**
 * Created by Avaja on 07.05.2016.
 */
public class ItemTeslaUpgrade extends Item  {

    public ItemTeslaUpgrade() {
        setCreativeTab(OpenTechnology.tab);
        setUnlocalizedName("OpenTechnology_teslaUpgrade");
        setTextureName(OpenTechnology.MODID+":UpgradeTesla");
    }
}
