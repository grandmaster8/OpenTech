package ot.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ot.OpenTechnology;
import ot.blocks.basic.BasicBlockContainer;
import ot.proxy.ClientProxy;
import ot.tileentities.TileEntityRadar;

/**
 * Created by Avaja on 04.08.2016.
 */
public class BlockRadar extends BasicBlockContainer {

    public BlockRadar() {
        super(Material.iron, "radar");
        setCreativeTab(OpenTechnology.tab);
        setBlockTextureName("stone");
        setHarvestLevel("pickaxe", 0);
        setHardness(5);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.getOrientation(l).getOpposite().ordinal(),1);
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        return true;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ClientProxy.radarRenderingId;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityRadar();
    }
}
