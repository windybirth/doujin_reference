package phantancy.doujin;

import java.util.List;

import phantancy.doujin.io.file.DownloadPic;
import phantancy.doujin.io.web.ResolveEPage;
import phantancy.doujin.param.CommonParam;
import phantancy.doujin.param.RootPageInfo;

public class App {

    public static void main(String[] args) {
        // set root url
        CommonParam.setRootPageUrl(args[0]);
        
        ResolveEPage resolveEPage = new ResolveEPage();
        // get root page info
        RootPageInfo rootPageInfo = resolveEPage.getRootPageInfo(CommonParam.getRootPageUrl());
        // get pic urls
        List<String> picUrls = resolveEPage.getPicUrlList(rootPageInfo.getSubPageUrls());
        // set folder name
        String folderName = CommonParam.getRootPageUrl();
        if (rootPageInfo.getTitleJapanese() != null) {
            folderName = rootPageInfo.getTitleJapanese();
        }
        // Download pics
        DownloadPic downloadPic = new DownloadPic();
        downloadPic.downloadPicByUrlList(picUrls, folderName);
    }

}
