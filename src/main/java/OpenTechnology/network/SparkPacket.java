package OpenTechnology.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Avaja on 06.12.2016.
 */
public class SparkPacket implements IMessage {

    public int entityId;

    public SparkPacket(int entityId) {
        this.entityId = entityId;
    }

    public SparkPacket() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
    }
}

