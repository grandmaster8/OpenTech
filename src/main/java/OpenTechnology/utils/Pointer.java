package OpenTechnology.utils;

import OpenTechnology.Config;
import net.minecraft.entity.Entity;

import java.util.List;

/**
 * Created by NEO on 21.01.2016.
 */
public class Pointer {
    public List<Entity> entity;
    public int dimension, x, y, z, time;
    public boolean live;

    public Pointer(List<Entity> entity, int dimension, int x, int y, int z) {
        this.entity = entity;
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
        live = true;
    }

    public void update(){
        time++;
        if(Config.timeLimit <= time){
            live = false;
        }
    }
}
