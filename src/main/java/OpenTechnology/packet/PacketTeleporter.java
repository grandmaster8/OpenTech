package OpenTechnology.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityPortalFX;

/**
 * Created by NEO on 19.01.2016.
 */
public class PacketTeleporter implements IMessage {
    public int x, y, z;

    public PacketTeleporter() {
    }

    public PacketTeleporter(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public static class HandlerClient implements IMessageHandler<PacketTeleporter, IMessage>{

        @Override
        public IMessage onMessage(PacketTeleporter message, MessageContext ctx) {
            if(ctx.side == Side.CLIENT){
                int x = message.x;
                int y = message.y;
                int z = message.z;
                for(int i = 0; i < 500; i++){
                    double xx = Math.random() + x;
                    double yy = Math.random() * 2 + y;
                    double zz = Math.random() + z;
                    try{
                        Minecraft.getMinecraft().effectRenderer.addEffect(new EntityPortalFX(Minecraft.getMinecraft().theWorld, xx, yy, zz, 0, 0, 0));
                    }catch (Exception e){

                    }
                }
            }
            return null;
        }
    }

    public static class HandlerServer implements IMessageHandler<PacketTeleporter, IMessage>{

        @Override
        public IMessage onMessage(PacketTeleporter message, MessageContext ctx) {
            return null;
        }
    }
}
