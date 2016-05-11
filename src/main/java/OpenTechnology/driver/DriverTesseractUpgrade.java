package OpenTechnology.driver;

import OpenTechnology.environment.TesseractUpgrade;
import OpenTechnology.item.Items;
import li.cil.oc.api.driver.item.HostAware;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverItem;
import net.minecraft.item.ItemStack;

/**
 * Created by Avaja on 10.05.2016.
 */
public class DriverTesseractUpgrade extends DriverItem implements HostAware{

    public DriverTesseractUpgrade() {
        super(new ItemStack[]{new ItemStack(Items.tesseract)});
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        return new TesseractUpgrade();
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
