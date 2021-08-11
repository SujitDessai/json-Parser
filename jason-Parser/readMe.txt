This java application was developed using Java 8,Intellij IDE 

This is a Java application to read events logged from custom build server that logs file 
to a file named logfile.txt
High level flow of the javaapplication is as below
1)readfile
2)parse events from the file
3)filter events and calcuate time for individual events
4)connect to database and insert the events in table

logfile.txt is maintained under resources folder
The main code for reading this file is present in the Application class
event details are maintained in class Event .This is a getter/setter method to maintain
all parameters like id,state,type,host and timestamp related to the events

There is a Utility class built under util 
this class will do the following
->check if required file exists
->logs the total events in the file
->it will parse all the events present in the file
->it also carries a method for eventimestamp collection to capture start and end time of individual events
->it will also filter out those events which take more then 4ms and returns those as map string

There is a HSQLDBUtil java class 
this class will do the following
->it will create a table using the JDBCUtils class
->all the events details (id,duration,type,host & alerts) from the log file are inserted into testdb (file based db)

JDBCUtils java class is used for making a connection to a databse using jdbc:hsqldb:file:testdb driver

Note:-
try catch block has been used in this project as and where applicable
it uses different Java collections like List,ArrayList,HashMap(to create keyvalue pair)
logger.fine is used for logging purpose
use of parallelStream for multiple events insertion into database






