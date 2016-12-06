package OpenTechnology;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by NEO on 01.02.2016.
 */
public class Config {
    public static Configuration configuration;

    public static boolean logging;
    public static int entityTeleportationLimit;
    public static int chatboxMaxRadius;
    public static String prefixChat;
    public static int maxMessageLength;
    public static int maxTeslaRadius;
    public static double teslaCooling;
    public static int teslaMaxHeat;
    public static int teslaEnergy;
    public static float teslaAllDamage;
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
        teslaCooling = configuration.get("tesla", "teslaCooling", 100, "ticks").getInt();
        teslaMaxHeat = configuration.get("tesla", "teslaMaxHeat", 10000, "ticks").getInt();
        teslaEnergy = configuration.get("tesla", "teslaEnergy", 1000, "ticks").getInt();
        teslaAllDamage = (float) configuration.get("tesla", "teslaAllDamage", 10, "ticks").getDouble();

        radarRange = configuration.get("radar", "radarRange", 15, "").getInt();
        radarEnergyCost = configuration.get("radar", "radarEnergyCost", 30).getDouble();

        registerAdminChatBox = configuration.getBoolean("registerCreativeChatBox", "register", true, "");
        registerChatBox = configuration.getBoolean("registerChatBox", "register", true, "");
        registerPlayerInventoryBinder = configuration.getBoolean("registerPlayerInventoryBinder", "register", true, "");
        registerRadarUpgrade = configuration.getBoolean("registerRadarUpgrade", "register", true, "");
        registerTeslaUpgrade = configuration.getBoolean("registerTeslaUpgrade", "register", true, "");
        registerRadar = configuration.getBoolean("registerRadar", "register", true, "");

        configuration.save();
    }
}
