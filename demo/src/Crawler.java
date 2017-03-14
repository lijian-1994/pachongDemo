import java.util.ArrayList;
import java.util.List;

/**
 * 宽度优先 
 * @author
 *
 */
public class Crawler {

    PaserUrlUtil paseUrlUtil = new PaserUrlUtil();

    /**
     * 初始化种子 
     * @param url
     */
    public void initCrawlerBySeed(String url){
        UrlTO urlTO = new UrlTO();
        urlTO.setDeptValue(0);
        urlTO.setUrl(url);
        LinkQueue.addUnVisitedUrl(urlTO);
        System.out.println("UrlTO-----"+urlTO);
    }

    /**
     * 宽度优先搜索 
     * @throws Exception
     */
    public void crawlerByBSF() throws Exception{
        // 种子url  
        String url = "http://www.shuoshuo520.com/";
        //种子入队  
        initCrawlerBySeed(url);
        System.out.println("feeds-----"+url);
        while(!LinkQueue.unVisitedUrlEmpty()){
            UrlTO visitUrl = (UrlTO)LinkQueue.unVisitedPoll();//将首元素出队
            if(visitUrl == null)
                continue;
            //放入已经访问的url中  

            List<UrlTO> unVisitUrlList = null;
            Integer deptValue = visitUrl.getDeptValue();
            String nextUrl = visitUrl.getUrl();

            LinkQueue.addVisitedUrl(nextUrl);
            System.out.println("正在处理的url实体--deptValue--"+deptValue+"--url--"+nextUrl);

            if(deptValue == 0){
                unVisitUrlList = paseUrlUtil.getCategoryUrls(nextUrl);
            }else if(deptValue == 1){
                unVisitUrlList = paseUrlUtil.getBookUrls(nextUrl);
            }else if(deptValue == 2){
                unVisitUrlList = paseUrlUtil.getDetailUrlList(nextUrl);
            }else if(deptValue == 3){
                unVisitUrlList = new ArrayList<UrlTO>();
                unVisitUrlList.add(paseUrlUtil.getToReadUrl(nextUrl));
            }else if(deptValue == 4){
                unVisitUrlList = paseUrlUtil.getChapterList(nextUrl);
            }else if(deptValue == 5){
                //最后一层
            }

            for(UrlTO urlTO: unVisitUrlList){
                LinkQueue.addUnVisitedUrl(urlTO);

            }



        }
    }
}  