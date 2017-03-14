import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/14.
 */

    public class LinkQueue {

        // �Ѿ����ʵ�url����  
        private static Set<Object> visitedUrl=new HashSet<Object>();

        // δ�����ʵ�url����
        private static Queue<Object> unVisitedUrl=new LinkedList<Object>();

        public static Queue<Object> getUnVisitedUrl() {
            return unVisitedUrl;
        }

        public static void removeVisitedUrl(String url) {
            visitedUrl.remove(url);
        }

        public static Object unVisitedPoll() {
            //����Ԫ�س���
            return unVisitedUrl.poll();
        }

        public static void addVisitedUrl(String url){
            System.out.println("�Ѿ����ʵ�url--"+url);
            visitedUrl.add(url);
        }

        public static void addUnVisitedUrl(UrlTO url) {
            if(url!= null && !url.getUrl().trim().equals("")&& !visitedUrl.contains(url.getUrl())){
                System.out.println("�����������µ�url"+url.getUrl());
                //����Ԫ����ӵ����е�ĩβ
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
