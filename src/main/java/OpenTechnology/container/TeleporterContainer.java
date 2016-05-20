package OpenTechnology.container;

import OpenTechnology.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Avaja on 10.05.2016.
 */
public class TeleporterContainer extends BasicContainer {

    public TeleporterContainer( InventoryPlayer inventoryPlayer, IInventory teleporterInventory ) {

        int count = 0;
        for ( int h = 0; h < 2; h++ ){
            for( int w = 0; w < 3; w++ ){
                addSlotToContainer( new Slot( teleporterInventory, count, 62 + 18 * w, 27 + 18 * h ) );
                count++;
            }
        }

        bindPlayerInventory( 0, -19, inventoryPlayer );
    }

    @Override
    public ItemStack transferStackInSlot( EntityPlayer player, int slot ) {
        if( slot < 6 ){
            if ( player.inventory.addItemStackToInventory( getSlot( slot ).getStack(  ) ) ){
                getSlot( slot ).putStack( null );
            }
        }else{
            if ( Utils.addToIInventory( getSlot( 0 ).inventory, getSlot( slot ).getStack(  ) ) ){
                getSlot( slot ).putStack( null );
            }
        }
        return null;
    }
}
