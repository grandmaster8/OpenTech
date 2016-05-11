package OpenTechnology.tileentities;

import OpenTechnology.Config;
import OpenTechnology.utils.Utils;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import li.cil.oc.api.prefab.TileEntityEnvironment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Avaja on 11.05.2016.
 */
public class TileEntityPlayerInventoryBinder extends TileEntityEnvironment implements SimpleComponent, Analyzable {

    private String player_name;
    private EntityPlayer player;

    public TileEntityPlayerInventoryBinder() {
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).withConnector().create();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    @Override
    public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return new Node[]{node};
    }

    @Override
    public String getComponentName() {
        return "inventory_binder";
    }

    public void setPlayer(EntityPlayer player){
        this.player = player;
        player_name = player.getDisplayName();
    }

    public boolean isConnected(){
        boolean ret = player_name != null && !player_name.isEmpty();
        if (ret)
            player = worldObj.getPlayerEntityByName(player_name);
        return ret && player != null;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("player_name")){
            player_name = nbt.getString("player_name");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (player != null){
            nbt.setString("player_name", player.getDisplayName());
        }
    }

    @Callback(doc="function(slot:number)stack:table;")
    public Object[] getStackInSlot(Context context, Arguments arguments) throws Exception{
        if (!isConnected())
            return new Object[]{false, "player not connected"};

        int slot = arguments.checkInteger(0);
        if (player != null){
            if (slot < 0 || slot > player.inventory.getSizeInventory())
                return new Object[]{false, "invalid slot"};
            ItemStack stack = player.inventory.getStackInSlot(slot);
            if(stack == null)
                return new Object[]{"null"};
            return new Object[]{Utils.createStringTable("unlocalizedname", stack.getUnlocalizedName(), "name", stack.getDisplayName(), "id", Item.getIdFromItem(stack.getItem()), "stacksize", stack.stackSize, "damage", stack.getItemDamage())};
        }
        return new Object[]{false};
    }

    @Callback(doc="function(side:number, slot:number, count:number);")
    public Object[] dropIntoInventory(Context context, Arguments arguments) throws Exception{
        int side = arguments.checkInteger(0);
        int slot = arguments.checkInteger(1);
        int count = arguments.checkInteger(2);

        ForgeDirection direction = ForgeDirection.getOrientation(side);

        if (!isConnected())
            return new Object[]{false, "player not connected"};

        if (slot < 0 || slot > player.inventory.getSizeInventory())
            return new Object[]{false, "invalid slot"};

        if (count <= 0)
            return new Object[]{false, "invalid count"};

        TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);

        if (tileEntity instanceof IInventory){
            ItemStack stack = player.inventory.getStackInSlot(slot);
            ItemStack split = stack.splitStack(count);
            Connector connector = (Connector) node;
            double energy = Utils.distance(xCoord, yCoord, zCoord, player.posX, player.posY, player.posZ) * Config.binderEnergyFactor;

            if (connector.tryChangeBuffer(-energy)) {
                if (Utils.addToIInventory((IInventory) tileEntity, split)) {
                    if (stack.stackSize <= 0)
                        player.inventory.setInventorySlotContents(slot, null);
                    return new Object[]{true};
                }else{
                    stack.stackSize += split.stackSize;
                }
            }
            else
                return new Object[]{false, "not enough energy"};
        }


        return new Object[]{false};
    }
}
