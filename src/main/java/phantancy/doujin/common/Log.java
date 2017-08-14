package phantancy.doujin.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private Class<?> classLog = null;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    public Log(Class<?> clazz) {
        classLog = clazz;
    }

    public void writeLog(String message) {
        String messageLog = dateFormat.format(new Date()) + " " + classLog.getName() + ": " + message;
        System.out.println(messageLog);
    }
}
