package ot.system;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import ot.effects.SparkEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Avaja on 06.12.2016.
 */
public class SparksSystem {

    private static List<Pair> entities = new ArrayList<Pair>();

    public static void addEntity(Entity entity){
        if(Minecraft.getMinecraft().gameSettings.particleSetting != 0)
            return;
        entities.add(new Pair(0, entity));
    }

    public static void updateAll(){
        if(Minecraft.getMinecraft().gameSettings.particleSetting != 0)
            return;

        Random r = new Random(System.currentTimeMillis());
        Minecraft minecraft = Minecraft.getMinecraft();

        for(int i = 0; i < entities.size(); i++){
            Pair pair = entities.get(i);
            pair.ticks++;
            if(pair.ticks >= 20 * 30)
                entities.remove(i);

            int count = r.nextInt(2);
            for(int q = 0; q < count; q++){
                if(pair.entity != null){
                    minecraft.effectRenderer.addEffect(new SparkEffect(pair.entity));
                }else{
                    entities.remove(i);
                }
            }
        }
    }

    private static class Pair{
        public int ticks;
        public Entity entity;

        public Pair(int ticks, Entity entity) {
            this.ticks = ticks;
            this.entity = entity;
        }
    }
}
