package ot.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import ot.tileentities.TileEntityEnergyController;

/**
 * Created by Avaja on 31.01.2017.
 */
public class EnergyControllerContainer extends Container {

    private TileEntityEnergyController energyController;

    public EnergyControllerContainer(InventoryPlayer inventoryPlayer, TileEntityEnergyController energyController) {
        this.energyController = energyController;

        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for(Object iCrafting : crafters){
            ICrafting crafting = (ICrafting) iCrafting;
            crafting.sendProgressBarUpdate(this, 0, energyController.getEnergyInputBuffer());
            crafting.sendProgressBarUpdate(this, 1, energyController.getEnergyOutputBuffer());
        }
    }

    @Override
    public void updateProgressBar(int id, int value) {
        super.updateProgressBar(id, value);
        switch(id){
            case 0:
                energyController.setEnergyInputBuffer(value);
                break;
            case 1:
                energyController.setEnergyOutputBuffer(value);
                break;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return true;
    }
}
