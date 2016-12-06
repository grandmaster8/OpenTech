package OpenTechnology.environment;

import OpenTechnology.Config;
import OpenTechnology.OpenTechnology;
import li.cil.oc.api.API;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Connector;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.ManagedEnvironment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

/**
 * Created by Avaja on 07.05.2016.
 */
public class TeslaUpgrade extends ManagedEnvironment {

    private EnvironmentHost host;

    private boolean isHeat;
    private int heat;

    public TeslaUpgrade(EnvironmentHost host) {
        this.setNode(Network.newNode(this, Visibility.Network).withComponent("tesla").withConnector().create());
        this.host = host;
        isHeat = false;
        heat = 0;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }


    @Override
    public void update() {
        if(isHeat){
            heat -= Config.teslaCooling;
            if(heat <= 0)
                isHeat = false;
        }
    }

    @Callback( doc="Attack" )
    public Object[] attack(Context context, Arguments arguments) throws Exception{
        if(isHeat)
            return new Object[]{false, "tesla overheated."};

        Connector connector = (Connector) node();

        if(connector.tryChangeBuffer(Config.teslaEnergy) || !API.isPowerEnabled){
            int x = (int)host.xPosition(), y = (int)host.yPosition(), z = (int)host.zPosition();
            int minX = x - Config.maxTeslaRadius;
            int minY = y - Config.maxTeslaRadius;
            int minZ = z - Config.maxTeslaRadius;
            int maxX = x + Config.maxTeslaRadius;
            int maxY = y + Config.maxTeslaRadius;
            int maxZ = z + Config.maxTeslaRadius;

            List entities = host.world().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ));
            if(entities.size() > 0) {
                float damage = Config.teslaAllDamage / entities.size();
                for(Object o : entities){
                    EntityLivingBase livingBase = (EntityLivingBase) o;
                    livingBase.attackEntityFrom(OpenTechnology.electricDamage, damage);
                }
            }

            heat = Config.teslaMaxHeat;
            isHeat = true;
        }
        return new Object[]{};
    }

    @Callback(doc="check overheated.")
    public Object[] checkOverHeated(Context context, Arguments arguments) throws Exception{
        return new Object[]{isHeat};
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
