package ot.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ot.OpenTechnology;
import ot.blocks.basic.BasicBlockContainer;
import ot.tileentities.TileEntityPIB;

/**
 * Created by Avaja on 10.12.2016.
 */
public class BlockPIBinder extends BasicBlockContainer {

    protected BlockPIBinder() {
        super(Material.iron, "pib");

        setCreativeTab(OpenTechnology.tab);
        setBlockTextureName("pib");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityPIB();
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityPlayer) {
        ((TileEntityPIB)world.getTileEntity(x, y, z)).playerClicked(entityPlayer);
    }
}
