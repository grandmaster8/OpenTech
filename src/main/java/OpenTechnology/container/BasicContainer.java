package OpenTechnology.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Avaja on 10.05.2016.
 */
public class BasicContainer extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    protected void bindPlayerInventory(int x, int y, InventoryPlayer inventoryPlayer){
        int numRows = inventoryPlayer.getSizeInventory() / 9;
        inventoryPlayer.openInventory();
        int i = (numRows - 4) * 18;
        int j;
        int k;

        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, k + j * 9 + 9, x + 8 + k * 18, y + 103 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(inventoryPlayer, j, x + 8 + j * 18, y + 161 + i));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return null;
    }
}
