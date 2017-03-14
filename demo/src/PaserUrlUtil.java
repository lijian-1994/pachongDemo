/**
 * Created by Administrator on 2017/3/14.
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class PaserUrlUtil {
    private HttpClient httpClient=new DefaultHttpClient();

    /**
     * 获得html的string字符串
     * @param url
     * @return
     * @throws Exception
     */
    public String getHtmlStr(String url) throws Exception {
        HttpGet httpGet=new HttpGet(url);
        HttpResponse response;
        String htmlStr=null;
        try {
            response=httpClient.execute(httpGet);
            HttpEntity entity=response.getEntity();
            if(entity != null) {
                htmlStr=new String(EntityUtils.toString(entity));
                htmlStr=new String(htmlStr.getBytes("ISO-8859-1"), "gbk"); // 读取乱码解决
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return htmlStr;
    }

    /**
     * 获得document
     * @param url
     * @return
     * @throws Exception
     */
    public Document getDocument(String url) throws Exception{
        Thread.currentThread().sleep(5000*2);
        return Jsoup.parse(getHtmlStr(url));
    }

    /**
     * 获得种类url连接
     * @return
     * @throws Exception
     */
    public List<UrlTO> getCategoryUrls(String url) throws Exception{
        Document doc = getDocument(url);
        List<UrlTO> urlList = new ArrayList<UrlTO>();
        Elements elements = doc.select(".navlist").select("li").select("a");
        String categoryUrl= null;
        UrlTO urlTO=null;
        for(Element element:elements){
            categoryUrl = element.attr("href");
            urlTO = new UrlTO();
            urlTO.setDeptValue(1);
            urlTO.setUrl(categoryUrl);
            urlList.add(urlTO);
        }
        return urlList;
    }

    /***
     * 通过分类url获得所有的该类下书籍url
     * @param categoryUrl
     * @return
     * @throws Exception
     */
    public List<UrlTO> getBookUrls(String categoryUrl) throws Exception{
        System.out.println("bookUrls-处理进入 deptvalue-==1-");
        List<UrlTO> urlTOList = new ArrayList<UrlTO>();
        UrlTO urlTO = new UrlTO();
        urlTO.setDeptValue(2);
        String nextUrl = getNextBookUrl(categoryUrl);
        while(nextUrl != null && !nextUrl.trim().equals("")){
            System.out.println("bookUrls--"+nextUrl);
            urlTO.setUrl(nextUrl);
            nextUrl = getNextBookUrl(nextUrl);
            urlTOList.add(urlTO);
        }
        return urlTOList;
    }

    /**
     * 获得下一个分页连接
     * @param categoryUrl
     * @return
     * @throws Exception
     */
    public String getNextBookUrl(String categoryUrl) throws Exception{
        Document doc = getDocument(categoryUrl);
        Elements elements = doc.select("#pagelink").select("strong +a");
        if(elements == null){
            return null;
        }
        return elements.first().attr("href");
    }

    /**
     * 获取每个页面书籍详情url
     * @param categoryUrl
     * @return
     * @throws Exception
     */
    public List<UrlTO> getDetailUrlList(String categoryUrl) throws Exception{
        Document doc = getDocument(categoryUrl);
        Elements elements = doc.select(".grid").select("tr");
        String detailUrl = null;
        List<UrlTO> urlTOList = new ArrayList<UrlTO>();
        UrlTO urlTO = new UrlTO();
        for(Element element:elements){
            detailUrl =  element.select("td").first().attr("href");
            urlTO.setDeptValue(3);
            urlTO.setUrl(detailUrl);
            urlTOList.add(urlTO);
        }
        return urlTOList;
    }

    public UrlTO getToReadUrl(String detailUrl) throws Exception{
        Document doc = getDocument(detailUrl);
        UrlTO urlTO = new UrlTO();
        String toReadUrl=doc.select("#bt_1").select("a").first().attr("href");
        urlTO.setDeptValue(4);
        urlTO.setUrl(toReadUrl);
        return urlTO;
    }

    /**
     * 获得chapter的url
     * @param detailUrl
     * @return
     * @throws Exception
     */
    public List<UrlTO> getChapterList(String detailUrl) throws Exception {

        Document doc= getDocument(detailUrl);
        Elements elements=doc.select(".list").select("dd").select("a");
        List<UrlTO> urlList=new ArrayList<UrlTO>();
        UrlTO urlTO = new UrlTO();
        String chapterUrl= null;
        for(Element element: elements) {
            chapterUrl = detailUrl + element.attr("href");
            urlTO.setDeptValue(5);
            urlTO.setUrl(chapterUrl);
        }
        return urlList;
    }

    /**
     *
     * @param chapterUrl
     * @return
     * @throws Exception
     */
    public Chapter getChapter(String chapterUrl) throws Exception {
        Document doc=getDocument(chapterUrl);
        Chapter chapter=new Chapter();
        String name=doc.select("h1").text();
        String content=doc.select(".width").text();
        chapter.setName(name);
        chapter.setContent(content);
        return chapter;
    }


}
