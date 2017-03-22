package ot.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ot.blocks.basic.BasicBlockContainer;
import ot.system.ChatBoxEventSystem;
import ot.tileentities.TileEntityCreativeChatBox;

/**
 * Created by Avaja on 05.05.2016.
 */
public class BlockCreativeChatBox extends BasicBlockContainer implements OTBlock {

    public BlockCreativeChatBox() {
        super(Material.iron, "creativeChatbox");
        this.setBlockTextureName("creativeChatbox");
        setHarvestLevel("pickaxe", 0);
        setHardness(5);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i1) {
        return new TileEntityCreativeChatBox();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
        TileEntityCreativeChatBox box = (TileEntityCreativeChatBox) world.getTileEntity(x, y, z);
        ChatBoxEventSystem.remove(box);

        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }

    @Override
    public EnumRarity getRarity() {
        return EnumRarity.epic;
    }
}
