package OpenTechnology.blocks;

import OpenTechnology.OpenTechnology;
import OpenTechnology.tileentities.TileEntityPlayerInventoryBinder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Avaja on 11.05.2016.
 */
public class BlockPlayerInventoryBinder extends BlockContainer {

    protected BlockPlayerInventoryBinder() {
        super(Material.iron);
        setBlockName("ot_player_inventory_binder");
        setCreativeTab(OpenTechnology.tab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityPlayerInventoryBinder();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntityPlayerInventoryBinder binder = (TileEntityPlayerInventoryBinder) world.getTileEntity(x, y, z);
        if (!binder.isConnected())
            binder.setPlayer(player);

        return true;
    }
}
