package phantancy.doujin.param;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import phantancy.doujin.common.Log;

public class Setting {

    public static final String username;
    
    public static final String password;
    
    public static final String localSavePath;
    
    static {
        Properties prop = new Properties();
        try {
        	FileInputStream input = new FileInputStream(new File("conf/setting.properties"));
            prop.load(new InputStreamReader(input, Charset.forName("UTF-8")));
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
