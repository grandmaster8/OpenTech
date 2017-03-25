package ot.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntitySpellParticleFX;
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
        Random rand = Minecraft.getMinecraft().theWorld.rand;
        if(rand.nextBoolean()){
            return EnumRarity.valueOf("unreal");
        }else{
            return EnumRarity.epic;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        EffectRenderer effectRenderer = Minecraft.getMinecraft().effectRenderer;
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        for (int l = 0; l < 10; ++l)
        {
            double px, py, pz, pmx, pmy, pmz;
            px = x + 0.5;
            py = y + 0.5;
            pz = z + 0.5;
            pmx = random.nextBoolean() ? random.nextGaussian() * - 1 : random.nextGaussian();
            pmy = random.nextGaussian();
            pmz = random.nextBoolean() ? random.nextGaussian() * - 1 : random.nextGaussian();

            EntitySpellParticleFX particleFX = new EntitySpellParticleFX(world, px, py, pz, pmx, pmy, pmz);
            particleFX.setRBGColorF(random.nextFloat(), random.nextFloat(), random.nextFloat());

            effectRenderer.addEffect(particleFX);
        }
    }
}
