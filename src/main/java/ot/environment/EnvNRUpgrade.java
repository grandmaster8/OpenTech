package ot.environment;

import li.cil.oc.api.Network;
import li.cil.oc.api.internal.Agent;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Connector;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.ManagedEnvironment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ot.Config;
import ot.item.Items;

/**
 * Created by Avaja on 15.12.2016.
 */
public class EnvNRUpgrade extends ManagedEnvironment {

    private EnvironmentHost container;

    private int fuel = 0;
    private boolean isEmpty, isIncluded;

    public EnvNRUpgrade(EnvironmentHost container) {
        this.container = container;
        setNode(Network.newNode(this, Visibility.Network).withComponent("reactor").withConnector().create());
        isEmpty = true;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void update() {
        Connector connector = (Connector) node();
        if(!isEmpty && isIncluded){
            if(connector.changeBuffer(Config.NREfficiency) < Config.NREfficiency){
                fuel--;
                if(fuel <= 0){
                    isEmpty = true;
                    fuel = 0;
                }
            }
        }
    }

    @Callback(doc="return fuel count.")
    public Object[] count(Context context, Arguments arguments) throws Exception{
        return new Object[]{fuel};
    }

    @Callback(doc="")
    public Object[] insert(Context context, Arguments arguments) throws Exception{
        if(isEmpty){
            Agent agent = (Agent) container;
            ItemStack itemStack = agent.mainInventory().getStackInSlot(agent.selectedSlot());
            if(itemStack.getItem() == Items.uranCell){
                int f = itemStack.getMaxDamage() - itemStack.getItemDamage();
                f = f <= 0 ? 1 : f;

                float per = (f) / (itemStack.getMaxDamage() / 100f);
                fuel = (int) ((Config.NRFuelCount / 100f) * per);
                itemStack.stackSize--;
                if(itemStack.stackSize <= 0){
                    agent.mainInventory().setInventorySlotContents(agent.selectedSlot(), null);
                }
                isEmpty = false;
                return new Object[]{true};
            }else{
                return new Object[]{false, "selected slot does not contain fuel"};
            }
        }
        return new Object[]{false, "is full"};
    }

    @Callback(doc="")
    public Object[] activate(Context context, Arguments arguments){
        isIncluded = true;
        return new Object[]{true};
    }

    @Callback(doc="")
    public Object[] deactivate(Context context, Arguments arguments){
        isIncluded = false;
        return new Object[]{true};
    }

    @Override
    public void load(NBTTagCompound nbt) {
        super.load(nbt);
        if(nbt.hasKey("fuel")){
            fuel = nbt.getInteger("fuel");
            if(fuel > 0)
                isEmpty = false;
        }
    }

    @Override
    public void save(NBTTagCompound nbt) {
        super.save(nbt);
        nbt.setInteger("fuel", fuel);
    }
}
