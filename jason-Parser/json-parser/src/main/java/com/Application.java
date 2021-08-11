package com;

import com.model.Event;
import com.util.HSQLDBUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.util.Utility.*;
//main application to be run
public class Application {
    public static void main(String[] args) {
        String filepath = Application.class.getClassLoader().getResource("logfile.txt").getFile();
        //read file
        List<String> strings = readFile(filepath);
        //parse the event to java object
        List<Event> eventList = parseEvent(strings);
        System.out.println("Total Events which are present in the file: "+eventList);
        //calculate time
        Map<String, Long> filteredEvents = calculateTime(eventList);

        Map<String,Boolean> checkInserted = new HashMap<>();

        HSQLDBUtil hsqldbUtil = new HSQLDBUtil();
        hsqldbUtil.createTable();

        //insert record into table
        eventList.parallelStream()
                .forEach(event -> {
                    final String id = event.getId();
                    if(! checkInserted.containsKey(id)){
                        checkInserted.put(event.getId(),true);

                        long duration = filteredEvents.get(id);
                        boolean alert = duration > 4;

                        String type = "";
                        if(event.getType() != null){
                            type = event.getType();
                        }
                        String host = "";
                        if(event.getHost() != null){
                            host = event.getHost();
                        }

                        hsqldbUtil.insertRecord(id,Long.toString(duration),type,
                                host,Boolean.toString(alert));
                    }
                });

    }
}
