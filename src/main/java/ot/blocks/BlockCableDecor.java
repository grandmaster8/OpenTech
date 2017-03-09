package ot.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.Items;
import li.cil.oc.common.tileentity.Cable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ot.proxy.ClientProxy;
import ot.proxy.CommonProxy;
import ot.tileentities.TileEntityCableDecor;
import scala.reflect.ClassTag$;

import java.util.Random;

/**
 * Created by Avaja on 08.12.2016.
 */
public class BlockCableDecor extends li.cil.oc.common.block.Cable {

    public BlockCableDecor() {
        super(ClassTag$.MODULE$.<Cable>apply(Cable.class));
        //setCreativeTab(OpenTechnology.tab);
        minX = 0;
        minY = 0;
        minZ = 0;
        maxX = 1;
        maxY = 1;
        maxZ = 1;
        lightOpacity = 255;
    }

    @Override
    public Cable createTileEntity(World world, int metadata) {
        return new TileEntityCableDecor();
    }

    @Override
    public void doSetBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        if(CommonProxy.isWrench){
            super.doSetBlockBoundsBasedOnState(world, x, y, z);
        }else{
            setBlockBounds(0, 0, 0, 1, 1, 1);
        }
    }

    @Override
    public int getLightOpacity() {
        return lightOpacity;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        tileEntity.blockMetadata = stack.getItemDamage();
    }

    @Override
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, ForgeDirection globalSide, ForgeDirection localSide) {
        if(!CommonProxy.isWrench){
            Block block = Block.getBlockById(world.getTileEntity(x, y, z).blockMetadata);
            return block.getIcon(globalSide.ordinal(), 0);
        }
        return super.getIcon(world, x, y, z, globalSide, localSide);
    }


    @Override
    public Item getItemDropped(int i, Random random, int i1) {
        return Item.getItemFromBlock(Items.get("cable").block());
    }

    @Override
    public boolean isOpaqueCube() {
        return super.isOpaqueCube();
    }


    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderType() {
        if(!CommonProxy.isWrench){
            return ClientProxy.CableDecorRenderingId;
        }
        return super.getRenderType();
    }

    @Override
    public boolean isNormalCube() {
        if(CommonProxy.isWrench){
            return super.isNormalCube();
        }
        return true;
    }


    @Override
    public synchronized AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
    }

    @Override
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        if(!CommonProxy.isWrench){
            return 16777215;
        }
        return super.colorMultiplier(world, x, y, z);
    }

    @Override
    public int getRenderColor(int metadata) {
        if(!CommonProxy.isWrench){
            return 16777215;
        }
        return super.getRenderColor(metadata);
    }
}
