import java.util.Date;

/**
 * Created by Berdniky on 13.12.2014.
 */
public class RequestInformation {
    private String ip = "";
    private String uri = "";
    private Date timeStamp = new Date();
    private int recievedBytes = 0;
    private int sentBytes = 0;
    private double speed = 0;

    public RequestInformation(String ip, String uri, int recievedBytes, int sentBytes, double speed) {
        this.ip = ip;
        this.uri = uri;
        this.recievedBytes = recievedBytes;
        this.sentBytes = sentBytes;
        this.speed = speed;
    }

    public String getIp() {
        return ip;
    }

    public String getUri() {
        return uri;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public int getSentBytes() {
        return sentBytes;
    }

    public int getRecievedBytes() {
        return recievedBytes;
    }

    public double getSpeed() {
        return speed;
    }
}
