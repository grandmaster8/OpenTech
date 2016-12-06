package OpenTechnology.network;

import OpenTechnology.system.SparksSystem;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;

public class SparkPacketHandler implements IMessageHandler<SparkPacket, IMessage> {

    public SparkPacketHandler() {
    }

    @Override
    public IMessage onMessage(SparkPacket message, MessageContext ctx) {
        if(ctx.side == Side.CLIENT){
            Minecraft minecraft = Minecraft.getMinecraft();
            SparksSystem.addEntity(minecraft.theWorld.getEntityByID(message.entityId));
        }
        return null;
    }
}
