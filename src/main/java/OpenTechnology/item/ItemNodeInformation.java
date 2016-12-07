package OpenTechnology.item;

import li.cil.oc.api.API;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Avaja on 07.12.2016.
 */
public abstract class ItemNodeInformation extends Item {
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {

        list.add("zzz....");
        list.add("Tier: " + (API.driver.driverFor(stack).tier(stack) + 1));
        if(stack.hasTagCompound()){
            NBTTagCompound tagCompound = stack.getTagCompound();
            if(tagCompound.hasKey("oc:data")){
                NBTTagCompound data = tagCompound.getCompoundTag("oc:data");
                if(data.hasKey("node")){
                    list.add("§8" + data.getCompoundTag("node").getString("address").substring(0, 13) + "...§7");
                }
            }
        }
    }
}
