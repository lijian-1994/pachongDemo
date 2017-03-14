import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/14.
 */

    public class LinkQueue {

        // 已经访问的url集合  
        private static Set<Object> visitedUrl=new HashSet<Object>();

        // 未被访问的url集合
        private static Queue<Object> unVisitedUrl=new LinkedList<Object>();

        public static Queue<Object> getUnVisitedUrl() {
            return unVisitedUrl;
        }

        public static void removeVisitedUrl(String url) {
            visitedUrl.remove(url);
        }

        public static Object unVisitedPoll() {
            //将首元素出队
            return unVisitedUrl.poll();
        }

        public static void addVisitedUrl(String url){
            System.out.println("已经访问的url--"+url);
            visitedUrl.add(url);
        }

        public static void addUnVisitedUrl(UrlTO url) {
            if(url!= null && !url.getUrl().trim().equals("")&& !visitedUrl.contains(url.getUrl())){
                System.out.println("想队列中添加新的url"+url.getUrl());
                //将此元素添加到队列的末尾
                unVisitedUrl.offer(url);
            }
        }

        public static Integer getVisitedUrlNum() {
            return visitedUrl.size();
        }

        public static  boolean unVisitedUrlEmpty() {
            return unVisitedUrl.isEmpty();
        }

}
