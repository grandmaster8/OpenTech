package OpenTechnology.system;

import OpenTechnology.tileentities.TileEntityLDA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avaja on 07.12.2016.
 */
public class LDAS {

    private static List<TileEntityLDA> ldas = new ArrayList<TileEntityLDA>();

    public static void addLDA(TileEntityLDA lda){
        ldas.add(lda);
    }

    public static void removeLDA(TileEntityLDA lda){
        ldas.remove(lda);
    }

    public static boolean broadcastMessage(TileEntityLDA sender, String message){
        for(TileEntityLDA tileEntityLDA : ldas){
            float k = (sender.yCoord + 16) / 256f;
            double dist = sender.getDistanceFrom(tileEntityLDA.xCoord, tileEntityLDA.yCoord, tileEntityLDA.zCoord);
            if(dist <= (sender.getDistance() * k) && sender.getChannel() == tileEntityLDA.getChannel()){
                tileEntityLDA.receiveMessage(sender, (int)dist, message);
            }
        }
        return false;
    }

    public static boolean sendMessage(TileEntityLDA sender, String receiveAddress, String message){
        for(TileEntityLDA tileEntityLDA : ldas){
            if(tileEntityLDA.node().address().equals(receiveAddress)){
                float k = (sender.yCoord + 16) / 256f;
                double dist = sender.getDistanceFrom(tileEntityLDA.xCoord, tileEntityLDA.yCoord, tileEntityLDA.zCoord);
                if(dist <= (sender.getDistance() * k) && sender.getChannel() == tileEntityLDA.getChannel()){
                    tileEntityLDA.receiveMessage(sender, (int)dist, message);
                    return true;
                }
            }
        }
        return false;
    }
}
