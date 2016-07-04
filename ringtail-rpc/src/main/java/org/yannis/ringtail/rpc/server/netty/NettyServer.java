package org.yannis.ringtail.rpc.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.Server;
import org.yannis.ringtail.rpc.codec.NettyDecoder;
import org.yannis.ringtail.rpc.codec.NettyEncoder;
import org.yannis.ringtail.rpc.handler.netty.NettyHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 2016/7/1.
 */
public class NettyServer implements Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private static Map<String,SocketChannel> map = new ConcurrentHashMap<String, SocketChannel>();

    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    @Override
    public void doServie() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new NettyDecoder(RpcRequest.class))
                                    .addLast(new NettyEncoder(RpcResponse.class))
                                    .addLast(new NettyHandler()); // 处理 RPC 请求
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            String host = "127.0.0.1";
            int port = 8080;

            ChannelFuture future = bootstrap.bind(host, port).sync();
            LOGGER.info("server started on port {}", port);

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
