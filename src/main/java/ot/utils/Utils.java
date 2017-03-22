package ot.utils;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import li.cil.oc.api.network.Node;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Avaja on 07.05.2016.
 */
public class Utils {

    public static List getEntitiesInBound( Class c, World world, int minx, int miny, int minz, int maxx, int maxy, int maxz ){
        return world.getEntitiesWithinAABB( c, AxisAlignedBB.getBoundingBox( minx, miny, minz, maxx, maxy, maxz ) );
    }

    public static String getForgeName(Item item){
        FMLControlledNamespacedRegistry<Item> items = GameData.getItemRegistry();
        return items.getNameForObject(item);
    }

    public static double distance( Node n1, Node n2 ){
        TileEntity te1 = (  TileEntity  ) n1.host(  );
        TileEntity te2 = (  TileEntity  ) n2.host(  );
        if(  te1 != null && te2 != null  ){
            double dx = Math.pow(  te2.xCoord - te1.xCoord, 2  );
            double dy = Math.pow(  te2.yCoord - te1.yCoord, 2  );
            double dz = Math.pow(  te2.zCoord - te1.zCoord, 2  );
            double res = dx + dy + dz;
            return Math.abs(  Math.sqrt(  res  ) );
        }
        return 0;
    }

    public static MovingObjectPosition getLookAt(EntityLivingBase livingBase){
        World world = livingBase.worldObj;
        Vec3 origin = Vec3.createVectorHelper(livingBase.posX, livingBase.posY + livingBase.getEyeHeight(), livingBase.posZ);
        Vec3 direction = livingBase.getLookVec();
        Vec3 lookAt = origin.addVector(direction.xCoord * 20, direction.yCoord * 20, direction.zCoord * 20);
        return world.rayTraceBlocks(origin, lookAt);
    }

    public static double distance( double x1,double y1,double z1, double x2,double y2,double z2 ){
        double dx = Math.pow( x2 - x1, 2 );
        double dy = Math.pow( y2 - y1, 2 );
        double dz = Math.pow( z2 - z1, 2 );
        double res = dx + dy + dz;
        return Math.abs( Math.sqrt( res ) );
    }

    public static boolean addToIInventory( IInventory inventory, ItemStack stack ){
        stack = stack.copy();
        int size = inventory.getSizeInventory(  );
        ItemStack[] tmp = new ItemStack[inventory.getSizeInventory()];
        for (int i = 0; i < inventory.getSizeInventory(); i++){
            tmp[i] = inventory.getStackInSlot(i);
        }
        for ( int i = 0; i < size; i++ ){
            ItemStack inventoryStack = tmp[i];
            if ( inventoryStack != null && stack != null && inventoryStack.isItemEqual( stack ) ){
                int delta = inventoryStack.getMaxStackSize(  ) - inventoryStack.stackSize;
                if ( delta >= stack.stackSize ) {
                    inventoryStack.stackSize += stack.stackSize;
                    for (int q = 0; q < tmp.length; q++){
                        inventory.setInventorySlotContents(q, tmp[q]);
                    }
                    return true;
                }else{
                    inventoryStack.stackSize = inventoryStack.getMaxStackSize(  );
                    stack.stackSize -= delta;
                }
            }else if ( inventoryStack == null ){
                tmp[i] = stack;
                for (int q = 0; q < tmp.length; q++){
                    inventory.setInventorySlotContents(q, tmp[q]);
                }
                return true;
            }
        }
        return false;
    }

    public static int getForgeDirectionId(ForgeDirection forgeDirection){
        int id = 0;
        for(ForgeDirection direction : ForgeDirection.values()){
            if(direction.equals(forgeDirection))
                return id;
            id++;
        }
        return 7;
    }

    public static ForgeDirection getDirection(int side){
        switch(side){
            case 0:
                return ForgeDirection.SOUTH;
            case 1:
                return ForgeDirection.WEST;
            case 2:
                return ForgeDirection.NORTH;
            case 3:
                return ForgeDirection.EAST;
        }
        return null;
    }

    public static ForgeDirection getLookDirection(EntityLivingBase livingBase){
        double side = MathHelper.floor_double((double)(livingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        switch((int) side){
            case 0:
                return ForgeDirection.SOUTH;
            case 1:
                return ForgeDirection.WEST;
            case 2:
                return ForgeDirection.NORTH;
            case 3:
                return ForgeDirection.EAST;
        }
        return null;
    }

    public static Vec3 getLook(EntityLivingBase livingBase) {
        float f1;
        float f2;
        float f3;
        float f4;

        f1 = MathHelper.cos(-livingBase.rotationYaw * 0.017453292F - (float)Math.PI);
        f2 = MathHelper.sin(-livingBase.rotationYaw * 0.017453292F - (float)Math.PI);
        f3 = -MathHelper.cos(-livingBase.rotationPitch * 0.017453292F);
        f4 = MathHelper.sin(-livingBase.rotationPitch * 0.017453292F);
        return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
    }

    public static boolean addToIInventory( IInventory inventory, int min, ItemStack stack ){
        stack = stack.copy();
        int size = inventory.getSizeInventory(  );
        ItemStack[] tmp = new ItemStack[inventory.getSizeInventory()];
        for (int i = 0; i < inventory.getSizeInventory(); i++){
            tmp[i] = inventory.getStackInSlot(i);
        }
        for ( int i = min; i < size; i++ ){
            ItemStack inventoryStack = tmp[i];
            if ( inventoryStack != null && stack != null && inventoryStack.isItemEqual( stack ) ){
                int delta = inventoryStack.getMaxStackSize(  ) - inventoryStack.stackSize;
                if ( delta >= stack.stackSize ) {
                    inventoryStack.stackSize += stack.stackSize;
                    for (int q = 0; q < tmp.length; q++){
                        inventory.setInventorySlotContents(q, tmp[q]);
                    }
                    return true;
                }else{
                    inventoryStack.stackSize = inventoryStack.getMaxStackSize(  );
                    stack.stackSize -= delta;
                }
            }else if ( inventoryStack == null ){
                tmp[i] = stack;
                for (int q = 0; q < tmp.length; q++){
                    inventory.setInventorySlotContents(q, tmp[q]);
                }
                return true;
            }
        }
        return false;
    }

    public static HashMap createStringTable( Object... objects ){
        if ( objects != null && objects.length >= 2 ){
            HashMap<String, Object> hashMap = new HashMap<String, Object>(  );
            for ( int i = 0; i < objects.length; i += 2 ){
                hashMap.put( ( String ) objects[i], objects[i + 1] );
            }
            return hashMap;
        }
        return null;
    }

    public static Map createIndexTable( Object... objects ){
        if ( objects != null && objects.length > 0 ){
            HashMap<Integer, Object> hashMap = new HashMap<Integer, Object>(  );
            for ( int i = 0; i < objects.length; i ++ ){
                hashMap.put( i, objects[i] );
            }
            return hashMap;
        }
        return null;
    }

    public static EntityPlayerMP findPlayer(String name){
        Iterator iterator = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
        while(iterator.hasNext()){
            EntityPlayerMP player = (EntityPlayerMP) iterator.next();
            if(player.getCommandSenderName().equals(name))
                return player;
        }
        return null;
    }
}
