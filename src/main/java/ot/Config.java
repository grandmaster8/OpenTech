package ot;

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
    public static int teslaEnergyUsage;
    public static float teslaDamage;

    public static int radarRange;
    public static double radarEnergyUsage;

    public static int ldaMaxDistance;
    public static int ldaEnergyUsage;
    public static int ldaMaxPacketSize;

    public static String pibUsingEnergy;
    public static int pibUsingEnergyDimension;

    public static boolean registerCreativeChatBox,
            registerChatBox,
            registerRadarUpgrade,
            registerTeslaUpgrade,
            registerRadar,
            registerLDA,
            registerDecorativeCable,
            registerPIB
    ;


    public static void init(File file){
        configuration = new Configuration(file);
        configuration.load();

        chatboxMaxRadius = configuration.get("chatbox", "chatboxMaxRadius", 128, "").getInt();
        prefixChat = configuration.get("chatbox", "prefixChat", "@", "").getString();
        maxMessageLength = configuration.get("chatbox", "maxMessageLength", 100).getInt();

        maxTeslaRadius = configuration.get("tesla", "maxTeslaRadius", 10).getInt();
        maxTeslaHeat = configuration.get("tesla", "maxTeslaHeat", 100, "").getInt();
        teslaEnergyUsage = configuration.get("tesla", "teslaEnergyUsage", 1000, "").getInt();
        teslaDamage = (float) configuration.get("tesla", "teslaDamage", 10, "").getDouble();

        radarRange = configuration.get("radar", "radarRange", 15, "").getInt();
        radarEnergyUsage = configuration.get("radar", "radarEnergyUsage", 30).getDouble();

        registerCreativeChatBox = configuration.getBoolean("registerCreativeChatBox", "register", true, "");
        registerChatBox = configuration.getBoolean("registerChatBox", "register", true, "");
        registerRadarUpgrade = configuration.getBoolean("registerRadarUpgrade", "register", true, "");
        registerTeslaUpgrade = configuration.getBoolean("registerTeslaUpgrade", "register", true, "");
        registerRadar = configuration.getBoolean("registerRadar", "register", true, "");
        registerLDA = configuration.getBoolean("registerLDA", "register", true, "");
        registerDecorativeCable = configuration.getBoolean("registerDecorativeCable", "register", true, "");
        registerPIB = configuration.getBoolean("registerPIB", "register", true, "");

        ldaMaxDistance = configuration.getInt("lda", "ldaMaxDistance", 2000, 0, 1000000, "");
        ldaMaxPacketSize = configuration.getInt("lda", "ldaMaxPacketSize", 1024 * 8, 0, 1000000, "");
        ldaEnergyUsage = configuration.getInt("lda", "ldaEnergyUsage", 1000, 0, 1000000, "");

        pibUsingEnergy = configuration.getString("pib", "pibUsingEnergy", "(distance * (distance / 16)) * itemCount", "");
        pibUsingEnergyDimension = configuration.getInt("pib", "pibUsingEnergyDimension", 1000, 0, 100000000, "");

        configuration.save();
    }
}
