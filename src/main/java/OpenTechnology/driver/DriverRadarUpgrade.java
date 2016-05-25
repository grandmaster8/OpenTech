package OpenTechnology.driver;

import OpenTechnology.environment.RadarUpgrade;
import OpenTechnology.item.Items;
import li.cil.oc.api.driver.item.HostAware;
import li.cil.oc.api.driver.item.Slot;
import li.cil.oc.api.internal.Drone;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverItem;
import net.minecraft.item.ItemStack;

/**
 * Created by Avaja on 21.05.2016.
 */
public class DriverRadarUpgrade extends DriverItem implements HostAware {

    public DriverRadarUpgrade() {
        super(new ItemStack[]{new ItemStack(Items.radar)});
    }

    @Override
    public boolean worksWith(ItemStack stack, Class<? extends EnvironmentHost> host) {
        return worksWith(stack) && host == Robot.class  || host == Drone.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        return new RadarUpgrade(host);
    }

    @Override
    public String slot(ItemStack stack) {
        return Slot.Upgrade;
    }
}
