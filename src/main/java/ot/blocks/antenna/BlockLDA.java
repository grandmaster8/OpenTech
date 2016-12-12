package ot.blocks.antenna;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ot.OpenTechnology;
import ot.blocks.Blocks;
import ot.proxy.ClientProxy;
import ot.system.LDAS;
import ot.tileentities.TileEntityLDA;

/**
 * Created by Avaja on 07.12.2016.
 */
public class BlockLDA extends BlockContainer {

    public BlockLDA() {
        super(Material.iron);
        setBlockName("OpenTechnology_lda");
        setBlockTextureName(OpenTechnology.MODID+":antennaController");
        setCreativeTab(OpenTechnology.tab);
        setHarvestLevel("pickaxe", 0);
        setHardness(5);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int tileProvider) {
        TileEntityLDA tileEntityLDA = (TileEntityLDA) world.getTileEntity(x, y, z);
        if(tileEntityLDA.isStructure()){
            for(int i = 1; i <= 15; i++){
                Block b = world.getBlock(x, y + i, z);
                if(b == Blocks.antenna || b == Blocks.antennaCell)
                    world.setBlockMetadataWithNotify(x, y + i, z, 0, 3);
            }
        }

        LDAS.removeLDA(tileEntityLDA);
        super.breakBlock(world, x, y, z, block, tileProvider);
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        return true;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ClientProxy.LDARenderingId;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityLDA();
    }
}
