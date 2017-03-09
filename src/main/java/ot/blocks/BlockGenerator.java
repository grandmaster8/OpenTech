package ot.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ot.OpenTechnology;
import ot.blocks.basic.BasicBlockContainer;
import ot.tileentities.TileEntityGenerator;

/**
 * Created by Avaja on 09.03.2017.
 */
public class BlockGenerator extends BasicBlockContainer {

    protected BlockGenerator() {
        super(Material.anvil, "generator");
        setBlockTextureName("generator");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if(!world.isRemote)
            player.openGui(OpenTechnology.instance, 1, world, x, y, z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityGenerator();
    }
}
