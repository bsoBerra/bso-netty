import java.util.Date;

/**
 * Created by Berdniky on 13.12.2014.
 */
public class IpStatistics {
    private String ip;
    private long ipRequestCount = 1;
    private Date lastIpRequestDate;

    public void increaseIpRequestCount() {
        ipRequestCount++;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getRequestCount() {
        return ipRequestCount;
    }

    public void setRequestCount(long ipRequestCount) {
        this.ipRequestCount = ipRequestCount;
    }

    public Date getLastIpRequestDate() {
        return lastIpRequestDate;
    }

    public void setLastIpRequestDate(Date lastIpRequestDate) {
        this.lastIpRequestDate = lastIpRequestDate;
    }
}
