/**
 * Created by Administrator on 2017/3/14.
 */
public class UrlTO {

        private Integer deptValue;

        private String url;

        public Integer getDeptValue() {
            return deptValue;
        }

        public void setDeptValue(Integer deptValue) {
            this.deptValue=deptValue;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url=url;
        }

        public String toString(){
            return "dept="+deptValue+"--url--"+url;
        }


}
