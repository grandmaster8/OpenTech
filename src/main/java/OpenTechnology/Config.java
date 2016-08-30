package OpenTechnology;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by NEO on 01.02.2016.
 */
public class Config {
    public static Configuration configuration;

    public static boolean logging;
    public static int timeLimit;
    public static int entityTeleportationLimit;
    public static int chatboxMaxRadius;
    public static String prefixChat;
    public static int maxMessageLength;
    public static int maxTeslaRadius;
    public static double speedTeslaCharge;
    public static double maxTeslaCharge;
    public static double teslaCooling;
    public static double teslaHeatPercent;
    public static int teslaTimeReload;

    public static int teslaCoolingWaterFactor;
    public static int teslaCoolingIceFactor;
    public static int teslaCoolingAirFactor;

    public static double binderEnergyFactor;
    public static int radarRange;
    public static double radarEnergyCost;

    public static boolean registerAdminChatBox, registerChatBox, registerPlayerInventoryBinder, registerTeleporter, registerRadarUpgrade, registerTeslaUpgrade, registerTesseractUpgrade, registerRadar;


    public static void init(File file){
        configuration = new Configuration(file);
        configuration.load();
        entityTeleportationLimit = configuration.get("game", "entityTeleportationLimit", 5, "Limit the number of things to tesseract.").getInt();

        chatboxMaxRadius = configuration.get("chatbox", "chatboxMaxRadius", 128, "").getInt();
        prefixChat = configuration.get("chatbox", "prefixChat", "@", "").getString();
        maxMessageLength = configuration.get("chatbox", "maxMessageLength", 100).getInt();

        maxTeslaRadius = configuration.get("tesla", "maxTeslaRadius", 10).getInt();
        speedTeslaCharge = configuration.get("tesla", "speedTeslaCharge", 200.0).getDouble();
        maxTeslaCharge = configuration.get("tesla", "maxTeslaCharge", 3000.0).getDouble();
        teslaTimeReload = configuration.get("tesla", "teslaTimeReload", 90, "ticks").getInt();
        teslaCooling = configuration.get("tesla", "teslaCooling", 100, "ticks").getInt();
        teslaHeatPercent = configuration.get("tesla", "teslaHeatPercent", 10, "ticks").getInt();
        teslaCoolingWaterFactor = configuration.get("tesla", "teslaCoolingWaterFactor", 2, "").getInt();
        teslaCoolingIceFactor = configuration.get("tesla", "teslaCoolingIceFactor", 4, "").getInt();
        teslaCoolingAirFactor = configuration.get("tesla", "teslaCoolingAirFactor", 1, "").getInt();

        binderEnergyFactor = configuration.get("binder", "binderEnergyFactor", 30, "").getDouble();

        radarRange = configuration.get("radar", "radarRange", 15, "").getInt();
        radarEnergyCost = configuration.get("radar", "radarEnergyCost", 30).getDouble();

        registerAdminChatBox = configuration.getBoolean("registerAdminChatBox", "register", true, "");
        registerChatBox = configuration.getBoolean("registerChatBox", "register", true, "");
        registerPlayerInventoryBinder = configuration.getBoolean("registerPlayerInventoryBinder", "register", true, "");
        registerTeleporter = configuration.getBoolean("registerTeleporter", "register", true, "");
        registerRadarUpgrade = configuration.getBoolean("registerRadarUpgrade", "register", true, "");
        registerTeslaUpgrade = configuration.getBoolean("registerTeslaUpgrade", "register", true, "");
        registerTesseractUpgrade = configuration.getBoolean("registerTesseractUpgrade", "register", true, "");
        registerRadar = configuration.getBoolean("registerRadar", "register", true, "");

        configuration.save();
    }
}
