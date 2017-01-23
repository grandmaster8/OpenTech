package ot.item;

import li.cil.oc.api.driver.item.Chargeable;
import li.cil.oc.common.block.RobotProxy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
        setMaxDamage(100);
        setHasSubtypes(true);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer entityPlayer, List list, boolean b) {
        list.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("lore.scanner.charge") +
                (getEnergy(stack) + "/" + Config.scannerEnergyCount));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float dx, float dy, float dz) {
        if(!world.isRemote && getEnergy(stack) >= Config.scannerUsageCost){
            setEnergy(stack, getEnergy(stack) - Config.scannerUsageCost);
            return true;
        }

        if(getEnergy(stack) < Config.scannerUsageCost)
            return true;

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
                        entityPlayer.addChatComponentMessage(new ChatComponentText(String.format(StatCollector.translateToLocal("chat.scanner"),
                                ix, iy, iz)));
                    }
                }
            }
        }
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return (Config.scannerEnergyCount - getEnergy(stack)) / (double)Config.scannerEnergyCount;
    }

    protected int getEnergy(ItemStack stack){
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("energy")){
            return stack.getTagCompound().getInteger("energy");
        }else{
            NBTTagCompound nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
            nbt.setInteger("energy", 0);
        }
        return 0;
    }

    protected void setEnergy(ItemStack stack, int energyCount){
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("energy")){
            stack.getTagCompound().setInteger("energy", energyCount);
        }else{
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("energy", energyCount);
            stack.setTagCompound(nbt);
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        super.getSubItems(item, creativeTabs, list);
        ItemStack charged = new ItemStack(this, 1, 0);
        setEnergy(charged, Config.scannerEnergyCount);
        list.add(charged);
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, damage);
    }

    @Override
    public boolean canCharge(ItemStack stack) {
        return true;
    }

    @Override
    public double charge(ItemStack stack, double amount, boolean simulate) {
        int energy = Config.scannerEnergyCount - getEnergy(stack);
        if(energy >= amount){
            setEnergy(stack, (int) (getEnergy(stack) + amount));
            return 0;
        }else{
            setEnergy(stack, Config.scannerEnergyCount);
            return amount - energy;
        }
    }


}
