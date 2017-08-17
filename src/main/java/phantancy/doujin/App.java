package phantancy.doujin;

import java.util.List;

import phantancy.doujin.common.Log;
import phantancy.doujin.io.file.DownloadPic;
import phantancy.doujin.io.web.ResolveEPage;
import phantancy.doujin.param.CommonParam;
import phantancy.doujin.param.RootPageInfo;

public class App {

    public static void main(String[] args) {
        ResolveEPage resolveEPage = new ResolveEPage();
        for (String rootPageUrl : CommonParam.getRootPageUrl()) {
            // get root page info
            RootPageInfo rootPageInfo = resolveEPage.getRootPageInfo(rootPageUrl);
            // get pic urls
            List<String> picUrls = resolveEPage.getPicUrlList(rootPageInfo.getSubPageUrls());
            // set folder name.
            // TODO improve default name
            String folderName = rootPageUrl;
            if (rootPageInfo.getTitleJapanese() != null) {
                folderName = rootPageInfo.getTitleJapanese();
            }
            // Download pics
            DownloadPic downloadPic = new DownloadPic();
            downloadPic.downloadPicByUrlList(picUrls, folderName);
        }
    }

}
