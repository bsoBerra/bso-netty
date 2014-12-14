import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by Berdniky on 11.12.2014.
 */
public class MyServerRequestHandler {
    private final static int PAUSE = 10000;

    public FullHttpResponse uriHandler(String uri) throws InterruptedException {

        if (uri.equals("/hello")) {
            return getHelloPage();
        } else if (uri.contains("/redirect")) {
            return doRedirect(uri.substring(uri.indexOf("=")+1, uri.length()));
        }else if (uri.equals("/status")) {
            return getStatistics();
        } else return getNotFoundPage();
    }

    private FullHttpResponse getHelloPage() throws InterruptedException {
        String helloPage = "<body><h1>Hello world!</h1></body>";
        FullHttpResponse page = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(helloPage, CharsetUtil.UTF_8));
        Thread.sleep(PAUSE);
        return page;
    }

    private FullHttpResponse getNotFoundPage() {
        String notFoundPage = "<body><h1>Page not found</h1></body>";
        FullHttpResponse page = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(notFoundPage, CharsetUtil.UTF_8));
        return page;
    }

    private FullHttpResponse getStatistics() {
        String statistics = MyServerInitializer.myServerStatistics.getStatistics();
        FullHttpResponse page = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(statistics, CharsetUtil.UTF_8));
        return page;
    }

    private FullHttpResponse doRedirect (String uri) {
        FullHttpResponse page = new DefaultFullHttpResponse(HTTP_1_1,HttpResponseStatus.FOUND);
        page.headers().set(HttpHeaders.Names.LOCATION, uri);
        return page;
    }
}
