import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by Berdniky on 11.12.2014.
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    String ip;
    String uri;
    int recievedBytes = 0;
    int sentBytes = 0;
    double speed = 0;

    MyServerHandler(String ip) {
        this.ip = ip;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        speed = System.currentTimeMillis();
        MyServerInitializer.myServerStatistics.addActiveConnection();
        MyServerInitializer.myServerStatistics.increaseRequestCount();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        MyServerInitializer.myServerStatistics.removeActiveConnection();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        recievedBytes += msg.toString().length();
        if(msg instanceof HttpRequest) {

            uri = ((HttpRequest) msg).getUri();
            if(uri.contains("/redirect?url=")) {
                MyServerInitializer.myServerStatistics.addRedirectUrlRequestCountMap(uri.substring(uri.indexOf("=")+1));
            }

            FullHttpResponse response = new MyServerRequestHandler().uriHandler((uri));
            if(response != null) {
                this.sentBytes = response.content().writerIndex();
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            }

        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        speed = (System.currentTimeMillis() - speed) / 1000;
        speed = (recievedBytes + sentBytes) / speed;
        speed = new BigDecimal(speed).setScale(2, RoundingMode.UP).doubleValue();

        RequestInformation requestInformation = new RequestInformation(ip, uri, recievedBytes, sentBytes, speed);
        MyServerInitializer.myServerStatistics.addRequestInformation(requestInformation);

        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
