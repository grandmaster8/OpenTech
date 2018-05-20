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

    public static int scannerWidth, scannerHeight, scannerEnergyCount, scannerUsageCost;

    public static int NREfficiency;
    public static int NRFuelCount;

    public static boolean chatBoxCatchOnlyPlayer = false;

    public static boolean registerCreativeChatBox,
            registerChatBox,
            registerRadarUpgrade,
            registerTeslaUpgrade,
            registerRadar,
            registerLDA,
            registerPIB,
            registerScanner,
            registerNR,
            registerShieldGenerator,
            registerEnergyController,
            registerWorldInterface,
            registerPIM
    ;


    public static void init(File file){
        configuration = new Configuration(file);
        configuration.load();

        chatboxMaxRadius = configuration.get("chatbox", "chatboxMaxRadius", 128, "").getInt();
        maxMessageLength = configuration.get("chatbox", "maxMessageLength", 128).getInt();
        chatBoxCatchOnlyPlayer = configuration.get("chatbox", "charBoxCatchOnlyPlayer", false).getBoolean();

        maxTeslaRadius = configuration.get("TESLA_UPGRADE", "maxTeslaRadius", 10).getInt();
        maxTeslaHeat = configuration.get("TESLA_UPGRADE", "maxTeslaHeat",   100, "").getInt();
        teslaEnergyUsage = configuration.get("TESLA_UPGRADE", "teslaEnergyUsage",  10000, "").getInt();
        teslaDamage = (float) configuration.get("TESLA_UPGRADE", "teslaDamage",  10, "").getDouble();

        radarRange = configuration.get("RADAR", "radarRange", 15, "").getInt();
        radarEnergyUsage = configuration.get("RADAR", "radarEnergyUsage", 30).getDouble();

        registerCreativeChatBox = configuration.getBoolean("registerCreativeChatBox", "REGISTER", true, "");
        registerChatBox = configuration.getBoolean("registerChatBox", "REGISTER", true, "");
        registerRadarUpgrade = configuration.getBoolean("registerRadarUpgrade", "REGISTER", true, "");
        registerTeslaUpgrade = configuration.getBoolean("registerTeslaUpgrade", "REGISTER", true, "");
        registerRadar = configuration.getBoolean("registerRadar", "REGISTER", true, "");
        registerLDA = configuration.getBoolean("registerLDA", "REGISTER", true, "");
        registerPIB = configuration.getBoolean("registerPIB", "REGISTER", true, "");
        registerScanner = configuration.getBoolean("registerScanner", "REGISTER", true, "");
        registerNR = configuration.getBoolean("registerNR", "REGISTER", true, "");
        registerShieldGenerator = configuration.getBoolean("registerShieldGenerator", "REGISTER", true, "");
        registerEnergyController = configuration.getBoolean("registerEnergyController", "REGISTER", true, "");
        registerWorldInterface = configuration.getBoolean("registerWorldInterface", "REGISTER", true, "");
        registerPIM = configuration.getBoolean("registerPIM", "REGISTER", true, "");

        ldaMaxDistance = configuration.getInt("ldaMaxDistance", "LDA", 2000, 0, 1000000, "");
        ldaMaxPacketSize = configuration.getInt("ldaMaxPacketSize", "LDA", 1024 * 8, 0, 1000000, "");
        ldaEnergyUsage = configuration.getInt("ldaEnergyUsage", "LDA", 1000, 0, 1000000, "");

        pibUsingEnergy = configuration.getString("pibUsingEnergy", "pib", "distance * (distance / 16) * itemCount", "");
        pibUsingEnergyDimension = configuration.getInt("pibUsingEnergyDimension", "pib", 1000, 0, 100000000, "");

        scannerWidth = configuration.getInt("scannerWidth", "scanner", 40, 0, 1000000, "");
        scannerHeight = configuration.getInt("scannerHeight", "scanner", 40, 0, 1000000, "");
        scannerEnergyCount = configuration.getInt("scannerEnergyCount", "scanner", 40000, 0, 10000000, "");
        scannerUsageCost = configuration.getInt("scannerUsageCost", "scanner", 4000, 0, 10000000, "");

        NREfficiency = configuration.getInt("NREfficiency", "reactor", 10, 0, 10000000, "");
        NRFuelCount = configuration.getInt("NRFuelCount", "reactor", 100000, 0, 10000000, "");


        configuration.save();
    }
}
