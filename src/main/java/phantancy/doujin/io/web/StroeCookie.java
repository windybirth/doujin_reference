package phantancy.doujin.io.web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import lombok.NonNull;
import phantancy.doujin.common.Log;
import phantancy.doujin.param.Setting;

public class StroeCookie {

    private static Log log = new Log(StroeCookie.class);
    
    private static Map<String,String> cookies;
    
    public static Document getWithECookies(@NonNull String pageUrl) {
        int times = 0;
        while (true) {
            times++;
            try {
                if (cookies == null) {
                    loginE();
                }
                // use cookies for access
                Document document = Jsoup.connect(pageUrl).cookies(cookies).post();
                document.charset(Charset.forName("UTF-8"));
                return document;
            } catch (IOException e) {
                log.writeLog("Connect fail " + times + " time, page url: " + pageUrl);
                e.printStackTrace();
                if (times > 5) {
                    return null;
                }
            }
        }
    }
    
    private static void loginE() throws IOException {
        String username = Setting.username;
        String password = Setting.password;
        // set post data in map
        Map<String,String> map = new HashMap<String,String>();
        map.put("CookieDate", "1");
        map.put("b", "d");
        map.put("bt", "");
        map.put("UserName", username);
        map.put("PassWord", password);
        
        // send request
        Connection conn = Jsoup.connect("https://forums.e-hentai.org/index.php?act=Login&amp;CODE=01");
        conn.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)");
        Response response = conn.ignoreContentType(true).method(Method.POST).data(map).execute();
        
        // set cookies
        cookies = response.cookies();
        // TODO the cookies should get from response
        cookies.put("igneous", "4bab3ce5d");
        cookies.put("ipb_member_id", "2458221");
        cookies.put("ipb_pass_hash", "51b89707d25d9fbbcdc665693b018d47");
        cookies.put("s", "9b66acb3f");
        cookies.put("lv", "1502798936-1502815273");
        Set<String> keys = cookies.keySet();
        for (String key : keys) {
            log.writeLog(key + ":" + cookies.get(key));
        }
    }
}
