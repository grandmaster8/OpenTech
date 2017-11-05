package ot.tileentities;

import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import ot.utils.Utils;

import java.util.*;


/**
 * Created by Avaja on 20.03.2017.
 */
public class TileEntityWorldInterface extends TileEntity implements Analyzable, Environment {

    protected Node node;
    private boolean addToNetwork = false;

    public TileEntityWorldInterface() {
        node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).create();
    }

    @Override
    public void updateEntity() {
        if(!addToNetwork){
            addToNetwork = true;
            Network.joinOrCreateNetwork(this);
        }
    }

    @Callback(doc="function(name:string, slot:integer); get information about the stack from the player by name.")
    public Object[] getStackInSlot(Context context, Arguments arguments) throws Exception{
        String name = arguments.checkString(0);
        int slot = arguments.checkInteger(0) + 1;
        EntityPlayer player = Utils.findPlayer(name);
        if(player != null){
            if(player.getCommandSenderName().equals(name)){
                if(slot >= 0 && slot < player.inventory.getSizeInventory()){
                    ItemStack itemStack = player.inventory.getStackInSlot(slot);
                    if(itemStack != null){
                        HashMap values = Utils.createStringTable(
                                "name", Utils.getForgeName(itemStack.getItem()),
                                "damage", itemStack.getItemDamage(),
                                "maxDamage", itemStack.getMaxDamage(),
                                "size", itemStack.stackSize,
                                "label", itemStack.getItem().getItemStackDisplayName(itemStack),
                                "hasTag", itemStack.hasTagCompound()
                        );

                        return new Object[]{values};
                    }else{
                        return new Object[]{null};
                    }
                }else{
                    return new Object[]{false, "invalid slot"};
                }
            }
        }
        return new Object[]{false, "player not found"};
    }

    @Callback(doc = "function(name:string, slot:integer); destroy stack.")
    public Object[] destroyStackInSlot(Context context, Arguments arguments) throws Exception{
        String name = arguments.checkString(0);
        int slot = arguments.checkInteger(1) + 1;
        EntityPlayer player = Utils.findPlayer(name);
        if(player != null){
            if(player.getCommandSenderName().equals(name)) {
                if (slot >= 0 && slot < player.inventory.getSizeInventory()) {
                    player.inventory.setInventorySlotContents(slot, null);
                    return new Object[]{true};
                }else{
                    return new Object[]{false, "invalid slot"};
                }
            }
        }
        return new Object[]{false, "player not found"};
    }

    @Callback(doc="function(player:string); get current and max player health.")
    public Object[] getPlayerHealth(Context context, Arguments arguments) throws Exception{
        String name = arguments.checkString(0);
        EntityPlayer player = Utils.findPlayer(name);
        if(player != null){
            return new Object[]{player.getHealth(), player.getMaxHealth()};
        }
        return new Object[]{false, "player not found"};
    }

    @Callback
    public Object[] heal(Context context, Arguments arguments) throws Exception{
        String name = arguments.checkString(0);
        EntityPlayer player = Utils.findPlayer(name);
        if(player != null){
            double health = arguments.checkDouble(1);
            player.heal((float) health);
            return new Object[]{true};
        }
        return new Object[]{false, "player not found"};
    }

    @Callback
    public Object[] kill(Context machine, Arguments args) throws Exception{
        String name = args.checkString(0);
        EntityPlayer player = Utils.findPlayer(name);
        if(player != null){
            player.setDead();
            return new Object[]{true};
        }
        return new Object[]{false, "player not found"};
    }

    @Callback(doc="function(player:string, dimension:integer); get bed location.")
    public Object[] getSpawnLocation(Context machine, Arguments args) throws Exception{
        String name = args.checkString(0);
        EntityPlayer player = Utils.findPlayer(name);
        if(player != null){
            int dimention = args.checkInteger(1);
            ChunkCoordinates chunkCoordinates = player.getBedLocation(dimention);
            if(chunkCoordinates != null)
                return new Object[]{chunkCoordinates.posX, chunkCoordinates.posY, chunkCoordinates.posZ};
            else
                return new Object[]{false, "spawn location not found"};
        }
        return new Object[]{false, "player not found"};
    }

    @Callback
    public Object[] getPlayerFoodStats(Context machine, Arguments args) throws Exception{
        String name = args.checkString(0);
        EntityPlayer player = Utils.findPlayer(name);
        if(player != null){
            return new Object[]{player.getFoodStats().needFood(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()};
        }
        return new Object[]{false, "player not found"};
    }

    @Callback
    public Object[] isFlying(Context context, Arguments args) throws Exception{
        String name = args.checkString(0);
        EntityPlayerMP player = Utils.findPlayer(name);
        if(player != null){
           return new Object[]{player.capabilities.isFlying};
        }
        return new Object[]{false, "player not found"};
    }

    @Callback(doc="return player position and dimention, x, y, z, dimension")
    public Object[] getPlayerPosition(Context context, Arguments args) throws Exception{
        String name = args.checkString(0);
        EntityPlayerMP player = Utils.findPlayer(name);
        if(player != null){
            return new Object[]{player.posX, player.posY, player.posZ, player.dimension};
        }
        return new Object[]{false, "player not found"};
    }

    @Callback(doc="function(player:string); get current potion effects.")
    public Object[] getActiveEffects(Context context, Arguments arguments) throws Exception{
        String name = arguments.checkString(0);
        EntityPlayer player = Utils.findPlayer(name);
        if(player != null){
            List<HashMap<Object, Object>> outEffects = new ArrayList<HashMap<Object, Object>>();
            Collection collection = player.getActivePotionEffects();
            Iterator iterator = collection.iterator();
            while(iterator.hasNext()){
                PotionEffect potionEffect = (PotionEffect) iterator.next();
                HashMap<Object, Object> values2 = new HashMap<Object, Object>();
                values2.put("potionId", potionEffect.getPotionID());
                values2.put("duration", potionEffect.getDuration());
                values2.put("amplifier", potionEffect.getAmplifier());
                values2.put("name", potionEffect.getEffectName());
                outEffects.add(values2);
            }
            return new Object[]{outEffects.toArray()};
        }
        return new Object[]{false, "player not found"};
    }

    @Callback
    public Object[] getOnlinePlayers(Context machine, Arguments args) throws Exception{
        return new Object[]{MinecraftServer.getServer().getConfigurationManager().getAllUsernames()};
    }

    @Callback(doc="function(player:string, message:string); kick player")
    public Object[] kick(Context context, Arguments arguments) throws Exception{
        String name = arguments.checkString(0);
        String message = arguments.checkString(1);
        EntityPlayerMP player = Utils.findPlayer(name);
        if(player != null){
            player.playerNetServerHandler.kickPlayerFromServer(message);
            return new Object[]{true};
        }
        return new Object[]{false, "player not found"};
    }

    private String getComponentName() {
        return "world_interface";
    }

    @Override
    public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return new Node[]{node};
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public void onConnect(Node node) {

    }

    @Override
    public void onDisconnect(Node node) {

    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void invalidate() {
        super.invalidate();
        if (node != null) node.remove();
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        if (node != null) node.remove();
    }

    // ----------------------------------------------------------------------- //

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (node != null && node.host() == this) {
            node.load(nbt.getCompoundTag("oc:node"));
        }
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (node != null && node.host() == this) {
            final NBTTagCompound nodeNbt = new NBTTagCompound();
            node.save(nodeNbt);
            nbt.setTag("oc:node", nodeNbt);
        }
    }
}
