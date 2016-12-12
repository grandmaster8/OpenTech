package ot.driver;

import li.cil.oc.api.driver.EnvironmentProvider;
import li.cil.oc.api.driver.Item;
import li.cil.oc.api.driver.item.HostAware;
import li.cil.oc.api.driver.item.Slot;
import li.cil.oc.api.internal.Drone;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.ManagedEnvironment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ot.environment.TeslaUpgrade;
import ot.item.Items;

/**
 * Created by Avaja on 07.05.2016.
 */
public class DriverTeslaUpgrade implements Item, HostAware, EnvironmentProvider {

    public DriverTeslaUpgrade() {
    }

    @Override
    public boolean worksWith(ItemStack stack) {
        boolean ret = stack != null && stack.getItem() == Items.tesla;
        return ret;
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        return new TeslaUpgrade(host);
    }

    @Override
    public String slot(ItemStack stack) {
        return Slot.Upgrade;
    }

    @Override
    public int tier(ItemStack stack) {
        return 2;
    }

    @Override
    public NBTTagCompound dataTag(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        final NBTTagCompound nbt = stack.getTagCompound();
        if (!nbt.hasKey("oc:data")) {
            nbt.setTag("oc:data", new NBTTagCompound());
        }
        return nbt.getCompoundTag("oc:data");
    }

    @Override
    public boolean worksWith(ItemStack stack, Class<? extends EnvironmentHost> host) {
        return worksWith(stack) && Robot.class.isAssignableFrom(host) || Drone.class.isAssignableFrom(host);
    }

    @Override
    public Class<?> getEnvironment(ItemStack stack) {
        if(stack.getItem() == Items.tesla)
            return TeslaUpgrade.class;
        return null;
    }
}
