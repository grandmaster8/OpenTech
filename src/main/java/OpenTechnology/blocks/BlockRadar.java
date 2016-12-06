package OpenTechnology.blocks;

import OpenTechnology.OpenTechnology;
import OpenTechnology.tileentities.TileEntityRadar;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Avaja on 04.08.2016.
 */
public class BlockRadar extends BlockContainer {

    public BlockRadar() {
        super(Material.iron);
        setCreativeTab(OpenTechnology.tab);
        setBlockName("OpenTechnology_radar");
        setHarvestLevel("pickaxe", 0);
        setHardness(5);
    }

/*    @Override
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
        return -1;
    }*/

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityRadar();
    }
}
