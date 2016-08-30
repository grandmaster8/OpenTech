package OpenTechnology.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;

/**
 * Created by Avaja on 29.08.2016.
 */
public class PacketHeatStatus implements IMessage {

    private boolean isHeat;

    public PacketHeatStatus(boolean isHeat) {
        this.isHeat = isHeat;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        buf.writeBoolean(isHeat);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        isHeat = buf.readBoolean();
    }

    public class Handler implements IMessageHandler<PacketHeatStatus, PacketHeatStatus> {

        @Override
        public PacketHeatStatus onMessage(PacketHeatStatus message, MessageContext ctx) {
            if (ctx.side == Side.CLIENT){

            }
            return null;
        }
    }
}
