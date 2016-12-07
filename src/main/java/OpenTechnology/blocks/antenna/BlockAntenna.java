package OpenTechnology.blocks.antenna;

import OpenTechnology.OpenTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by Avaja on 07.12.2016.
 */
public class BlockAntenna extends Block {

    public BlockAntenna() {
        super(Material.iron);
        setBlockName("OpenTechnology_Antenna");
        setBlockTextureName(OpenTechnology.MODID + ":antenna");
        setCreativeTab(OpenTechnology.tab);
        setHarvestLevel("pickaxe", 0);
        setHardness(5);
    }
}
