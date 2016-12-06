package OpenTechnology.environment;

import OpenTechnology.render.WorldRender;
import OpenTechnology.render.effects.TeslaLightningEffect;
import li.cil.oc.api.Network;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.ManagedEnvironment;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Avaja on 07.05.2016.
 */
public class TeslaUpgrade extends ManagedEnvironment {

    private EnvironmentHost host;

    private boolean isHeat;
    private int counter;

    public TeslaUpgrade(EnvironmentHost host) {
        this.setNode(Network.newNode(this, Visibility.Network).withComponent("tesla").withConnector().create());
        this.host = host;
        isHeat = false;
        counter = 0;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }


    @Override
    public void update() {
        counter++;
        if(counter == 40){
            WorldRender.addRender(new TeslaLightningEffect((Robot)host, Minecraft.getMinecraft().thePlayer));
            counter = 0;
        }
    }

    @Callback( doc="Attack in radius." )
    public Object[] attack(Context context, Arguments arguments) throws Exception{

        return new Object[]{};
    }

    @Override
    public void load( NBTTagCompound nbt ) {
        super.load( nbt );
    }

    @Override
    public void save( NBTTagCompound nbt ) {
        super.save( nbt );
    }
}
