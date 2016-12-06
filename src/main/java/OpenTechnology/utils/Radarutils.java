package OpenTechnology.utils;

import OpenTechnology.Config;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Avaja on 21.05.2016.
 */
public class RadarUtils {
    public static Set<Map<String, Object>> getEntities(World world, int xCoord, int yCoord, int zCoord, AxisAlignedBB bounds, Class<? extends EntityLivingBase> eClass, float range) {
        Set<Map<String, Object>> entities = new HashSet<Map<String, Object>>();
        for(Object obj : world.getEntitiesWithinAABB(eClass, bounds)) {
            EntityLivingBase entity = (EntityLivingBase) obj;
            double dx = entity.posX - (xCoord + 0.5);
            double dy = entity.posY - (yCoord + 0.5);
            double dz = entity.posZ - (zCoord + 0.5);
            if(Math.sqrt(dx * dx + dy * dy + dz * dz) < range) {
                // Maps are converted to tables on the Lua side.
                Map<String, Object> entry = new HashMap<String, Object>();
                if(entity instanceof EntityPlayer) {
                    entry.put("name", ((EntityPlayer) entity).getDisplayName());
                } else if(entity instanceof EntityLiving && ((EntityLiving) entity).hasCustomNameTag()) {
                    entry.put("name", ((EntityLiving) entity).getCustomNameTag());
                } else {
                    entry.put("name", entity.getCommandSenderName());
                }

                entry.put("x", (int) dx);
                entry.put("y", (int) dy);
                entry.put("z", (int) dz);

                entry.put("distance", Math.sqrt(dx * dx + dy * dy + dz * dz));
                entities.add(entry);
            }
        }
        return entities;
    }

    public static Set<Map<String, Object>> getItems(World world, int xCoord, int yCoord, int zCoord, AxisAlignedBB bounds, Class<? extends EntityItem> eClass) {
        Set<Map<String, Object>> entities = new HashSet<Map<String, Object>>();
        for(Object obj : world.getEntitiesWithinAABB(eClass, bounds)) {
            EntityItem entity = (EntityItem) obj;
            double dx = entity.posX - (xCoord + 0.5);
            double dy = entity.posY - (yCoord + 0.5);
            double dz = entity.posZ - (zCoord + 0.5);
            if(Math.sqrt(dx * dx + dy * dy + dz * dz) < Config.radarRange) {
                // Maps are converted to tables on the Lua side.
                Map<String, Object> entry = new HashMap<String, Object>();
                ItemStack stack = entity.getEntityItem();
                entry.put("name", Item.itemRegistry.getNameForObject(stack));
                entry.put("damage", stack.getItemDamage());
                entry.put("hasTag", stack.hasTagCompound());
                entry.put("size", stack.stackSize);
                entry.put("label", stack.getDisplayName());

                entry.put("x", (int) dx);
                entry.put("y", (int) dy);
                entry.put("z", (int) dz);

                entry.put("distance", Math.sqrt(dx * dx + dy * dy + dz * dz));
                entities.add(entry);
            }
        }
        return entities;
    }

}
