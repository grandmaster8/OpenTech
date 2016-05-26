package OpenTechnology.tileentities;

import OpenTechnology.Config;
import OpenTechnology.item.Items;
import OpenTechnology.utils.Utils;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import li.cil.oc.api.prefab.TileEntityEnvironment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Created by Avaja on 11.05.2016.
 */
public class TileEntityPlayerInventoryBinder extends TileEntityEnvironment implements SimpleComponent, Analyzable {

    private String player_name;
    private EntityPlayer player;

    public TileEntityPlayerInventoryBinder(  ) {
        node = Network.newNode( this, Visibility.Network ).withComponent( getComponentName(  ) ).withConnector(  ).create(  );
    }

    @Override
    public void updateEntity(  ) {
        super.updateEntity(  );
    }

    @Override
    public Node[] onAnalyze( EntityPlayer player, int side, float hitX, float hitY, float hitZ ) {
        return new Node[]{node};
    }

    @Override
    public String getComponentName(  ) {
        return "inventory_binder";
    }

    public void setPlayer( EntityPlayer player ){
        this.player = player;
        player_name = player.getDisplayName(  );
    }

    public boolean isConnected(){
        boolean ret = player_name != null && !player_name.isEmpty(  );
        if ( ret && player == null && MinecraftServer.getServer() != null && MinecraftServer.getServer().getConfigurationManager() != null){
            List<EntityPlayer> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
            for (EntityPlayer player : players){
                if(player.getDisplayName().equals(player_name)){
                    this.player = player;
                    break;
                }
            }
        }
        return ret && player != null;
    }

