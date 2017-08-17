package phantancy.doujin.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private Class<?> classLog = null;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    public Log(Class<?> clazz) {
        classLog = clazz;
    }

    // TODO save log in log file
    public void writeLog(String message) {
        writeLog(message, classLog);
    }
    
    public static void writeLog(String message, Class<?> clazz) {
        String messageLog = dateFormat.format(new Date()) + " " + clazz.getName() + ": " + message;
        System.out.println(messageLog);
    }
}
