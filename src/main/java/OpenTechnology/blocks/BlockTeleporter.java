package OpenTechnology.blocks;

import OpenTechnology.OpenTechnology;
import OpenTechnology.tileentities.TileEntityTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockTeleporter extends Block implements ITileEntityProvider{
	private IIcon side, top, bottom;

	public BlockTeleporter() {
		super(Material.iron);
		super.setHardness(10);
		super.setBlockName("OpenTechnology_teleporter");
		this.setCreativeTab(OpenTechnology.tab);
	}

	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		top = p_149651_1_.registerIcon(OpenTechnology.MODID+":teleportertop");
		side = p_149651_1_.registerIcon(OpenTechnology.MODID+":teleporterside");
		bottom = p_149651_1_.registerIcon(OpenTechnology.MODID+":teleporterbottom");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == 1){
			return top;
		}else if(side == 0){
			return bottom;
		}else{
			return this.side;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTeleporter();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		player.openGui(OpenTechnology.instance, 1, world, x, y, z);
		return true;
	}
}
