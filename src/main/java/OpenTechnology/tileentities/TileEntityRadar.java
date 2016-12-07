package OpenTechnology.tileentities;

import OpenTechnology.Config;
import OpenTechnology.utils.RadarUtils;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Avaja on 04.08.2016.
 */
public class TileEntityRadar extends TileEntity implements Analyzable, Environment {

    private Node node;
    private float rotation;
    private static float rotationSpeed = 3.0f;

    public TileEntityRadar() {
        node = li.cil.oc.api.Network.newNode(this, Visibility.Network).withComponent("radar").create();
        rotation = 0;
    }

    private int getDistance(Arguments args) {
        if(args.isInteger(0)) {
            return args.checkInteger(0);
        } else {
            return Config.radarRange;
        }
    }

    @Override
    public void updateEntity() {
        rotation = (rotation + rotationSpeed) % 360;
    }

    public int getFacing() {
        return (worldObj == null) ? 0 : this.getBlockMetadata();
    }

    private AxisAlignedBB getBounds(int d) {
        int distance = Math.min(d, Config.radarRange);
        if(distance < 1) {
            distance = 1;
        }
        return AxisAlignedBB.
                getBoundingBox((float) xCoord, (float) yCoord, (float) zCoord, (float) xCoord + 1, yCoord + 1, (float) yCoord + 1).
                expand(distance, distance, distance);
    }

    @Callback(doc = "function([distance:number]):table; Returns a list of all entities detected within the specified or the maximum range", direct = true)
    public Object[] getEntities(Context context, Arguments args) {
        List<Map> entities = new ArrayList<Map>();
        int distance = getDistance(args);
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyCost * distance * 1.75))) {
            AxisAlignedBB bounds = getBounds(distance);
            entities.addAll(RadarUtils.getEntities(worldObj, xCoord, yCoord, zCoord, bounds, EntityPlayer.class, Config.radarRange));
            entities.addAll(RadarUtils.getEntities(worldObj, xCoord, yCoord, zCoord, bounds, EntityLiving.class, Config.radarRange));
            context.pause(0.5);
        }
        return new Object[] { entities.toArray() };
    }

    @Callback(doc = "function([distance:number]):table; Returns a list of all players detected within the specified or the maximum range", direct = true)
    public Object[] getPlayers(Context context, Arguments args) {
        List<Map> entities = new ArrayList<Map>();
        int distance = getDistance(args);
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyCost * distance * 1.0))) {
            AxisAlignedBB bounds = getBounds(distance);
            entities.addAll(RadarUtils.getEntities(worldObj, xCoord, yCoord, zCoord, bounds, EntityPlayer.class, Config.radarRange));
            context.pause(0.5);
        }
        return new Object[] { entities.toArray() };
    }

    @Callback(doc = "function([distance:number]):table; Returns a list of all mobs detected within the specified or the maximum range", direct = true)
    public Object[] getMobs(Context context, Arguments args) {
        List<Map> entities = new ArrayList<Map>();
        int distance = getDistance(args);
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyCost * distance * 1.0))) {
            AxisAlignedBB bounds = getBounds(distance);
            entities.addAll(RadarUtils.getEntities(worldObj, xCoord, yCoord, zCoord, bounds, EntityLiving.class, Config.radarRange));
            context.pause(0.5);
        }
        return new Object[] { entities.toArray() };
    }

    @Callback(doc = "function([distance:number]):table; Returns a list of all items detected within the specified or the maximum range", direct = true)
    public Object[] getItems(Context context, Arguments args) {
        List<Map> entities = new ArrayList<Map>();
        int distance = getDistance(args);
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyCost * distance * 2.0))) {
            AxisAlignedBB bounds = getBounds(distance);
            entities.addAll(RadarUtils.getItems(worldObj, xCoord, yCoord, zCoord, bounds, EntityItem.class));
            context.pause(0.5);
        }
        return new Object[] { entities.toArray() };
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return new Node[]{node};
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public void onConnect(Node node) {

    }

    @Override
    public void onDisconnect(Node node) {

    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void invalidate() {
        super.invalidate();
        if (node != null) node.remove();
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        if (node != null) node.remove();
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (node != null && node.host() == this) {
            node.load(nbt.getCompoundTag("oc:node"));
        }
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (node != null && node.host() == this) {
            final NBTTagCompound nodeNbt = new NBTTagCompound();
            node.save(nodeNbt);
            nbt.setTag("oc:node", nodeNbt);
        }
    }
}