    @Override
    public void readFromNBT( NBTTagCompound nbt ) {
        super.readFromNBT( nbt );
        if ( nbt.hasKey( "player_name" ) ){
            player_name = nbt.getString("player_name");
            isConnected(  );
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT( nbt );
        if (isConnected()){
            nbt.setString("player_name", player.getDisplayName());
        }
    }

    @Override
    public Packet getDescriptionPacket(  ) {
        NBTTagCompound tagCompound = new NBTTagCompound(  );
        writeToNBT( tagCompound );
        return new S35PacketUpdateTileEntity( xCoord, yCoord, zCoord, 0, tagCompound );
    }

    @Override
    public void onDataPacket( NetworkManager net, S35PacketUpdateTileEntity pkt ) {
        readFromNBT( pkt.func_148857_g(  ) );
    }

    @Callback( doc="function( slot:number )stack:table; Получить стек из слота ( slot ) игрока." )
    public Object[] getStackInSlot( Context context, Arguments arguments ) throws Exception{
        if ( !isConnected(  ) )
            return new Object[]{false, "player not connected"};

        int slot = arguments.checkInteger( 0 );
        if ( player != null ){
            if ( slot < 0 || slot > player.inventory.getSizeInventory(  ) )
                return new Object[]{false, "invalid slot"};
            ItemStack stack = player.inventory.getStackInSlot( slot );
            if( stack == null )
                return new Object[]{"null"};
            return new Object[]{Utils.createStringTable( "unlocalizedname", stack.getUnlocalizedName(  ), "name", stack.getDisplayName(  ), "id", Item.getIdFromItem( stack.getItem(  ) ), "stacksize", stack.stackSize, "damage", stack.getItemDamage(  ) )};
        }
        return new Object[]{false};
    }

    @Callback( doc="function( side:number, slot:number, count:number ); Переместить стек из слота ( slot ) игрока в количестве ( count ), в контейнер находящийся в стороне ( side )" )
    public Object[] dropIntoInventory( Context context, Arguments arguments ) throws Exception{
        int side = arguments.checkInteger( 0 );
        int slot = arguments.checkInteger( 1 ) - 1;
        int count = arguments.checkInteger( 2 );

        ForgeDirection direction = ForgeDirection.getOrientation( side );

        if ( !isConnected(  ) )
            return new Object[]{false, "player not connected"};

        if ( slot < 1 || slot > player.inventory.getSizeInventory(  ) )
            return new Object[]{false, "invalid slot"};

        if ( count <= 0 )
            return new Object[]{false, "invalid count"};

        TileEntity tileEntity = worldObj.getTileEntity( xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ );

        if ( tileEntity instanceof IInventory ){
            ItemStack stack = player.inventory.getStackInSlot( slot );
            ItemStack split;
            if(stack.stackSize >= count){
                split = stack.splitStack( count );
            }else{
                split = stack.splitStack(stack.stackSize);
            }
            Connector connector = ( Connector ) node;
            double dist = getDistanceFrom( player.posX, player.posY, player.posZ );

            if ( connector.tryChangeBuffer( -calculateEnergy( dist, count ) ) ) {
                if ( Utils.addToIInventory( ( IInventory ) tileEntity, split ) ) {
                    if ( stack.stackSize <= 0 )
                        player.inventory.setInventorySlotContents( slot, null );
                    return new Object[]{true};
                }else{
                    stack.stackSize += split.stackSize;
                }
            }
            else
                return new Object[]{false, "not enough energy"};
        }


        return new Object[]{false};
    }

    @Callback( doc="function( side:number, outSlot:number, inSlot:number, count:number ); Переместить стек из контейнера в стороне( side ) из слота( outSlot ) в слот игрока( inSlot ) в количестве ( count )" )
    public Object[] putIntoSlot( Context context, Arguments arguments ) throws Exception{

        if ( !isConnected(  ) )
            return new Object[]{false, "player not connected"};

        int side = arguments.checkInteger( 0 );
        int outSlot = arguments.checkInteger( 1 ) - 1;
        int inSlot = arguments.checkInteger( 2 ) - 1;
        int count = arguments.checkInteger( 3 );

        ForgeDirection direction = ForgeDirection.getOrientation( side );

        TileEntity tileEntity = worldObj.getTileEntity( xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ );

        if ( tileEntity instanceof IInventory ){
            IInventory containerInventory = ( IInventory ) tileEntity;

            if ( outSlot >= 1 && outSlot < containerInventory.getSizeInventory(  ) ){

                if ( inSlot >= 1 &&  inSlot < player.inventory.getSizeInventory(  ) ){
                    Connector connector = ( Connector ) node;
                    double dist = getDistanceFrom( player.posX, player.posY, player.posZ );
                    ItemStack outStack = containerInventory.getStackInSlot( outSlot );

                    if ( outStack != null && outStack.stackSize >= count ){
                        ItemStack split = outStack.splitStack( count );

                        if ( connector.tryChangeBuffer( -calculateEnergy( dist, count ) ) ){

                            if ( player.inventory.addItemStackToInventory( split ) ) {

                                if ( outStack.stackSize <= 0 )
                                    containerInventory.setInventorySlotContents( outSlot, null );
                                return new Object[]{true};

                            }else{
                                return new Object[]{false, "player inventory full"};
                            }

                        }else{
                            outStack.stackSize += count;
                            return new Object[]{false, "not enough energy"};
                        }
                    }
                }else{
                    return new Object[]{false, "invalid input slot"};
                }
            }else{
                return new Object[]{false, "invalid output slot"};
            }
        }

        return new Object[]{};
    }

    @Callback(  )
    public Object[] isConnected( Context context, Arguments arguments ) throws Exception{
        return new Object[]{isConnected(  )};
    }

    @Callback( doc="function( distance:double, itemCount:number );" )
    public Object[] calculateEnergy( Context context, Arguments arguments ) throws Exception{
        double distance = arguments.checkDouble( 0 );
        int count = arguments.checkInteger( 1 );
        return new Object[]{calculateEnergy( distance, count )};
    }

    @Callback( doc="" )
    public Object[] getDistanceToPlayer( Context context, Arguments arguments ) throws Exception{
        if ( isConnected(  ) )
            return new Object[]{getDistanceFrom( player.posX, player.posY, player.posZ )};
        else
            return new Object[]{false, "player not connected."};
    }

    private double calculateEnergy( double distance, int count ){
        double factor = ( distance / 10 ) < 1 ? 1 : ( distance / 10 );
        return factor * distance * Config.binderEnergyFactor;
    }



}
