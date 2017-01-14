package ot.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import ot.OpenTechnology;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Avaja on 28.12.2016.
 */
public class CheckVersion {

    public static void check(EntityPlayer player) {
        try{
            String latestVersion = getLatestVersion();
            if(latestVersion != null){
                latestVersion = latestVersion.replace("\n", "");
                if(!latestVersion.equals(OpenTechnology.VERSION)){
                    player.addChatComponentMessage(new ChatComponentText(String.format("§aOpenTechnology§f: A newer version is available: %s", latestVersion)));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String getLatestVersion(){
        try{
            InputStream in = new URL("https://raw.githubusercontent.com/Avaja/OpenTechnology/master/LATEST_VERSION").openStream();

            byte[] buff = new byte[in.available()];
            in.read(buff);

            return new String(buff, "UTF-8");
        }catch (Exception e){
            return null;
        }
    }
}
