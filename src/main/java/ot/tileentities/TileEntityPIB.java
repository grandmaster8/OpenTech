package ot.tileentities;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Avaja on 10.12.2016.
 */
public class TileEntityPIB extends TileEntity implements Analyzable, Environment {

    private Node node = null;
    private boolean addToNetwork = false;

    private EntityPlayer target = null;
    private String name = null;

    public TileEntityPIB() {
        node = li.cil.oc.api.Network.newNode(this, Visibility.Network).withComponent("pib").create();
    }

    @Override
    public void updateEntity() {
        if(!addToNetwork){
            addToNetwork = true;
            li.cil.oc.api.Network.joinOrCreateNetwork(this);
        }
    }

    @Callback(doc="")
    public Object[] isConnected(Context context, Arguments arguments) throws Exception{
        return new Object[]{target != null};
    }

    @Callback(doc="")
    public Object[] getTargetName(Context context, Arguments arguments) throws Exception{
        return new Object[]{name};
    }

    @Callback(doc="")
    public Object[] checkBinding(Context context, Arguments arguments) throws Exception{
        return new Object[]{name != null};
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
    public void onChunkUnload() {
        if (node != null) node.remove();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if(nbt.hasKey("target")){
            name = nbt.getString("target");
            checkPlayer();
        }
        if (node != null && node.host() == this) {
            node.load(nbt.getCompoundTag("oc:node"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if(target != null){
            nbt.setString("target", target.getCommandSenderName());
        }
        if (node != null && node.host() == this) {
            final NBTTagCompound nodeNbt = new NBTTagCompound();
            node.save(nodeNbt);
            nbt.setTag("oc:node", nodeNbt);
        }
    }

    public void playerClicked(EntityPlayer entityPlayer) {
        if(target == null)
            target = entityPlayer;
        else{
            if(entityPlayer == target){
                entityPlayer = null;
            }
        }
    }

    private void checkPlayer(){
        if(name != null && !name.isEmpty()){
            ServerConfigurationManager configurationManager = MinecraftServer.getServer().getConfigurationManager();
            EntityPlayer player = configurationManager.func_152612_a(name);
            if(player != null)
                target = player;
        }
    }

    public void checkPlayer(EntityPlayer entityPlayer){
        if(entityPlayer != null){
            if(entityPlayer.getCommandSenderName().equals(name))
                target = entityPlayer;
        }
    }
}
