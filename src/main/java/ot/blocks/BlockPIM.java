package ot.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ot.OpenTechnology;
import ot.proxy.ClientProxy;
import ot.tileentities.TileEntityPIM;

public class BlockPIM extends BlockContainer {

    public static IIcon iIcon1, iIcon2;

    protected BlockPIM() {
        super(Material.ground);
        setStepSound(soundTypeMetal);
        setCreativeTab(OpenTechnology.tab);
        setBlockBounds(0f, 0f, 0f, 1f, 0.3f, 1f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        super.registerBlockIcons(registry);

        iIcon1 = registry.registerIcon(OpenTechnology.MODID + ":pim_black");
        iIcon2 = registry.registerIcon(OpenTechnology.MODID + ":pim_blue");
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return iIcon2;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ClientProxy.PIMRenderingId;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return side == ForgeDirection.DOWN;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        world.markBlockForUpdate(x, y, z);
        if(!world.isRemote)
            if(entity instanceof EntityPlayer){
                TileEntityPIM pim = (TileEntityPIM) world.getTileEntity(x, y, z);
                if(!pim.hasPlayer())
                    pim.setPlayer((EntityPlayer) entity);
            }
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityPIM();
    }
}
