package ot;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ot.container.EnergyControllerContainer;
import ot.render.gui.GuiEnergyController;
import ot.tileentities.ic.TileEntityEnergyController;

/**
 * Created by Avaja on 31.01.2017.
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID){
            case 0:
                return new EnergyControllerContainer(player.inventory, (TileEntityEnergyController) world.getTileEntity(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID){
            case 0:
                return new GuiEnergyController(new EnergyControllerContainer(player.inventory, (TileEntityEnergyController) world.getTileEntity(x, y, z)), (TileEntityEnergyController) world.getTileEntity(x, y, z));
        }
        return null;
    }
}
