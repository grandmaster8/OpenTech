package ot.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import ot.blocks.Blocks;

/**
 * Created by Avaja on 08.12.2016.
 */
public class ItemBlockCableDecor extends ItemBlock {

    public ItemBlockCableDecor(Block block) {
        super(Blocks.cableDecor);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float f1, float f2, float f3) {
        if(world.getBlock(x, y, z) instanceof li.cil.oc.common.block.Cable){
            world.setBlock(x, y, z, Blocks.cableDecor);
            Blocks.cableDecor.onBlockPlacedBy(world, x, y, z, player, itemStack);
        }
        return true;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return Block.getBlockById(itemStack.getItemDamage()).getUnlocalizedName();
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        String wood = StatCollector.translateToLocal(getUnlocalizedName(itemStack) + ".name");
        return wood + "[" + StatCollector.translateToLocal("tile.OpenTechnology_CableDecor.name") + "]";
    }
}
