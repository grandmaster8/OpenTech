package ot.blocks.antenna;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import ot.OpenTechnology;
import ot.blocks.Blocks;
import ot.proxy.ClientProxy;
import ot.tileentities.TileEntityLDA;

import java.util.Random;

/**
 * Created by Avaja on 07.12.2016.
 */
public class BlockAntenna extends Block {

    public BlockAntenna() {
        super(Material.iron);
        setBlockName("OpenTechnology_Antenna");
        setBlockTextureName(OpenTechnology.MODID + ":antenna");
        setCreativeTab(OpenTechnology.tab);
        setHarvestLevel("pickaxe", 0);
        setHardness(5);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int tileProvider) {
        super.breakBlock(world, x, y, z, block, tileProvider);
        Block b = world.getBlock(x, y - 1, z);
        if(b == Blocks.antenna){
            b.updateTick(world, x, y, z, world.rand);
        }else if(b == Blocks.lda){
            TileEntityLDA lda = (TileEntityLDA) world.getTileEntity(x, y - 1, z);
            lda.destroyStructure();
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        Block b = world.getBlock(x, y - 1, z);
        if(b == null) return;
        if(b == Blocks.antenna){
            b.updateTick(world, x, y - 1, z, world.rand);
        }else if(b == Blocks.lda){
            TileEntityLDA lda = (TileEntityLDA) world.getTileEntity(x, y - 1, z);
            lda.destroyStructure();
        }
    }

    @Override
    public int getRenderType() {
        return ClientProxy.LDARenderingId;
    }
}
