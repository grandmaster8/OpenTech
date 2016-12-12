package ot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ot.blocks.Blocks;

/**
 * Created by Avaja on 05.05.2016.
 */
public class CreativeTab extends CreativeTabs{

    public CreativeTab() {
        super(OpenTechnology.MODID);
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(Blocks.creativeChatbox);
    }
}
