package OpenTechnology.blocks;

import OpenTechnology.OpenTechnology;
import OpenTechnology.tileentities.TileEntityDigitizer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Avaja on 28.05.2016.
 */
public class BlockDigitizer extends BlockContainer{

    protected BlockDigitizer() {
        super(Material.anvil);
        setBlockName("OpenTechnology_Digitizer");
        setCreativeTab(OpenTechnology.tab);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        player.openGui(OpenTechnology.instance, 2, world, x, y, z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityDigitizer();
    }
}
