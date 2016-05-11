package OpenTechnology.gui;

import OpenTechnology.container.TeleporterContainer;
import OpenTechnology.tileentities.TileEntityTeleporter;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Avaja on 10.05.2016.
 */
public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case 1:
                TileEntityTeleporter teleporter = (TileEntityTeleporter) world.getTileEntity(x, y, z);
                return new TeleporterContainer(player.inventory, teleporter);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case 1:
                TileEntityTeleporter teleporter = (TileEntityTeleporter) world.getTileEntity(x, y, z);
                return new TeleporterGui(new TeleporterContainer(player.inventory, teleporter));
            default:
                return null;
        }
    }
}
