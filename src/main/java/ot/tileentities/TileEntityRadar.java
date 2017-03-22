package ot.tileentities;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.util.Point;
import ot.Config;
import ot.utils.RadarUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Avaja on 04.08.2016.
 */
public class TileEntityRadar extends TileEntity implements Analyzable, Environment, SidedComponent {

    private Node node;
    private boolean addToNetwork;

    private float yaw, target;
    private static float rotationSpeed = 3.0f;

    public TileEntityRadar() {
        node = li.cil.oc.api.Network.newNode(this, Visibility.Network).withComponent("radar").create();
        yaw = 0;
        addToNetwork = false;
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
        if(!addToNetwork){
            addToNetwork = true;
            li.cil.oc.api.Network.joinOrCreateNetwork(this);
        }

        rotationSpeed = 1f;

        if(yaw != target){
            float t = yaw - target;
            if(t < 0){
                if(yaw + rotationSpeed >= target)
                    yaw = target;
                else
                    yaw += rotationSpeed;
            }else{
                if(yaw - rotationSpeed <= target)
                    yaw = target;
                else
                    yaw -= rotationSpeed;
            }
        }
    }

    float sign(Point p1, Point p2, Point p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
    }

    boolean pointInTriangle(Point pt, Point v1, Point v2, Point v3) {
        boolean b1, b2, b3;

        b1 = sign(pt, v1, v2) < 0.0f;
        b2 = sign(pt, v2, v3) < 0.0f;
        b3 = sign(pt, v3, v1) < 0.0f;

        return ((b1 == b2) && (b2 == b3));
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

    @Callback
    public Object[] setYaw(Context machine, Arguments args){
        target = (float) args.checkDouble(0);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return new Object[]{true};
    }

    @Callback(doc = "function([distance:number]):table; Returns a list of all entities detected within the specified or the maximum range", direct = true)
    public Object[] getEntities(Context context, Arguments args) {
        List<Map> entities = new ArrayList<Map>();
        int distance = getDistance(args);
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyUsage * distance * 1.75))) {
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
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyUsage * distance * 1.0))) {
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
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyUsage * distance * 1.0))) {
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
        if(((Connector) this.node()).tryChangeBuffer(0 - (Config.radarEnergyUsage * distance * 2.0))) {
            AxisAlignedBB bounds = getBounds(distance);
            entities.addAll(RadarUtils.getItems(worldObj, xCoord, yCoord, zCoord, bounds, EntityItem.class));
            context.pause(0.5);
        }
        return new Object[] { entities.toArray() };
    }

    public float getYaw() {
        return yaw;
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

    @Override
    public boolean canConnectNode(ForgeDirection side) {
        return side == ForgeDirection.DOWN;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setFloat("yaw", yaw);
        tagCompound.setFloat("target", target);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 3, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound tagCompound = pkt.func_148857_g();
        yaw = tagCompound.getFloat("yaw");
        target = tagCompound.getFloat("target");
    }
}
