package ot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Avaja on 09.03.2017.
 */
public class Profiler {

    private HashMap<String, List<Long>> sections = new HashMap<String, List<Long>>();
    private String currentSection;
    private boolean isStart = false;
    private long startTime = 0;
    private int depth;

    public Profiler(int depth) {
        this.depth = depth;
    }

    public void start(String name){
        if(isStart)
            end();
        isStart = true;
        startTime = System.nanoTime();
        currentSection = name;
    }

    public void end() {
        long delta = System.nanoTime() - startTime;
        List<Long> time;
        if(sections.containsKey(currentSection))
            time = sections.get(currentSection);
        else
            time = new ArrayList<Long>();

        time.add(delta);
        if(time.size() >= depth)
            time.remove(0);

        sections.put(currentSection, time);
        isStart = false;
    }

    public long getTime(String name){
        if(sections.containsKey(name)){
            List<Long> time = sections.get(name);
            long sum = 0;
            for(int i = 0; i < time.size(); i++) {
                sum += time.get(i);
            }

            return (long) (sum / (time.size() + 0.0f));
        }
        return 0;
    }

    public String getInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        if(sections.size() > 0){
            for(Map.Entry<String, List<Long>> entry : sections.entrySet()){
                stringBuilder.append(entry.getKey() + " " + String.format("%.2f", getTime(entry.getKey()) / 1000.0 / 1000.0) + "ms" + "\n");
            }
        }else{
            stringBuilder.append("sections empty.");
        }
        return stringBuilder.toString();
    }
}
