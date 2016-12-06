package OpenTechnology.effects;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;

import java.util.Random;

/**
 * Created by Avaja on 06.12.2016.
 */
public class SparkEffect extends EntityFX {

    public SparkEffect(Entity entity) {
        super(entity.worldObj, entity.posX, entity.posY, entity.posZ);

        //93, 154, 252
        particleRed = 93f / 255f;
        particleGreen = 154f / 255f;
        particleBlue = 252f / 255f;

        Random r = worldObj.rand;

        double vx = r.nextBoolean() ? r.nextFloat() * -1 : r.nextFloat();
        double vy = r.nextBoolean() ? r.nextFloat() * -1 : r.nextFloat();
        double vz = r.nextBoolean() ? r.nextFloat() * -1 : r.nextFloat();

        setPosition(posX, (posY - 1) + r.nextDouble() + 0.3, posZ);

        addVelocity(vx / 10, vy / 100, vz / 10);
        particleGravity = 0.2f;

        particleMaxAge = 60;
    }
}
