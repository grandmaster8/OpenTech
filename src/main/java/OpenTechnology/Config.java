package OpenTechnology;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by NEO on 01.02.2016.
 */
public class Config {
    public static Configuration configuration;

    public static int teleportFactor;
    public static boolean logging;
    public static int timeLimit;
    public static int entityTeleportationLimit;
    public static int chatboxMaxRadius;
    public static String prefixChat;
    public static int maxMessageLength;
    public static int maxTeslaRadius;
    public static double teslaFactor;
    public static double teslaDamage;
    public static int teslaTimeReload;
    public static double binderEnergyFactor;

    public static void init(File file){
        configuration = new Configuration(file);
        configuration.load();
        entityTeleportationLimit = configuration.get("game", "entityTeleportationLimit", 5, "Limit the number of things to tesseract.").getInt();

        chatboxMaxRadius = configuration.get("chatbox", "chatboxMaxRadius", 128, "").getInt();
        prefixChat = configuration.get("chatbox", "prefixChat", "@", "").getString();
        maxMessageLength = configuration.get("chatbox", "maxMessageLength", 100).getInt();

        maxTeslaRadius = configuration.get("tesla", "maxTeslaRadius", 10).getInt();
        teslaFactor = configuration.get("tesla", "teslaFactor", 5.0).getDouble();
        teslaDamage = configuration.get("tesla", "teslaDamage", 30.0).getDouble();
        teslaTimeReload = configuration.get("tesla", "teslaTimeReload", 90, "ticks").getInt();

        binderEnergyFactor = configuration.get("binder", "binderEnergyFactor", 30, "").getDouble();

        configuration.save();
    }
}
