package com.fuck.test.im.server.netty;

//import com.fuck.test.im.server.jedis.JedisProxy;
import com.fuck.test.im.server.netty.handler.IMWebSocketServerHandler;
import com.fuck.test.im.server.netty.handler.WebSocketIndexPageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
//import javax.annotation.Resource;

/**
 * DESCRIPTION:
 * 基于netty websocket 配置及启动
 * @author zouyan
 * @create 2018-01-03 下午2:22
 * created by fuck~
 **/
@Component
public class NettyServer implements Runnable{

    private static final Logger logger = LogManager.getLogger(NettyServer.class);

//    @Resource
//    private JedisProxy jedis;

    private final int port;
    private final String indexUrl;
    //农事无忧 主播url
    private final String anchorUrl;
    //农事无忧 游客url
    private final String visitorUrl;
    //ssl配置文件
    private final String sslPath;

    public NettyServer(@Value("${im.server.port}") int port, @Value("${im.index.url}") String indexUrl,
            @Value("${im.anchor.url}") String anchorUrl, @Value("${im.visitor.url}") String visitorUrl,
                       @Value("${im.ssl.path}") String sslPath) {
        this.port = port;
        this.indexUrl = indexUrl;
        this.anchorUrl = anchorUrl;
        this.visitorUrl = visitorUrl;
        this.sslPath = sslPath;
    }

    private static final String WEBSOCKET_PATH = "/websocket";

    //初始化用于Acceptor的主"线程池"
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    //初始化用于IO操作的从"线程池"
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    @PostConstruct
    public void defaultInitial() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            File certChainFile = new File(sslPath + "/server.crt");
            File keyFile = new File(sslPath + "/pkcs8_server.key");
            File rootFile = new File(sslPath + "/server.crt");

            final SslContext sslCtx = SslContextBuilder.forServer(certChainFile, keyFile).trustManager(rootFile).clientAuth(ClientAuth.NONE).build();

            //ssl
           // SelfSignedCertificate ssc = new SelfSignedCertificate();
           // final SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();

            //初始化ServerBootstrap,netty服务端应入口
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //配置serverBootstrap主从"线程池"
            serverBootstrap.group(bossGroup, workerGroup);
            //配置channel实例（channel类似于stream，channel相当于全双工、异步，stream半双工、同步）
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
            //配置SocketChannel处理器,添加实际业务处理handler
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(sslCtx.newHandler(ch.alloc()));//ssl handler
                    pipeline.addLast(new HttpServerCodec()); //HTTP请求的解码和编码
                    //把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
                    //原因: HTTP解码器会在每个HTTP消息中生成多个消息对象HttpRequest/HttpResponse,HttpContent,LastHttpContent
                    pipeline.addLast(new HttpObjectAggregator(65536)); //Http消息转换
                //    pipeline.addLast(new ChunkedWriteHandler()); //大文件支持
                    pipeline.addLast(new WebSocketServerCompressionHandler());//websocket数据压缩
                    //pipeline.addLast(new IMWebSocketServerHandler(jedis)); //自定义websocket业务处理
                    //websockethandler初始化websocket，如握手、ping、pong
                    pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
                    pipeline.addLast(new WebSocketIndexPageHandler(indexUrl, anchorUrl, visitorUrl));
                    pipeline.addLast(new IMWebSocketServerHandler()); //自定义websocket业务处理
                }
            });
            //绑定端口
            Channel channel = serverBootstrap.bind(port).sync().channel();
            logger.info("websocket started on port:{}", port);
            //当前线程阻塞 除非server socket关闭
            channel.closeFuture().sync();
        } catch (Exception e) {
            logger.error("netty server启动出错!", e);
        } finally {
            logger.info("netty server关闭");
            //退出netty 关闭主从"线程池"
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @PreDestroy
    public void shutdown() {
        try {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } catch (Exception e) {
            logger.error("netty server eventLoopGroup关闭出错！", e);
        }
    }
}
