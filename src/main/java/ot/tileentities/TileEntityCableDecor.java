package ot.tileentities;

import li.cil.oc.common.tileentity.Cable;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Avaja on 08.12.2016.
 */
public class TileEntityCableDecor extends Cable {
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if(nbt.hasKey("metadata")){
            blockMetadata = nbt.getInteger("metadata");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("metadata", blockMetadata);
    }

    @Override
    public void readFromNBTForServer(NBTTagCompound nbt) {
        super.readFromNBTForServer(nbt);
        if(nbt.hasKey("metadata")){
            blockMetadata = nbt.getInteger("metadata");
        }
    }

    @Override
    public void writeToNBTForServer(NBTTagCompound nbt) {
        super.writeToNBTForServer(nbt);
        nbt.setInteger("metadata", blockMetadata);
    }

    @Override
    public void readFromNBTForClient(NBTTagCompound nbt) {
        super.readFromNBTForClient(nbt);
        if(nbt.hasKey("metadata")){
            blockMetadata = nbt.getInteger("metadata");
        }
    }

    @Override
    public void writeToNBTForClient(NBTTagCompound nbt) {
        super.writeToNBTForClient(nbt);
        nbt.setInteger("metadata", blockMetadata);
    }
}
