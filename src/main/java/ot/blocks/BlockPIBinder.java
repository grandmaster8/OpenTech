package ot.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ot.OpenTechnology;
import ot.blocks.basic.BasicBlockContainer;
import ot.tileentities.TileEntityPIB;

/**
 * Created by Avaja on 10.12.2016.
 */
public class BlockPIBinder extends BasicBlockContainer implements OTBlock {

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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int cx, float cy, float cz, float p_149727_9_) {
        if(!world.isRemote)
            ((TileEntityPIB)world.getTileEntity(x, y, z)).playerClicked(entityPlayer);
        return true;
    }

    @Override
    public EnumRarity getRarity() {
        return EnumRarity.uncommon;
    }
}
