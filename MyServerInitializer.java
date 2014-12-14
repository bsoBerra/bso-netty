import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Created by Berdniky on 11.12.2014.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel>{
    public static MyServerStatistics myServerStatistics = MyServerStatistics.getInstance();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        String ip = socketChannel.remoteAddress().getHostString();

        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("handler", new MyServerHandler(ip));
    }
}
