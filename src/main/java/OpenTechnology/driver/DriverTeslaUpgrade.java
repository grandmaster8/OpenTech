package OpenTechnology.driver;

import OpenTechnology.environment.TeslaUpgrade;
import OpenTechnology.item.Items;
import li.cil.oc.api.driver.item.Container;
import li.cil.oc.api.driver.item.HostAware;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverItem;
import net.minecraft.item.ItemStack;

/**
 * Created by Avaja on 07.05.2016.
 */
public class DriverTeslaUpgrade extends DriverItem implements HostAware {

    public DriverTeslaUpgrade() {
        super(new ItemStack(Items.tesla));
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        return new TeslaUpgrade();
    }

    @Override
    public String slot(ItemStack stack) {
        return "upgrade";
    }

    @Override
    public boolean worksWith(ItemStack stack, Class<? extends EnvironmentHost> host) {
        return worksWith(stack) && host == Robot.class;
    }
}
