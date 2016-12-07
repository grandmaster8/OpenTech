package OpenTechnology.render;

import OpenTechnology.blocks.Blocks;
import OpenTechnology.proxy.ClientProxy;
import OpenTechnology.tileentities.TileEntityRadar;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by Avaja on 07.12.2016.
 */
public class RenderingRadarHandler implements ISimpleBlockRenderingHandler {

    @Override
    public int getRenderId() {
        return ClientProxy.radarRenderingId;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        if (block == Blocks.radar) {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityRadar(), 0.0D, 0.0D, 0.0D, 0.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

}
