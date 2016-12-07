package OpenTechnology.blocks;

import OpenTechnology.OpenTechnology;
import OpenTechnology.system.LDAS;
import OpenTechnology.tileentities.TileEntityLDA;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Avaja on 07.12.2016.
 */
public class BlockLDA extends BlockContainer {

    public BlockLDA() {
        super(Material.iron);
        setBlockName("OpenTechnology_lda");
        setBlockTextureName(OpenTechnology.MODID+":chatbox");
        setCreativeTab(OpenTechnology.tab);
        setHarvestLevel("pickaxe", 0);
        setHardness(5);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        TileEntityLDA lda = new TileEntityLDA();
        LDAS.addLDA(lda);
        return lda;
    }
}
