package ot.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import ot.tileentities.TileEntityGenerator;

/**
 * Created by Avaja on 09.03.2017.
 */
public class GeneratorContainer extends Container {

    private InventoryPlayer inventoryPlayer;
    private TileEntityGenerator generator;

    public GeneratorContainer(InventoryPlayer inventoryPlayer, TileEntityGenerator generator) {
        this.inventoryPlayer = inventoryPlayer;
        this.generator = generator;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }
}
