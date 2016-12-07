package OpenTechnology.blocks.antenna;

import OpenTechnology.OpenTechnology;
import OpenTechnology.proxy.ClientProxy;
import OpenTechnology.system.LDAS;
import OpenTechnology.tileentities.TileEntityLDA;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Avaja on 07.12.2016.
 */
public class BlockLDA extends BlockContainer {

    public BlockLDA() {
        super(Material.iron);
        setBlockName("OpenTechnology_lda");
        setBlockTextureName(OpenTechnology.MODID+":antennaController");
        setCreativeTab(OpenTechnology.tab);
        setHarvestLevel("pickaxe", 0);
        setHardness(5);
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
        return ClientProxy.LDARenderingId;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        TileEntityLDA lda = new TileEntityLDA();
        LDAS.addLDA(lda);
        return lda;
    }
}
