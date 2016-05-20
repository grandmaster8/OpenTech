package OpenTechnology.environment;

import OpenTechnology.Config;
import OpenTechnology.utils.Utils;
import li.cil.oc.api.Network;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.machine.Machine;
import li.cil.oc.api.network.Connector;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.ManagedEnvironment;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Avaja on 07.05.2016.
 */
public class TeslaUpgrade extends ManagedEnvironment {

    private boolean isHeat;

    private int radius;
    private int heat;

    public TeslaUpgrade(  ) {
        this.setNode( Network.newNode( this, Visibility.Network ).withComponent( "tesla" ).withConnector(  ).create(  ) );
        radius = Config.maxTeslaRadius;
        isHeat = false;
    }

    @Override
    public boolean canUpdate(  ) {
        return true;
    }

    @Override
    public void update(  ) {
        if ( isHeat ){
            heat--;
            if ( heat == 0 ){
                isHeat = false;
            }
        }
    }

    @Callback( doc="Attack in radius." )
    public Object[] attack( Context context, Arguments arguments ) throws Exception{

        if ( isHeat )
            return new Object[]{false, "overheated"};

        Machine machine = ( Machine ) context.node(  ).host(  );
        Robot robot = ( Robot ) machine.host(  );
        Connector connector = ( Connector ) node(  );

        int tmp = radius / 2;

        List<Entity> entities = Utils.getEntitiesInBound( Entity.class, robot.world (  ), ( int )robot.xPosition(  ) - tmp, ( int )robot.yPosition(  ) - tmp, ( int )robot.zPosition(  ) - tmp, ( int )robot.xPosition(  ) + tmp, ( int )robot.yPosition(  ) + tmp, ( int )robot.zPosition(  ) + tmp );

        if ( entities.size(  ) > 0 ){
            double energy = 0;
            for ( Entity entity : entities ){
                double dist = entity.getDistance( robot.xPosition(  ), robot.yPosition(  ), robot.zPosition(  ) );
                energy += dist / 10 * dist * Config.teslaFactor;
            }
            if ( connector.tryChangeBuffer( -energy ) ){
                float damage = ( float ) ( Config.teslaDamage / entities.size(  ) );
                for ( Entity entity : entities ){
                    entity.attackEntityFrom( DamageSource.magic, damage );
                }

                if ( entities.size(  ) > 0 ){
                    World world = robot.world(  );
                    world.playSoundEffect( robot.xPosition(  ), robot.yPosition(  ), robot.zPosition(  ), "ambient.weather.thunder", 10000.0F, 0.8F + world.rand.nextFloat(  ) * 0.2F );
                    world.playSoundEffect( robot.xPosition(  ), robot.yPosition(  ), robot.zPosition(  ), "random.explode", 2.0F, 0.5F + world.rand.nextFloat(  ) * 0.2F );
                }

                isHeat = true;
                heat = Config.teslaTimeReload;
                return new Object[]{true};
            }else{
                return new Object[]{false, "not enough energy"};
            }
        }


        return new Object[]{};
    }

    public Object[] setRadius( Context context, Arguments arguments ) throws Exception{
        int tmp = arguments.checkInteger( 0 );
        if ( tmp > Config.maxTeslaRadius )
            radius = Config.maxTeslaRadius;
        else
            radius = tmp;

        return new Object[]{};
    }

    @Callback( doc="get radius." )
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

    @Override
    public void load( NBTTagCompound nbt ) {
        super.load( nbt );
        radius = nbt.getInteger( "radius" );
        heat = nbt.getInteger( "heat" );
        isHeat = nbt.getBoolean( "isHeat" );
    }

    @Override
    public void save( NBTTagCompound nbt ) {
        super.save( nbt );
        nbt.setInteger( "radius", radius );
        nbt.setInteger( "heat", heat );
        nbt.setBoolean( "isHeat", isHeat );
    }
}
