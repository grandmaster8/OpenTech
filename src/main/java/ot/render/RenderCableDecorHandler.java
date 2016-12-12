package ot.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import ot.proxy.ClientProxy;

/**
 * Created by Avaja on 08.12.2016.
 */
public class RenderCableDecorHandler implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        block = Block.getBlockById(metadata);
        if(block != null){
            Tessellator tessellator = Tessellator.instance;
            renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            IIcon iIcon = block.getIcon(3, 0);

            if(iIcon != null){
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);

                renderer.renderFaceXPos(block, 0.0D, -0.1D, 0.0D, iIcon);
                tessellator.draw();
            }

            iIcon = block.getIcon(2, 0);

            if(iIcon != null){
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                renderer.renderFaceXNeg(block, 0.0D, -0.1D, 0.0D, iIcon);
                tessellator.draw();
            }

            iIcon = block.getIcon(1, 0);

            if(iIcon != null){
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                renderer.renderFaceYPos(block, 0.0D, -0.1D, 0.0D, iIcon);
                tessellator.draw();
            }

            iIcon = block.getIcon(0, 0);

            if(iIcon != null){
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                renderer.renderFaceYNeg(block, 0.0D, -0.1D, 0.0D, iIcon);
                tessellator.draw();
            }

            iIcon = block.getIcon(4, 0);

            if(iIcon != null){
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                renderer.renderFaceZPos(block, 0.0D, -0.1D, 0.0D, iIcon);
                tessellator.draw();
            }

            iIcon = block.getIcon(5, 0);

            if(iIcon != null){
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1.0F);
                renderer.renderFaceZNeg(block, 0.0D, -0.1D, 0.0D, iIcon);
                tessellator.draw();
            }
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        renderer.renderStandardBlock(block, x, y, z);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ClientProxy.CableDecorRenderingId;
    }
}
