package ot.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import ot.OpenTechnology;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://github.com/Avaja/OpenTechnology/releases").openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String input;
            String all = "";
            while ((input = br.readLine()) != null){
                all += input;
            }
            br.close();
            Pattern pattern = Pattern.compile("latest-build\\((.+?)\\)");
            Matcher matcher = pattern.matcher(all);
            if(matcher.find()){
                return matcher.group(1);
            }
            return OpenTechnology.VERSION;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OpenTechnology.VERSION;
    }
}
