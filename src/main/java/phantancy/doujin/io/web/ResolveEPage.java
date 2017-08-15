package phantancy.doujin.io.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import phantancy.doujin.common.Log;
import phantancy.doujin.param.RootPageInfo;

public class ResolveEPage {

    private static Log log = new Log(ResolveEPage.class);

    private static final String dividePageMark = "/?p=";

    // ---- ROOT PAGE ----
    public RootPageInfo getRootPageInfo(String rootPageURL) {
        log.writeLog("Reading root page: " + rootPageURL);
        RootPageInfo rootPageInfo = new RootPageInfo();

        Document doc = readPgae(rootPageURL);
        HashMap<String, String> profileInfo = new HashMap<String, String>();
        Elements gddElements = doc.select("div[id=gdd]");
        if (gddElements.size() > 0) {
            Elements trElements = gddElements.get(0).getElementsByTag("tr");
            for (Element tr : trElements) {
                profileInfo.put(tr.select("td.gdt1").text(), tr.select("td.gdt2").text());
            }
        }
        // set language
        rootPageInfo.setLanguage(profileInfo.get("Language:").trim());
        // set length !!!
        String lengthStr = profileInfo.get("Length:").trim();
        lengthStr = lengthStr.replace("pages", "").trim();
        // default process has images
        int length = 1;
        try {
            length = Integer.parseInt(lengthStr);
        } catch (NumberFormatException e) {
            log.writeLog("Getting image amonut fail, length: " + lengthStr);
        }
        rootPageInfo.setLength(length);

        // set title
        Elements gd2Elements = doc.select("div[id=gd2]");
        if (gd2Elements.size() > 0) {
            rootPageInfo.setTitleEnglish(gd2Elements.get(0).select("h1[id=gn]").text());
            rootPageInfo.setTitleJapanese(gd2Elements.get(0).select("h1[id=gj]").text());
        }

        // get subPage
        int pageNumber = 0;
        while (pageNumber * 40 < rootPageInfo.getLength()) {
            log.writeLog("Getting page " + pageNumber + " urls");
            if (pageNumber == 0) {
                List<String> subPageUrls = getSubPageUrls(doc);
                rootPageInfo.addAllSubPageUrls(subPageUrls);
            } else {
                Document diviDoc = readPgae(rootPageURL + dividePageMark + pageNumber);
                List<String> subPageUrls = getSubPageUrls(diviDoc);
                rootPageInfo.addAllSubPageUrls(subPageUrls);
            }
            pageNumber++;
        }
        return rootPageInfo;
    }

    private List<String> getSubPageUrls(Document doc) {
        List<String> subPageUrls = new ArrayList<String>();

        // div with class=gdtm
        List<Element> imgRootElements = doc.select("div.gdtm");
        for (Element imgElement : imgRootElements) {
            Elements hrefElms = imgElement.select("a[href]");
            for (Element hrefElm : hrefElms) {
                subPageUrls.add(hrefElm.attr("href"));
            }
        }
        return subPageUrls;
    }

    // ---- SUB PAGE ----
    public List<String> getPicUrlList(List<String> subPageUrls) {
        List<String> picUrlList = new ArrayList<String>();
        Document doc = null;
        for (String subPageUrl : subPageUrls) {
            log.writeLog("Reading sub page: " + subPageUrl);
            // Get image page
            doc = readPgae(subPageUrl);
            // Get image Url
            List<Element> imgElements = doc.select("img[id=img]");
            String linkUrl = "";
            for (Element img : imgElements) {
                linkUrl = img.attr("src");
                picUrlList.add(linkUrl);
            }
        }
        return picUrlList;
    }

    // ---- COMMON ----
    private Document readPgae(String pageUrl) {
        return StroeCookie.getWithECookies(pageUrl);
    }

    private void SavePage(Document doc) {
        File file = new File("page_" + doc.title() + ".html");
        PrintWriter out;
        try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8")));
            out.write(doc.toString());
            out.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
