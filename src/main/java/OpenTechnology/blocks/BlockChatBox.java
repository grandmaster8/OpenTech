package OpenTechnology.blocks;

import OpenTechnology.OpenTechnology;
import OpenTechnology.system.ChatBoxEventSystem;
import OpenTechnology.tileentities.TileEntityChatBox;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Avaja on 06.05.2016.
 */
public class BlockChatBox extends BlockContainer{

    public BlockChatBox() {
        super(Material.iron);
        setBlockName("OpenTechnology_chatbox");
        setBlockTextureName(OpenTechnology.MODID+":chatbox");
        setCreativeTab(OpenTechnology.tab);
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
