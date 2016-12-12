package ot.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ot.blocks.basic.BasicBlockContainer;
import ot.system.ChatBoxEventSystem;
import ot.tileentities.TileEntityChatBox;

/**
 * Created by Avaja on 06.05.2016.
 */
public class BlockChatBox extends BasicBlockContainer{

    public BlockChatBox() {
        super(Material.iron, "chatbox");
        setBlockTextureName("chatbox");
        setHarvestLevel("pickaxe", 0);
        setHardness(5);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityChatBox();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
        TileEntityChatBox box = (TileEntityChatBox) world.getTileEntity(x, y, z);
        if (!world.isRemote)
            ChatBoxEventSystem.remove(box);

        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }
}
