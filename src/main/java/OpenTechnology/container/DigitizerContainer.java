package OpenTechnology.container;

import OpenTechnology.tileentities.TileEntityDigitizer;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Avaja on 28.05.2016.
 */
public class DigitizerContainer extends BasicContainer {


    public DigitizerContainer(EntityPlayer player, TileEntityDigitizer digitizer) {

        bindPlayerInventory(0, -19, player.inventory);
    }
}
