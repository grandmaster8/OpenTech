package ot.system;

import net.minecraft.entity.player.EntityPlayer;
import ot.tileentities.TileEntityPIB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avaja on 10.12.2016.
 */
public class PIBS {
    private static List<TileEntityPIB> pibs = new ArrayList<TileEntityPIB>();

    public static void add(TileEntityPIB pib){
        pibs.add(pib);
    }

    public static void remove(TileEntityPIB pib){
        pibs.remove(pib);
    }

    public static void checkPlayer(EntityPlayer entityPlayer){
        for(TileEntityPIB entityPIB : pibs){
            entityPIB.checkPlayer(entityPlayer);
        }
    }
}
