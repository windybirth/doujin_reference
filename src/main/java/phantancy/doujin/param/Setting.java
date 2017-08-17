package phantancy.doujin.param;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import phantancy.doujin.common.Log;

public class Setting {

    public static final String username;
    
    public static final String password;
    
    public static final String localSavePath;
    
    static {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("conf/setting.properties")));
        }catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        username = prop.getProperty("username");
        password = prop.getProperty("password");
        if (prop.getProperty("localSavePath") == null) {
            localSavePath = "";
        } else {
            localSavePath = prop.getProperty("localSavePath");
        }
        // TODO is not empty
        if (username == null) {
            Log.writeLog("username need to be set in file conf/setting.properties", Setting.class);
            System.exit(1);
        }
        if (password == null) {
            Log.writeLog("password need to be set in file conf/setting.properties", Setting.class);
            System.exit(1);
        }
    }
}
