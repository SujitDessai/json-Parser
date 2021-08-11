package com.util;

import com.google.gson.Gson;
import com.model.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Utility {
    final static String STARTED = "STARTED";
    final static String FINISHED = "FINISHED";
    static Logger logger = Logger.getLogger(Utility.class.getName());
    //method to read the file containing events
    public static List<String> readFile(String filePath)  {
        try  {
            File file = new File(filePath);
            //File is found
            logger.fine("File Found : " + file.exists());
            if(! file.exists())
                throw new FileNotFoundException("File is available on give location: "+file.getPath());

            List<String> strList = Files.readAllLines(file.toPath());
            logger.fine("Total events in file: "+strList.size());
            //Stream<String> lines = strList.stream();
            //lines.forEach(System.out::println);
            return strList;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<Event>  parseEvent(List<String> events){
        List<Event> eventList = new ArrayList<>();
        events.forEach(event -> {
            Event obj = new Gson().fromJson(event, Event.class);
            eventList.add(obj);
        });

        return eventList;
    }

    public static Map<String, Long> calculateTime(List<Event> eventList){
        Map<String, List<Event>> collect = eventList.stream()
                .collect(Collectors.groupingBy(e -> e.getId()));

        //System.out.println("Map: "+collect);

        //Sort the list of event based on state
        Map<String,Long> eventTimeMap = new HashMap<>();
        collect.forEach((k,v) -> {
            final Long[] start = new Long[1];
            final Long[] finish = new Long[1];

            v.forEach(event -> {
                if(event.getState().equals(STARTED)){
                    start[0] = Long.parseLong(event.getTimestamp());
                }
                if(event.getState().equals(FINISHED)){
                    finish[0] = Long.parseLong(event.getTimestamp());
                }

            });
            eventTimeMap.put(k,(finish[0]-start[0]));
            logger.fine("Event ID: "+k+" Time taken: "+(finish[0]-start[0]));

        });

        //filter events which are taking more than 4 milli-seconds
        Map<String, Long> filteredEvent = eventTimeMap.entrySet().stream()
                .filter(m -> m.getValue() > 4)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        logger.fine("Filtered Map: "+filteredEvent);

        return eventTimeMap;
    }
}
