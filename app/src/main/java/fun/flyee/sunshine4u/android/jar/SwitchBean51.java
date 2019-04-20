package fun.flyee.sunshine4u.android.jar;

import java.io.Serializable;

public class SwitchBean51 {

    /**
     * code : 200
     * data : {"iswap":"0","ismust":"0","wapurl":"","upurl":"","appurl":"","qq":"","qqkey":"","iplist":""}
     */

    private String code;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * iswap : 0
         * ismust : 0
         * wapurl :
         * upurl :
         * appurl :
         * qq :
         * qqkey :
         * iplist :
         */

        private String iswap;
        private String ismust;
        private String wapurl;
        private String upurl;
        private String appurl;
        private String qq;
        private String qqkey;
        private String iplist;

        public String getIswap() {
            return iswap;
        }

        public void setIswap(String iswap) {
            this.iswap = iswap;
        }

        public String getIsmust() {
            return ismust;
        }

        public void setIsmust(String ismust) {
            this.ismust = ismust;
        }

        public String getWapurl() {
            return wapurl;
        }

        public void setWapurl(String wapurl) {
            this.wapurl = wapurl;
        }

        public String getUpurl() {
            return upurl;
        }

        public void setUpurl(String upurl) {
            this.upurl = upurl;
        }

        public String getAppurl() {
            return appurl;
        }

        public void setAppurl(String appurl) {
            this.appurl = appurl;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getQqkey() {
            return qqkey;
        }

        public void setQqkey(String qqkey) {
            this.qqkey = qqkey;
        }

        public String getIplist() {
            return iplist;
        }

        public void setIplist(String iplist) {
            this.iplist = iplist;
        }
    }
}
