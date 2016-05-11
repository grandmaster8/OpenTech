package OpenTechnology.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Avaja on 10.05.2016.
 */
public class TeleporterContainer extends BasicContainer {

    public TeleporterContainer(InventoryPlayer inventoryPlayer, IInventory teleporterInventory) {
        bindPlayerInventory(0, -19, inventoryPlayer);

        int count = 0;
        for (int h = 0; h < 2; h++){
            for(int w = 0; w < 3; w++){
                addSlotToContainer(new Slot(teleporterInventory, count, 62 + 18 * w, 27 + 18 * h));
                count++;
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return null;
    }
}
