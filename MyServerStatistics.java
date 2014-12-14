import java.util.*;

/**
 * Created by Berdniky on 13.12.2014.
 */
public class MyServerStatistics {

    private static MyServerStatistics instance = null;

    private long requestCount = -2;
    private Map<String, IpStatistics> ipStatisticsMap = new HashMap<String, IpStatistics>();
    private Map<String, Integer> redirectUrlRequestCountMap = new HashMap<String, Integer>();
    private long activConection = 0;
    private List<RequestInformation> requestInformationList = new LinkedList<RequestInformation>();

    public synchronized static MyServerStatistics getInstance() {
        if (instance == null) {
            instance = new MyServerStatistics();
        }
        return instance;
    }

    public synchronized void increaseRequestCount() {
        requestCount++;
    }

    public synchronized void addRequestInformation(RequestInformation requestInformation) {
        String ip = requestInformation.getIp();
        Date date = requestInformation.getTimeStamp();

        if(requestInformationList.size() > 15) {
            requestInformationList.remove(0);
            requestInformationList.add(requestInformation);
        } else requestInformationList.add(requestInformation);

        if (ipStatisticsMap.get(ip) == null) {
            IpStatistics ipStatistics = new IpStatistics();
            ipStatistics.setIp(ip);
            ipStatistics.setLastIpRequestDate(date);
            ipStatisticsMap.put(ip, ipStatistics);
        } else {
            IpStatistics ipStatistics = ipStatisticsMap.get(ip);
            ipStatistics.increaseIpRequestCount();
            ipStatistics.setLastIpRequestDate(date);
        }
    }

    public synchronized void addActiveConnection() {
        activConection++;
    }

    public synchronized void removeActiveConnection() {
        activConection--;
    }

    public synchronized void addRedirectUrlRequestCountMap(String url) {
        if (redirectUrlRequestCountMap.get(url) == null) {
            redirectUrlRequestCountMap.put(url, 1);
        } else {
            redirectUrlRequestCountMap.put(url, redirectUrlRequestCountMap.get(url)+1);
        }
    }

    public synchronized String getStatistics() {
        StringBuffer statisticsPage = new StringBuffer();
        statisticsPage.append("<html><head><h1>STATISTICS<h1></head>");
        statisticsPage.append("<table><tr><th>Total request number: ").append(requestCount).append("</th></tr></table>");

        statisticsPage.append("<table border = 1><tr><th>Ip</th><th>Request count</th><th>Last request date</th></tr>");
        for (String ip : ipStatisticsMap.keySet()) {
            statisticsPage.append("<tr><th>" + ip + "</th>");
            statisticsPage.append("<th>" + ipStatisticsMap.get(ip).getRequestCount() + "</th>");
            statisticsPage.append("<th>" + ipStatisticsMap.get(ip).getLastIpRequestDate() + "</th></tr>");
        }
        statisticsPage.append("</table>");

        statisticsPage.append("<table border = 1><tr><th>Redirect url</th><th>Redirect count</th></tr>");
        for(String url : redirectUrlRequestCountMap.keySet()) {
            statisticsPage.append("<tr><th>" + url + "</th>");
            statisticsPage.append("<th>" + redirectUrlRequestCountMap.get(url) + "</th></tr>");
        }
        statisticsPage.append("</table>");

        statisticsPage.append("<table><tr><th>Active connects: ").append(activConection).append("</th></tr></table>");

        statisticsPage.append("<table border = 1><tr><th>IP</th><th>Uri</th><th>Time stamp</th><th>Sent bytes</th><th>Received Bytes</th><th>Speed (bytes/sec)</th></tr>");
        for(RequestInformation requestInformation : requestInformationList){
            statisticsPage.append("<tr><th>" + requestInformation.getIp() + "</th>");
            statisticsPage.append("<th>" + requestInformation.getUri() + "</th>");
            statisticsPage.append("<th>" + requestInformation.getTimeStamp() + "</th>");
            statisticsPage.append("<th>" + requestInformation.getSentBytes() + "</th>");
            statisticsPage.append("<th>" + requestInformation.getRecievedBytes() + "</th>");
            statisticsPage.append("<th>" + requestInformation.getSpeed() + "</th>");
        }
        statisticsPage.append("</table>");
        statisticsPage.append("</html>");

        return statisticsPage.toString();
    }

}