package OpenTechnology;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by NEO on 01.02.2016.
 */
public class Config {
    public static Configuration configuration;

    public static boolean logging;
    public static int chatboxMaxRadius;
    public static String prefixChat;
    public static int maxMessageLength;

    public static int maxTeslaRadius;
    public static int maxTeslaHeat;
    public static int teslaEnergy;
    public static float teslaAllDamage;

    public static int radarRange;
    public static double radarEnergyCost;

    public static int ldaMaxDistance;
    public static int ldaMaxPacketSize;

    public static boolean registerCreativeChatBox, registerChatBox, registerRadarUpgrade, registerTeslaUpgrade, registerRadar, registerLDA, registerDecorativeCable;


    public static void init(File file){
        configuration = new Configuration(file);
        configuration.load();

        chatboxMaxRadius = configuration.get("chatbox", "chatboxMaxRadius", 128, "").getInt();
        prefixChat = configuration.get("chatbox", "prefixChat", "@", "").getString();
        maxMessageLength = configuration.get("chatbox", "maxMessageLength", 100).getInt();

        maxTeslaRadius = configuration.get("tesla", "maxTeslaRadius", 10).getInt();
        maxTeslaHeat = configuration.get("tesla", "maxTeslaHeat", 100, "").getInt();
        teslaEnergy = configuration.get("tesla", "teslaEnergy", 1000, "").getInt();
        teslaAllDamage = (float) configuration.get("tesla", "teslaAllDamage", 10, "").getDouble();

        radarRange = configuration.get("radar", "radarRange", 15, "").getInt();
        radarEnergyCost = configuration.get("radar", "radarEnergyCost", 30).getDouble();

        registerCreativeChatBox = configuration.getBoolean("registerCreativeChatBox", "register", true, "");
        registerChatBox = configuration.getBoolean("registerChatBox", "register", true, "");
        registerRadarUpgrade = configuration.getBoolean("registerRadarUpgrade", "register", true, "");
        registerTeslaUpgrade = configuration.getBoolean("registerTeslaUpgrade", "register", true, "");
        registerRadar = configuration.getBoolean("registerRadar", "register", true, "");
        registerLDA = configuration.getBoolean("registerLDA", "register", true, "");
        registerDecorativeCable = configuration.getBoolean("registerDecorativeCable", "register", true, "");

        ldaMaxDistance = configuration.getInt("lda", "ldaMaxDistance", 2000, 0, 4000000, "");
        ldaMaxPacketSize = configuration.getInt("lda", "ldaMaxPacketSize", 1024 * 8, 0, 4000000, "");

        configuration.save();
    }
}
