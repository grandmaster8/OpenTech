package OpenTechnology.tileentities;

import OpenTechnology.environment.TesseractUpgrade;
import OpenTechnology.packet.PacketPlayerPosition;
import OpenTechnology.packet.PacketTeleporter;
import OpenTechnology.proxy.CommonProxy;
import OpenTechnology.utils.InventoryUtils;
import OpenTechnology.utils.Utils;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import li.cil.oc.api.prefab.TileEntityEnvironment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Avaja on 10.05.2016.
 */
public class TileEntityTeleporter extends TileEntityEnvironment implements SimpleComponent, Analyzable, SidedEnvironment, IInventory {
    public static HashMap<String, TileEntityTeleporter> teleporterList = new HashMap<String, TileEntityTeleporter>();

    //Fields
    private boolean connect;
    protected ItemStack[] inventory;
    private String inventoryName;

    public TileEntityTeleporter() {
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).withConnector(10000).create();
        inventoryName = "TeleporterInventory";
        inventory = new ItemStack[6];
        connect = false;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!connect && node != null){
            teleporterList.put(node.address(), this);
            connect = true;
        }
    }

    @Override
    public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return new Node[]{node};
    }

    @Override
    public Node sidedNode(ForgeDirection side) {
        if (side != ForgeDirection.UP) {
            return node;
        } else {
            return null;
        }
    }

    @Override
    public boolean canConnect(ForgeDirection side) {
        if (side != ForgeDirection.UP) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getComponentName() {
        return "teleporter";
    }

    @Callback(doc="function(uuid:string, slot:number, count:number)")
    public Object[] transferOfSlot(Context context, Arguments arguments) throws Exception {
        String uuid = arguments.checkString(0);
        int slot = arguments.checkInteger(1);
        int count = arguments.checkInteger(2);

        if (slot < 0 || slot > getSizeInventory())
            return new Object[]{false, "invalid slot"};

        if (teleporterList.containsKey(uuid)) {
            TileEntityTeleporter teleporter = teleporterList.get(uuid);
            Connector connector = (Connector) node;

            ItemStack stack = getStackInSlot(slot);
            if (stack != null && stack.stackSize >= count) {
                double dist = Utils.distance(node, teleporter.node);
                double energy = 4 * (dist / 10) * dist * count;
                if (teleporter.addToInventory(stack.splitStack(count))) {
                    if (connector.tryChangeBuffer(-energy)) {
                        if (stack.stackSize <= 0)
                            setInventorySlotContents(slot, null);

                        CommonProxy.wrapper.sendToDimension(new PacketTeleporter(teleporter.xCoord, teleporter.yCoord, teleporter.zCoord - 1), teleporter.worldObj.provider.dimensionId);
                        teleporter.worldObj.playSoundEffect(teleporter.xCoord, teleporter.yCoord, teleporter.zCoord, "mob.endermen.portal", 1.0F, 1.0F);
                        return new Object[]{true};
                    }else{
                        return new Object[]{false, "not enough energy"};
                    }
                }else{
                    return new Object[]{false, "inventory full"};
                }
            }
        }else if(TesseractUpgrade.tesseractList.containsKey(uuid)){
            TesseractUpgrade tesseract = TesseractUpgrade.tesseractList.get(uuid);

            if (tesseract.getRobot() == null)
                return new Object[]{false, "tesseract not sync"};

            Connector connector = (Connector) node;

            ItemStack stack = getStackInSlot(slot);
            if (stack != null && stack.stackSize >= count) {
                double dist = Utils.distance(xCoord, yCoord, zCoord, tesseract.getRobot().xPosition(), tesseract.getRobot().yPosition(), tesseract.getRobot().zPosition());
                double energy = 4 * (dist / 10) * dist * count;
                if (tesseract.addToInventory(stack.splitStack(count))) {
                    if (connector.tryChangeBuffer(-energy)) {
                        if (stack.stackSize <= 0)
                            setInventorySlotContents(slot, null);

                        CommonProxy.wrapper.sendToDimension(new PacketTeleporter((int)tesseract.getRobot().xPosition(), (int)tesseract.getRobot().yPosition(), (int)tesseract.getRobot().zPosition()), tesseract.getRobot().world().provider.dimensionId);
                        tesseract.getRobot().world().playSoundEffect((int)tesseract.getRobot().xPosition(), (int)tesseract.getRobot().yPosition(), (int)tesseract.getRobot().zPosition(), "mob.endermen.portal", 1.0F, 1.0F);
                        return new Object[]{true};
                    }else{
                        return new Object[]{false, "not enough energy"};
                    }
                }else{
                    return new Object[]{false, "inventory full"};
                }
            }
        }

        return new Object[]{false};
    }

    @Callback(doc="function(uuid:string)")
    public Object[] teleport(Context context, Arguments arguments) throws Exception {
        String uuid = arguments.checkString(0);

        if (teleporterList.containsKey(uuid)) {

            TileEntityTeleporter teleporter = teleporterList.get(uuid);
            List<Entity> entityList = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 1, yCoord + 2, zCoord + 1));
            if (entityList.size() > 0) {

                Connector connector = (Connector) node;

                for (Entity entity : entityList) {
                    double dist = entity.getDistance(teleporter.xCoord, teleporter.yCoord, teleporter.zCoord);
                    double factor = (dist / 10) > 0 ? dist / 10 : 1;
                    double energy = dist * factor;
                    if (connector.tryChangeBuffer(-energy)) {
                        if (entity instanceof EntityPlayer) {
                            EntityPlayer player = (EntityPlayer) entity;
                            player.setWorld(teleporter.worldObj);
                            player.setPosition(teleporter.xCoord - 0.5, teleporter.yCoord + 1.3, teleporter.zCoord - 0.5);
                            CommonProxy.wrapper.sendTo(new PacketPlayerPosition(teleporter.worldObj.provider.dimensionId, teleporter.xCoord + 0.5, teleporter.yCoord + 1, teleporter.zCoord + 0.5), (EntityPlayerMP) player);
                            CommonProxy.wrapper.sendToDimension(new PacketTeleporter(teleporter.xCoord, teleporter.yCoord + 1, teleporter.zCoord), teleporter.worldObj.provider.dimensionId);
                        } else {
                            entity.setWorld(teleporter.worldObj);
                            entity.setPosition(teleporter.xCoord + 0.5, teleporter.yCoord + 1.3, teleporter.zCoord + 0.5);
                            CommonProxy.wrapper.sendToDimension(new PacketTeleporter(teleporter.xCoord, teleporter.yCoord + 1, teleporter.zCoord), teleporter.worldObj.provider.dimensionId);
                        }
                    } else {
                        return new Object[]{false, "not enough energy"};
                    }
                }
                worldObj.playSoundEffect(teleporter.xCoord, teleporter.yCoord, teleporter.zCoord, "mob.endermen.portal", 1.0F, 1.0F);
                return new Object[]{true};
            }
        }
        return new Object[]{false};
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        try {
            inventory = InventoryUtils.readItemStacks("inventory", nbt);
            if (inventory == null || inventory.length < 6)
                inventory = new ItemStack[6];
        } catch (Exception e) {
            inventory = new ItemStack[6];
            e.printStackTrace();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        try {
            InventoryUtils.writeItemStacks("inventory", nbt, inventory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addToInventory(ItemStack stack) {
        return Utils.addToIInventory(this, stack);
    }

    @Override
    public String getInventoryName() {
        return inventoryName;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return false;
    }


    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot >= 0 && slot < inventory.length) {
            inventory[slot] = stack;
        }
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot >= 0 && slot < inventory.length) {
            return inventory[slot];
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        if (slot >= 0 && slot < inventory.length) {
            ItemStack ret = inventory[slot].splitStack(count);
            if (inventory[slot].stackSize <= 0)
                inventory[slot] = null;
            return ret;
        }
        return null;
    }
}
