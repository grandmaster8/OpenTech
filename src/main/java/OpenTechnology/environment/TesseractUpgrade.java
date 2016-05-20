package OpenTechnology.environment;

import OpenTechnology.packet.PacketTeleporter;
import OpenTechnology.proxy.CommonProxy;
import OpenTechnology.tileentities.TileEntityTeleporter;
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
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class TesseractUpgrade extends ManagedEnvironment{
    public static HashMap<String, TesseractUpgrade> tesseractList = new HashMap<String, TesseractUpgrade>(  );

    private boolean connect;
    private Robot robot;

    public TesseractUpgrade(  ) {
        setNode( Network.newNode( this, Visibility.Network ).withComponent( "tesseract" ).withConnector(  ).create(  ) );
        connect = false;
    }

    @Override
    public boolean canUpdate(  ) {
        return true;
    }

    @Override
    public void update(  ) {
        if ( !connect && node(  ) != null ){
            tesseractList.put( node(  ).address(  ), this );
            connect = true;
        }
    }

    @Callback( doc="function(  )" )
    public Object[] sync( Context context, Arguments arguments ) throws Exception{
        Machine machine = ( Machine ) context.node(  ).host(  );
        robot = ( Robot ) machine.host(  );
        return new Object[]{true};
    }

    @Callback( doc="function( uuid:string, slot:number, count:number )" )
    public Object[] transferOfSlot( Context context, Arguments arguments ) throws Exception{
        String uuid = arguments.checkString( 0 );
        int slot = arguments.checkInteger( 1 ) + 4;
        int count = arguments.checkInteger( 2 );

        Machine machine = ( Machine ) context.node(  ).host(  );
        Robot robot = ( Robot ) machine.host(  );

        if ( slot < 0 || slot > robot.getSizeInventory(  ) )
            return new Object[]{false, "invalid slot"};

        if ( tesseractList.containsKey( uuid ) ){
            TesseractUpgrade tesseract = tesseractList.get( uuid );
            if ( tesseract.robot == null )
                return new Object[]{false, "tesseract not sync."};

            Connector connector = ( Connector ) node(  );

            ItemStack stack = robot.getStackInSlot( slot );
            if ( stack != null && stack.stackSize >= count ) {
                double dist = Utils.distance( node(  ), tesseract.node(  ) );
                double energy = 4 * ( dist / 10 ) * dist * count;
                if ( tesseract.addToInventory( stack.splitStack( count ) ) ) {
                    if ( connector.tryChangeBuffer( -energy ) ) {
                        if ( stack.stackSize <= 0 )
                            robot.setInventorySlotContents( slot, null );

                        CommonProxy.wrapper.sendToDimension( new PacketTeleporter( ( int )tesseract.robot.xPosition(  ), ( int )tesseract.robot.yPosition(  ), ( int )tesseract.robot.zPosition(  ) ), tesseract.robot.world(  ).provider.dimensionId );
                        tesseract.robot.world(  ).playSoundEffect( ( int )tesseract.robot.xPosition(  ), ( int )tesseract.robot.yPosition(  ), ( int )tesseract.robot.zPosition(  ), "mob.endermen.portal", 1.0F, 1.0F );
                        return new Object[]{true};
                    }else{
                        return new Object[]{false, "not enough energy"};
                    }
                }else{
                    return new Object[]{false, "inventory full"};
                }
            }
        }else if ( TileEntityTeleporter.teleporterList.containsKey( uuid ) ){
            TileEntityTeleporter teleporter = TileEntityTeleporter.teleporterList.get( uuid );
            Connector connector = ( Connector ) node(  );

            ItemStack stack = robot.getStackInSlot( slot );
            if ( stack != null && stack.stackSize >= count ) {
                double dist = Utils.distance( robot.xPosition(  ), robot.yPosition(  ), robot.zPosition(  ), teleporter.xCoord, teleporter.yCoord, teleporter.zCoord );
                double energy = 4 * ( dist / 10 ) * dist * count;
                if ( teleporter.addToInventory( stack.splitStack( count ) ) ) {
                    if ( connector.tryChangeBuffer( -energy ) ) {
                        if ( stack.stackSize <= 0 )
                            robot.setInventorySlotContents( slot, null );

                        CommonProxy.wrapper.sendToDimension( new PacketTeleporter( teleporter.xCoord, teleporter.yCoord, teleporter.zCoord ), teleporter.getWorldObj(  ).provider.dimensionId );
                        teleporter.getWorldObj(  ).playSoundEffect( teleporter.xCoord, teleporter.yCoord, teleporter.zCoord, "mob.endermen.portal", 1.0F, 1.0F );
                        return new Object[]{true};
                    }else{
                        return new Object[]{false, "not enough energy"};
                    }
                }else{
                    return new Object[]{false, "inventory full"};
                }
            }
        }

        return new Object[]{false};
    }

    public boolean addToInventory( ItemStack stack ){
        if ( robot != null ){
            return Utils.addToIInventory( robot, 4, stack );
        }
        return false;
    }

    public Robot getRobot(  ){
        return robot;
    }
}