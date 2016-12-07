package OpenTechnology.blocks.antenna;

import OpenTechnology.OpenTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by Avaja on 07.12.2016.
 */
public class BlockCell extends Block {

    public BlockCell() {
        super(Material.iron);
        setBlockName("OpenTechnology_Cell");
        setBlockTextureName(OpenTechnology.MODID + ":antennaCell");
        setCreativeTab(OpenTechnology.tab);
        setHarvestLevel("pickaxe", 0);
        setHardness(5);
    }
}
