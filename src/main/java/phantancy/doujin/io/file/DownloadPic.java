package phantancy.doujin.io.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import phantancy.doujin.common.Log;
import phantancy.doujin.param.Setting;

public class DownloadPic {

    private static Log log = new Log(DownloadPic.class);
    
    private static final boolean overrideFile = false;

    // TODO remove current file while download fail
    public void downloadPicByUrlList(List<String> picUrlList, String folderName) {
        log.writeLog("Get " + picUrlList.size() + " image urls.\n Download Start...");
        String rootPath = Setting.localSavePath;
        String folderPath;
        if (rootPath.isEmpty()) {
            folderPath = getEnableFolderName(folderName);
        } else {
            folderPath = rootPath + "\\" + getEnableFolderName(folderName);
        }
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }
        log.writeLog("Folder Path: " + folder.getAbsolutePath());

        int number = 0;
        for (String imageUrl : picUrlList) {
            number++;
            // Get image name
            String[] urlSplit = imageUrl.split("/");
            String imageName = urlSplit[urlSplit.length - 1];
            log.writeLog("downloading picture [" + number + "/" + picUrlList.size() + "] ：" + imageName);
            // Duplicate image ignore
            File imageFile = new File(folder.getAbsolutePath() + "\\" + imageName);
            if (imageFile.exists()) {
                log.writeLog(imageName + " exist.");
                if (overrideFile) {
                    imageFile.delete();
                }
                continue;
            } else {
                try {
                    imageFile.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // Download
            int times = 0;
            while (true) {
                times++;
                InputStream is = null;
                OutputStream os = null;
                try {
                    URL url = new URL(imageUrl);
                    URLConnection con = url.openConnection();
                    // Timeout 5 seconds
                    con.setConnectTimeout(5000);
                    is = con.getInputStream();

                    // 1K buffer
                    byte[] bs = new byte[1024];
                    // read length
                    int len;
                    os = new FileOutputStream(imageFile);
                    // start reading
                    while ((len = is.read(bs)) != -1) {
                        os.write(bs, 0, len);
                    }
                    os.close();
                    is.close();
                    times += 3;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (times > 3) {
                    break;
                }
            }
        }
    }

    private String getEnableFolderName(String folderName) {
        return folderName.replaceAll("[/\\\\:*?<>|]","");
    }
}
