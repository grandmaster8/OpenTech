package ot.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import ot.blocks.BlockPIM;
import ot.proxy.ClientProxy;
import ot.tileentities.TileEntityPIM;

import java.util.EnumSet;
import java.util.Set;

public class BlockPIMRender implements ISimpleBlockRenderingHandler {

    private static final EnumSet<ForgeDirection> ALL_SIDES = EnumSet.allOf(ForgeDirection.class);

    private static boolean hasPlayer(IBlockAccess world, int x, int y, int z) {
        TileEntityPIM pim = (TileEntityPIM) world.getTileEntity(x, y, z);
        return pim.hasPlayer();
    }

    private static void setTopPartBounds(RenderBlocks renderer, final boolean hasPlayer) {
        renderer.setRenderBounds(1.0 / 16.0, 0.3, 1.0 / 16.0, 15.0 / 16.0, hasPlayer? (0.4 - 0.08) : 0.4, 15.0 / 16.0);
    }


    public static void renderInventoryBlock(RenderBlocks renderer, Block block, int metadata) {
        renderInventoryBlock(renderer, block, metadata, 0xFFFFFFFF);
    }

    public static void renderInventoryBlock(RenderBlocks renderer, Block block, int metadata, int colorMultiplier) {
        renderInventoryBlock(renderer, block, metadata, colorMultiplier, ALL_SIDES);
    }

    public static void renderInventoryBlock(RenderBlocks renderer, Block block, int metadata, int colorMultiplier, Set<ForgeDirection> enabledSides) {
        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);

        renderInventoryBlockNoBounds(renderer, block, metadata, colorMultiplier, enabledSides);
    }

    public static void renderInventoryBlockNoBounds(RenderBlocks renderer, Block block, int metadata, int colorMultiplier) {
        renderInventoryBlockNoBounds(renderer, block, metadata, colorMultiplier, ALL_SIDES);
    }

    public static void renderInventoryBlockNoBounds(RenderBlocks renderer, Block block, int metadata) {
        renderInventoryBlockNoBounds(renderer, block, metadata, 0xFFFFFFFF);
    }

    public static void renderInventoryBlockNoBounds(RenderBlocks renderer, Block block, int metadata, int colorMultiplier, Set<ForgeDirection> enabledSides) {
        if (enabledSides.isEmpty()) return;
        Tessellator tessellator = Tessellator.instance;
        float r;
        float g;
        float b;
        if (colorMultiplier > -1) {
            r = (colorMultiplier >> 16 & 255) / 255.0F;
            g = (colorMultiplier >> 8 & 255) / 255.0F;
            b = (colorMultiplier & 255) / 255.0F;
            GL11.glColor4f(r, g, b, 1.0F);
        }
        // Learn to matrix, please push and pop :D -- NC
        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startDrawingQuads();
        if (enabledSides.contains(ForgeDirection.DOWN)) {
            tessellator.setNormal(0.0F, -1.0F, 0.0F);
            renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
        }
        if (enabledSides.contains(ForgeDirection.UP)) {
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
        }
        if (enabledSides.contains(ForgeDirection.SOUTH)) {
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
        }
        if (enabledSides.contains(ForgeDirection.NORTH)) {
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
        }
        if (enabledSides.contains(ForgeDirection.WEST)) {
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
        }
        if (enabledSides.contains(ForgeDirection.EAST)) {
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
        }

        tessellator.draw();
        GL11.glPopMatrix();
    }

    public static void renderCube(Tessellator tes, double x1, double y1, double z1, double x2, double y2, double z2) {
        tes.addVertex(x1, y1, z1);
        tes.addVertex(x1, y2, z1);
        tes.addVertex(x2, y2, z1);
        tes.addVertex(x2, y1, z1);

        tes.addVertex(x1, y1, z2);
        tes.addVertex(x2, y1, z2);
        tes.addVertex(x2, y2, z2);
        tes.addVertex(x1, y2, z2);

        tes.addVertex(x1, y1, z1);
        tes.addVertex(x1, y1, z2);
        tes.addVertex(x1, y2, z2);
        tes.addVertex(x1, y2, z1);

        tes.addVertex(x2, y1, z1);
        tes.addVertex(x2, y2, z1);
        tes.addVertex(x2, y2, z2);
        tes.addVertex(x2, y1, z2);

        tes.addVertex(x1, y1, z1);
        tes.addVertex(x2, y1, z1);
        tes.addVertex(x2, y1, z2);
        tes.addVertex(x1, y1, z2);

        tes.addVertex(x1, y2, z1);
        tes.addVertex(x1, y2, z2);
        tes.addVertex(x2, y2, z2);
        tes.addVertex(x2, y2, z1);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        renderInventoryBlock(renderer, block, 0);

        renderer.setOverrideBlockTexture(BlockPIM.iIcon1);
        setTopPartBounds(renderer, false);
        renderInventoryBlockNoBounds(renderer, block, 0);
        renderer.clearOverrideBlockTexture();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);

        final boolean isBreaking = renderer.hasOverrideBlockTexture();
        if (!isBreaking) renderer.setOverrideBlockTexture(BlockPIM.iIcon1);

        final boolean hasPlayer = hasPlayer(world, x, y, z);
        setTopPartBounds(renderer, hasPlayer);
        renderer.renderStandardBlock(block, x, y, z);

        if (!isBreaking) renderer.clearOverrideBlockTexture();
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ClientProxy.PIMRenderingId;
    }
}
