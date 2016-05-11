package OpenTechnology.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NEO on 16.02.2016.
 */
public class PointerList {
    private static HashMap<String, Pointer> uuids = new HashMap<String, Pointer>();
    public static Timer timer = new Timer();
    public static TimerTask task = new TimerTask() {
        @Override
        public void run() {
            for(Map.Entry<String, Pointer> entries : uuids.entrySet()){
                if(!entries.getValue().live)
                    uuids.remove(entries.getKey());
                entries.getValue().update();
            }
        }
    };

    public static boolean isUuid(String uuid){
        return uuids.containsKey(uuid);
    }

    public static void addUuid(String uuid, Pointer pointer){
        uuids.put(uuid, pointer);
    }

    public static Pointer removeUuid(String uuid){
        if(isUuid(uuid)){
            return uuids.remove(uuid);
        }
        return null;
    }

    public static Pointer getEntityId(String uuid){
        if(isUuid(uuid)){
            return uuids.get(uuid);
        }
        return null;
    }
}
