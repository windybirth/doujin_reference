package phantancy.doujin.param;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CommonParam{
    
    private static final List<String> rootPageUrls;
    
    static {
        List<String> tempList = new ArrayList<String>();
        // read file
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream("conf/urlList.txt");
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                tempList.add(line);
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            if (sc != null) {
                sc.close();
            }
        }
        
        rootPageUrls = Collections.unmodifiableList(tempList);
    }
    
    
    public static List<String> getRootPageUrl() {
        return rootPageUrls;
    }
}
