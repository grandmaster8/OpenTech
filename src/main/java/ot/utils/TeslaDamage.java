package ot.utils;

import net.minecraft.util.DamageSource;

/**
 * Created by Avaja on 30.08.2016.
 */
public class TeslaDamage extends DamageSource {

    public static final TeslaDamage teslaDamage = new TeslaDamage();

    private TeslaDamage() {
        super("tesla");
        this.setFireDamage();
    }
}
