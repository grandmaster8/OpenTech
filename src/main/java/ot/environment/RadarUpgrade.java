package ot.environment;

import li.cil.oc.api.Network;
import li.cil.oc.api.driver.DeviceInfo;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Connector;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.ManagedEnvironment;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import ot.Config;
import ot.utils.RadarUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Avaja on 21.05.2016.
 */
public class RadarUpgrade extends ManagedEnvironment implements DeviceInfo {
    private EnvironmentHost container;

    public RadarUpgrade(EnvironmentHost host) {
        this.container = host;
        setNode(Network.newNode(this, Visibility.Network).withComponent("radar").withConnector().create());
    }

    private int getDistance(Arguments args) {
        if(args.isInteger(0)) {
            return args.checkInteger(0);
        } else {
            return Config.radarRange;
        }
    }

    private AxisAlignedBB getBounds(int d) {
        int distance = Math.min(d, Config.radarRange);
        if(distance < 1) {
            distance = 1;
        }
        return AxisAlignedBB.
                getBoundingBox((float) container.xPosition(), (float) container.yPosition(), (float) container.zPosition(), (float) container.xPosition() + 1, (float) container.yPosition() + 1, (float) container.zPosition() + 1).
                expand(distance, distance, distance);
    }

    @Callback(doc = "function([distance:number]):table; Returns a list of all entities detected within the specified or the maximum range", direct = true)
    public Object[] getEntities(Context context, Arguments args) {
        List<Map> entities = new ArrayList<Map>();
        int distance = getDistance(args);
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyUsage * distance * 1.75))) {
            AxisAlignedBB bounds = getBounds(distance);
            entities.addAll(RadarUtils.getEntities(container.world(), (int) container.xPosition(), (int) container.yPosition(), (int) container.zPosition(), bounds, EntityPlayer.class, Config.radarRange));
            entities.addAll(RadarUtils.getEntities(container.world(), (int) container.xPosition(), (int) container.yPosition(), (int) container.zPosition(), bounds, EntityLiving.class, Config.radarRange));
            context.pause(0.5);
        }
        return new Object[] { entities.toArray() };
    }

    @Callback(doc = "function([distance:number]):table; Returns a list of all players detected within the specified or the maximum range", direct = true)
    public Object[] getPlayers(Context context, Arguments args) {
        List<Map> entities = new ArrayList<Map>();
        int distance = getDistance(args);
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyUsage * distance * 1.0))) {
            AxisAlignedBB bounds = getBounds(distance);
            entities.addAll(RadarUtils.getEntities(container.world(), (int) container.xPosition(), (int) container.yPosition(), (int) container.zPosition(), bounds, EntityPlayer.class, Config.radarRange));
            context.pause(0.5);
        }
        return new Object[] { entities.toArray() };
    }

    @Callback(doc = "function([distance:number]):table; Returns a list of all mobs detected within the specified or the maximum range", direct = true)
    public Object[] getMobs(Context context, Arguments args) {
        List<Map> entities = new ArrayList<Map>();
        int distance = getDistance(args);
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyUsage * distance * 1.0))) {
            AxisAlignedBB bounds = getBounds(distance);
            entities.addAll(RadarUtils.getEntities(container.world(), (int) container.xPosition(), (int) container.yPosition(), (int) container.zPosition(), bounds, EntityLiving.class, Config.radarRange));
            context.pause(0.5);
        }
        return new Object[] { entities.toArray() };
    }

    @Callback(doc = "function([distance:number]):table; Returns a list of all items detected within the specified or the maximum range", direct = true)
    public Object[] getItems(Context context, Arguments args) {
        List<Map> entities = new ArrayList<Map>();
        int distance = getDistance(args);
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyUsage * distance * 2.0))) {
            AxisAlignedBB bounds = getBounds(distance);
            entities.addAll(RadarUtils.getItems(container.world(), (int) container.xPosition(), (int) container.yPosition(), (int) container.zPosition(), bounds, EntityItem.class));
            context.pause(0.5);
        }
        return new Object[] { entities.toArray() };
    }

    @Override
    public Map<String, String> getDeviceInfo() {
        Map<String, String> info = new HashMap<String, String>();
        return info;
    }
}
