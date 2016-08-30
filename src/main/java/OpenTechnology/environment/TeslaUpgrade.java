package OpenTechnology.environment;

import OpenTechnology.Config;
import OpenTechnology.OpenTechnology;
import OpenTechnology.utils.TeslaDamage;
import OpenTechnology.utils.Utils;
import li.cil.oc.api.Network;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.machine.Machine;
import li.cil.oc.api.network.Connector;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.ManagedEnvironment;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Avaja on 07.05.2016.
 */
public class TeslaUpgrade extends ManagedEnvironment {

    private EnvironmentHost host;

    private boolean isHeat;

    private double charge;

    private double maxChargeLevel;

    private int radius;
    private int heat;
    private boolean run;

    public TeslaUpgrade(EnvironmentHost host) {
        this.setNode(Network.newNode(this, Visibility.Network).withComponent("tesla").withConnector().create());
        this.host = host;
        radius = Config.maxTeslaRadius;
        isHeat = false;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    private int coolingFactor(){
        int x = (int) host.xPosition();
        int y = (int) host.yPosition();
        int z = (int) host.zPosition();
        World world = host.world();

        int factor = 0;

        Block water = Blocks.water;
        Block ice = Blocks.ice;
        Block air = Blocks.air;

        Block[] blocks = new Block[6];

        blocks[0] = world.getBlock(x + 1, y, z);
        blocks[1] = world.getBlock(x - 1, y, z);
        blocks[2] = world.getBlock(x, y + 1, z);
        blocks[3] = world.getBlock(x, y - 1, z);
        blocks[4] = world.getBlock(x, y, z + 1);
        blocks[5] = world.getBlock(x, y, z - 1);

        for (int i = 0; i < blocks.length; i++){
            if (blocks[i] == water){
                factor += Config.teslaCoolingWaterFactor;
            }else if(blocks[i] == ice){
                factor += Config.teslaCoolingIceFactor;
            }else if(blocks[i] == air){
                factor += Config.teslaCoolingAirFactor;
            }
        }

        return factor;
    }

    @Override
    public void update() {
        if (isHeat){
            heat -=  coolingFactor();
            if ( heat <= 0 ){
                isHeat = false;
                heat = 0;
            }
        }
        if (run){
            Connector connector = (Connector) node();

            if (charge < maxChargeLevel && connector.tryChangeBuffer(-Config.speedTeslaCharge)){
                charge += Config.speedTeslaCharge;
                if (charge > maxChargeLevel)
                    charge = maxChargeLevel;
            }
        }
    }

    @Callback( doc="Attack in radius." )
    public Object[] attack(Context context, Arguments arguments) throws Exception{

        if (!run) return new Object[]{false, "tesla disable"};

        if (isHeat)
            return new Object[]{false, "overheated"};

        Machine machine = ( Machine ) context.node(  ).host(  );
        Robot robot = ( Robot ) machine.host(  );

        int tmp = radius / 2;

        List<Entity> entities = Utils.getEntitiesInBound( Entity.class, robot.world (  ), ( int )robot.xPosition(  ) - tmp, ( int )robot.yPosition(  ) - tmp, ( int )robot.zPosition(  ) - tmp, ( int )robot.xPosition(  ) + tmp, ( int )robot.yPosition(  ) + tmp, ( int )robot.zPosition(  ) + tmp );

        for(Entity entity : entities)
            System.out.println(entity.toString());

        if ( entities.size() > 0 ){

            isHeat = true;
            heat = (int) ((charge / 100) * Config.teslaHeatPercent);

            charge -= heat;

            double damage = charge / entities.size();
            for (Entity entity : entities) {
                if (entity instanceof EntityLivingBase){
                    double dist = entity.getDistance(host.xPosition(), host.yPosition(), host.zPosition());
                    damage /= dist * 100;
                    entity.attackEntityFrom(TeslaDamage.teslaDamage, (float) damage);
                }
            }

            if (entities.size() > 0){
                World world = robot.world();
                world.playSoundEffect(robot.xPosition(), robot.yPosition(), robot.zPosition(), OpenTechnology.MODID+":tesla_attack", 10000.0F, 0.8F + world.rand.nextFloat() * 0.2F);
            }
        }


        return new Object[]{};
    }

    @Callback(doc="set chargeLevel.")
    public Object[] setChargeLevel(Context context, Arguments arguments) throws Exception{
        double chargeLevel = arguments.checkDouble(0);
        if (chargeLevel > Config.maxTeslaCharge)
            chargeLevel = Config.maxTeslaCharge;
        if (chargeLevel < 0)
            chargeLevel = 0;

        maxChargeLevel = chargeLevel;

        return new Object[]{};
    }

    @Callback(doc="set radius.")
    public Object[] setRadius( Context context, Arguments arguments ) throws Exception{
        int tmp = arguments.checkInteger( 0 );
        if ( tmp > Config.maxTeslaRadius)
            radius = Config.maxTeslaRadius;
        if (tmp < 0)
            tmp = 0;

            radius = tmp;

        return new Object[]{};
    }

    @Callback
    public Object[] getRadius( Context context, Arguments arguments )  throws Exception{
        return new Object[]{radius};
    }

    @Callback
    public Object[] isOverheated( Context context, Arguments arguments ) throws Exception{
        return new Object[]{isHeat};
    }

    @Callback
    public Object[] getHeat( Context context, Arguments arguments ) throws Exception{
        return new Object[]{heat};
    }

    @Callback
    public Object[] getChargeLevel( Context context, Arguments arguments ) throws Exception{
        return new Object[]{maxChargeLevel};
    }

    @Callback
    public Object[] getMaxChargeLevel( Context context, Arguments arguments ) throws Exception{
        return new Object[]{Config.maxTeslaCharge};

    }

    @Callback
    public Object[] getMaxRadius( Context context, Arguments arguments ) throws Exception{
        return new Object[]{Config.maxTeslaRadius};

    }

    @Callback
    public Object[] getCharge( Context context, Arguments arguments ) throws Exception{
        return new Object[]{charge};
    }

    @Callback
    public Object[] on( Context context, Arguments arguments ) throws Exception{
        run = true;
        return new Object[]{};
    }

    @Callback
    public Object[] off( Context context, Arguments arguments ) throws Exception{
        run = false;
        return new Object[]{};
    }

    @Override
    public void load( NBTTagCompound nbt ) {
        super.load( nbt );

        if (nbt.hasKey("radius")){
            radius = nbt.getInteger("radius");
        }else{
            radius = Config.maxTeslaRadius;
        }
        if(nbt.hasKey("heat")){
            heat = nbt.getInteger("heat");
        }else{
            heat = 0;
        }
        if (nbt.hasKey("maxChargeLevel")){
            maxChargeLevel = nbt.getDouble("maxChargeLevel");
        }else{
            maxChargeLevel = Config.maxTeslaCharge;
        }
        if (nbt.hasKey("charge")){
            charge = nbt.getDouble("charge");
        }else {
            charge = 0;
        }

        if (nbt.hasKey("run")){
            run = nbt.getBoolean("run");
        }else {
            run = false;
        }

        isHeat = heat > 0;
    }

    @Override
    public void save( NBTTagCompound nbt ) {
        super.save( nbt );
        nbt.setInteger( "radius", radius );
        nbt.setInteger( "heat", heat );
        nbt.setDouble("maxChargeLevel", maxChargeLevel);
        nbt.setDouble("charge", charge);
        nbt.setBoolean("run", run);
    }
}
