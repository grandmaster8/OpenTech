package ot.driver;

import li.cil.oc.api.driver.item.HostAware;
import li.cil.oc.api.driver.item.Slot;
import li.cil.oc.api.internal.Drone;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverItem;
import net.minecraft.item.ItemStack;
import ot.environment.EnvTurretUpgrade;
import ot.item.Items;

/**
 * Created by Avaja on 09.03.2017.
 */
public class DriverTurretUpgrade extends DriverItem implements HostAware {

    public DriverTurretUpgrade() {
        super(new ItemStack[]{new ItemStack(Items.turret)});
    }

    @Override
    public boolean worksWith(ItemStack stack, Class<? extends EnvironmentHost> host) {
        return worksWith(stack) && Robot.class.isAssignableFrom(host) || Drone.class.isAssignableFrom(host);
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        return new EnvTurretUpgrade(host);
    }

    @Override
    public String slot(ItemStack stack) {
        return Slot.Upgrade;
    }

    @Override
    public int tier(ItemStack stack) {
        return 2;
    }

}
