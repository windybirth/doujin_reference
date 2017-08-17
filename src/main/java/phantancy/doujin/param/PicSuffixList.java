package phantancy.doujin.param;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PicSuffixList {

    private static final List<String> suffixList;
    static {
        List<String> list = new ArrayList<String>();
        list.add("jpg");
        list.add("png");
        suffixList = Collections.unmodifiableList(list);
    }
    
    public static List<String> getSuffixList() {
        return suffixList;
    }
}
