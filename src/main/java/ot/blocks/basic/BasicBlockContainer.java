package ot.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import ot.OpenTechnology;

/**
 * Created by Avaja on 10.12.2016.
 */
public abstract class BasicBlockContainer extends BlockContainer {

    public BasicBlockContainer(Material material, String name) {
        super(material);
        setCreativeTab(OpenTechnology.tab);
        setBlockName(OpenTechnology.MODID + "_" + name);
    }

    @Override
    public Block setBlockTextureName(String texture) {
        textureName = OpenTechnology.MODID + ":" + texture;
        return this;
    }
}
