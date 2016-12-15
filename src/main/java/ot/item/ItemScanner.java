package ot.item;

import li.cil.oc.api.driver.item.Chargeable;
import li.cil.oc.common.block.RobotProxy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import ot.Config;
import ot.OpenTechnology;

import java.util.List;

/**
 * Created by Avaja on 14.12.2016.
 */
public class ItemScanner extends Item implements Chargeable {


    public ItemScanner() {
        setCreativeTab(OpenTechnology.tab);
        setTextureName(OpenTechnology.MODID + ":scanner");
        setUnlocalizedName(OpenTechnology.MODID + "_scanner");
        setMaxDamage(Config.scannerUsageCount);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean b) {
        list.add(EnumChatFormatting.YELLOW+ StatCollector.translateToLocal("lore.scanner.charge") + (itemStack.getMaxDamage() - itemStack.getItemDamage()) + "/" + itemStack.getMaxDamage());
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float dx, float dy, float dz) {
        if(itemStack.getMaxDamage() - itemStack.getItemDamage() >= Config.scannerUsageCost){
            itemStack.setItemDamage(itemStack.getItemDamage() + Config.scannerUsageCost);
        }else{
            return false;
        }

        if(!world.isRemote)
            return false;

        int minX = x - Config.scannerWidth / 2;
        int minY = y - Config.scannerHeight / 2;
        int minZ = z - Config.scannerWidth / 2;

        int maxX = x + Config.scannerWidth / 2;
        int maxY = y + Config.scannerHeight / 2;
        int maxZ = z + Config.scannerWidth / 2;

        for(int ix = minX; ix < maxX; ix++){
            for(int iz = minZ; iz < maxZ; iz++){
                for(int iy = minY; iy < maxY; iy++){
                    Block block  = world.getBlock(ix, iy, iz);
                    if(block instanceof RobotProxy){
                        entityPlayer.addChatComponentMessage(new ChatComponentText(String.format(StatCollector.translateToLocal("chat.scanner"), ix, iy, iz)));
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canCharge(ItemStack stack) {
        return true;
    }

    @Override
    public double charge(ItemStack stack, double amount, boolean simulate) {
        if(stack.getItemDamage() >= amount){
            stack.setItemDamage((int) (stack.getItemDamage() - amount));
            return 0;
        }else{
            int delta = stack.getMaxDamage() - stack.getItemDamage();
            stack.setItemDamage(0);
            return amount - delta;
        }
    }
}
