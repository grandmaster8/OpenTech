package ot.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ot.blocks.basic.BasicBlockContainer;
import ot.tileentities.TileEntityWorldInterface;

/**
 * Created by Avaja on 20.03.2017.
 */
public class BlockWorldInterface extends BasicBlockContainer {

    public BlockWorldInterface() {
        super(Material.iron, "world_interface");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityWorldInterface();
    }
}
