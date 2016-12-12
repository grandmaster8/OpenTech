package ot.blocks.antenna;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import ot.OpenTechnology;
import ot.blocks.Blocks;
import ot.proxy.ClientProxy;

/**
 * Created by Avaja on 07.12.2016.
 */
public class BlockCell extends Block {

    public BlockCell() {
        super(Material.iron);
        setBlockName("OpenTechnology_Cell");
        setBlockTextureName(OpenTechnology.MODID + ":antennaCell");
        setCreativeTab(OpenTechnology.tab);
        setHarvestLevel("pickaxe", 0);
        setHardness(5);
    }

    @Override
    public int getRenderType() {
        return ClientProxy.LDARenderingId;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int tileProvider) {
        super.breakBlock(world, x, y, z, block, tileProvider);
        Block b = world.getBlock(x, y - 1, z);
        if(b == Blocks.antenna){
            b.updateTick(world, x, y, z, world.rand);
        }
    }
}
