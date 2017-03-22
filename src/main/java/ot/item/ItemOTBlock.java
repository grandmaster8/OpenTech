package ot.item;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import ot.blocks.OTBlock;

/**
 * Created by Avaja on 22.03.2017.
 */
public class ItemOTBlock extends ItemBlock {

    public ItemOTBlock(Block block) {
        super(block);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        if(field_150939_a instanceof OTBlock){
            return ((OTBlock) field_150939_a).getRarity();
        }else{
            return super.getRarity(stack);
        }
    }
}
