public class Main {

    public static void main(String[] args) {
        Crawler crawler=new Crawler();
        try {
            crawler.crawlerByBSF();
            System.out.print("end");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
