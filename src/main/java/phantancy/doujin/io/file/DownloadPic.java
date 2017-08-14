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

public class DownloadPic {

    private static Log log = new Log(DownloadPic.class);
    
    private static final boolean overrideFile = false;

    // TODO 下载失败删除当前文件
    public void downloadPicByUrlList(List<String> picUrlList, String folderName) {
        log.writeLog("Get " + picUrlList.size() + " image urls.\n Download Start...");
        File folder = new File(folderName);
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
                    // 构造URL
                    URL url = new URL(imageUrl);
                    // 打开连接
                    URLConnection con = url.openConnection();
                    // 设置请求超时为5s
                    con.setConnectTimeout(500);
                    // 输入流
                    is = con.getInputStream();

                    // 1K的数据缓冲
                    byte[] bs = new byte[1024];
                    // 读取到的数据长度
                    int len;
                    // 输出的文件流
                    os = new FileOutputStream(imageFile);
                    // 开始读取
                    while ((len = is.read(bs)) != -1) {
                        os.write(bs, 0, len);
                    }
                    times += 3;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 完毕，关闭所有链接
                    try {
                        os.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (times > 3) {
                    break;
                }
            }
        }
    }
}
