package ot.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ot.blocks.basic.BasicBlockContainer;
import ot.tileentities.TileEntityWorldInterface;

import java.util.Random;

/**
 * Created by Avaja on 20.03.2017.
 */
public class BlockWorldInterface extends BasicBlockContainer implements OTBlock {

    public BlockWorldInterface() {
        super(Material.iron, "worldInterface");
        setBlockTextureName("worldInterface");
    }

    @Override
    public int getMobilityFlag() {
        return super.getMobilityFlag();
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityWorldInterface();
    }

    @Override
    public EnumRarity getRarity() {
        return EnumRarity.epic;
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        for (int l = 0; l < 3; ++l)
        {
            double d6 = (double)((float)x + random.nextFloat());
            double particle_y = (double)((float)y + random.nextFloat());
            d6 = (double)((float)z + random.nextFloat());
            double particle_moving_x = 0.0D;
            double particle_moving_y = 0.0D;
            double particle_moving_z = 0.0D;
            int i1 = random.nextInt(2) * 2 - 1;
            int j1 = random.nextInt(2) * 2 - 1;
            particle_moving_x = ((double)random.nextFloat() - 0.5D) * 0.125D;
            particle_moving_y = ((double)random.nextFloat() - 0.5D) * 0.125D;
            particle_moving_z = ((double)random.nextFloat() - 0.5D) * 0.125D;
            double particle_z = (double)z + 0.5D + 0.25D * (double)j1;
            particle_moving_z = (double)(random.nextFloat() * 1.0F * (float)j1);
            double particle_x = (double)x + 0.5D + 0.25D * (double)i1;
            particle_moving_x = (double)(random.nextFloat() * 1.0F * (float)i1);
            world.spawnParticle("mobSpell", particle_x, particle_y, particle_z, particle_moving_x, particle_moving_y, particle_moving_z);
        }
    }
}
