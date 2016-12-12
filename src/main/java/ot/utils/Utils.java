package ot.utils;

import li.cil.oc.api.network.Node;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Avaja on 07.05.2016.
 */
public class Utils {

    public static List getEntitiesInBound( Class c, World world, int minx, int miny, int minz, int maxx, int maxy, int maxz ){
        return world.getEntitiesWithinAABB( c, AxisAlignedBB.getBoundingBox( minx, miny, minz, maxx, maxy, maxz ) );
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

    public static MovingObjectPosition getLookAt(EntityPlayer entityPlayer){
        World world = entityPlayer.worldObj;
        Vec3 origin = Vec3.createVectorHelper(entityPlayer.posX, entityPlayer.posY + entityPlayer.getEyeHeight(), entityPlayer.posZ);
        Vec3 direction = entityPlayer.getLookVec();
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
}
