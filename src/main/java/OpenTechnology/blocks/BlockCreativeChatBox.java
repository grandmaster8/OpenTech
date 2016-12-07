package OpenTechnology.blocks;

import OpenTechnology.OpenTechnology;
import OpenTechnology.system.ChatBoxEventSystem;
import OpenTechnology.tileentities.TileEntityCreativeChatBox;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Avaja on 05.05.2016.
 */
public class BlockCreativeChatBox extends BlockContainer {

    public BlockCreativeChatBox() {
        super(Material.iron);
        this.setBlockName("OpenTechnology_creativeChatbox");
        this.setBlockTextureName(OpenTechnology.MODID+":creativeChatbox");
        this.setCreativeTab(OpenTechnology.tab);
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
}
