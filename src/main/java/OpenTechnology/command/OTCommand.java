package OpenTechnology.command;

import OpenTechnology.blocks.Blocks;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * Created by Avaja on 07.12.2016.
 */
public class OTCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "ot";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return "commands.ot.usage";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] strings) {
        if(strings != null && strings.length > 0 && commandSender instanceof EntityPlayer){
            if(strings.length == 1){
                if(strings[0].equals("antenna")){
                    EntityPlayer player = (EntityPlayer) commandSender;
                    Vec3 lookVec = player.getLookVec();
                    spawnAntenna(player.worldObj, (int)lookVec.xCoord, (int)lookVec.yCoord, (int)lookVec.zCoord);
                }
            }
        }
    }

    private void spawnAntenna(World world, int x, int y, int z){
        for(int i = 0; i <= 16; i++)
            if(world.getBlock(x, y + i, z) != net.minecraft.init.Blocks.air)
                return;
        world.setBlock(x, y, z, Blocks.lda);
        for(int i = 1; i <= 14; i++){
            world.setBlock(x, y + i, z, Blocks.antenna);
        }
        world.setBlock(x, y + 15, z, Blocks.antennaCell);

    }
}
