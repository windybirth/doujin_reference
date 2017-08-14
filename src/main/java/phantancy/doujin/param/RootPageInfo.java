package phantancy.doujin.param;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RootPageInfo {

    private String titleEnglish;
    
    private String titleJapanese;
    
    private String language;
    
    private List<String> subPageUrls = new ArrayList<String>();
    
    private int length;
    
    public void addAllSubPageUrls(List<String> addUrls) {
        subPageUrls.addAll(addUrls);
    }
}
