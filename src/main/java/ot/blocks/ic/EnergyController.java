package ot.blocks.ic;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ot.OpenTechnology;
import ot.tileentities.TileEntityEnergyController;

/**
 * Created by Avaja on 27.01.2017.
 */
public class EnergyController extends BlockContainer {

    public EnergyController() {
        super(Material.iron);
        setCreativeTab(OpenTechnology.tab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityEnergyController();
    }
}
