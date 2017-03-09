package ot.environment;

import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.ManagedEnvironment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ot.entity.EntityEnergyBolt;

/**
 * Created by Avaja on 09.03.2017.
 */
public class EnvTurretUpgrade  extends ManagedEnvironment {

    private EnvironmentHost container;
    private float yaw, pitch;

    public EnvTurretUpgrade(EnvironmentHost container) {
        this.container = container;
        setNode(Network.newNode(this, Visibility.Network).withComponent("turret").withConnector().create());
    }

    @Callback
    public Object[] setYaw(Context context, Arguments arguments) throws Exception {
        yaw = (float)arguments.checkDouble(0);
        return new Object[]{};
    }

    @Callback
    public Object[] setPitch(Context context, Arguments arguments) throws Exception {
        pitch = (float)arguments.checkDouble(0) * -1;
        return new Object[]{};
    }

    @Callback
    public Object[] getYaw(Context context, Arguments arguments) throws Exception {
        return new Object[]{yaw};
    }

    @Callback
    public Object[] getPitch(Context context, Arguments arguments) throws Exception {
        return new Object[]{pitch};
    }

    @Callback
    public Object[] attack(Context context, Arguments arguments) throws Exception {
        EntityEnergyBolt energyBolt = new EntityEnergyBolt(container.world());
        energyBolt.setDamage(5);
        float height = 0;
        if(container instanceof TileEntity){
            TileEntity tileEntity = (TileEntity) container;
            height = (float) tileEntity.getBlockType().getBlockBoundsMaxY();
        }
        energyBolt.setPosition(container.xPosition(), container.yPosition(), container.zPosition());
        energyBolt.setHeading(yaw, pitch);
        container.world().spawnEntityInWorld(energyBolt);
        return new Object[]{};
    }

    @Override
    public void load(NBTTagCompound nbt) {
        super.load(nbt);
        yaw = nbt.getFloat("yaw");
        pitch = nbt.getFloat("pitch");
    }

    @Override
    public void save(NBTTagCompound nbt) {
        super.save(nbt);
        nbt.setFloat("yaw", yaw);
        nbt.setFloat("pitch", pitch);
    }
}
