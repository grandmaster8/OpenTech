package ot.blocks.ic;

import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import ot.OpenTechnology;
import ot.tileentities.ic.TileEntityEnergyController;
import ot.utils.Utils;

/**
 * Created by Avaja on 27.01.2017.
 */
public class BlockEnergyController extends BlockContainer {

    private static IIcon sides, out;

    public BlockEnergyController() {
        super(Material.iron);
        setCreativeTab(OpenTechnology.tab);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        player.openGui(OpenTechnology.instance, 0, world, x, y, z);
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase livingBase, ItemStack p_149689_6_) {
        world.setBlockMetadataWithNotify(x, y, z, Utils.getLookAt(livingBase).sideHit, 0);
        super.onBlockPlacedBy(world, x, y, z, livingBase, p_149689_6_);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        return super.onBlockPlaced(world, x, y, z, side, p_149660_6_, p_149660_7_, p_149660_8_, p_149660_9_);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        sides = register.registerIcon(OpenTechnology.MODID + ":energyControllerSides");
        out = register.registerIcon(OpenTechnology.MODID + ":energyControllerOut");
    }

    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int metadata = blockAccess.getBlockMetadata(x, y, z);
        if(side != metadata)
            return sides;
        else
            return out;
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        if (side != 4)
            return sides;
        else
            return out;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityEnergyController();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
        if(!world.isRemote)
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergyTile) world.getTileEntity(x, y, z)));

        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }
}
