package org.yannis.ringtail.rpc.client.netty;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.yannis.ringtail.rpc.Client;
import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.codec.RpcDecoder;
import org.yannis.ringtail.rpc.codec.RpcEncoder;

/**
 * Created by dell on 2016/7/2.
 */
public class NettyClient extends SimpleChannelInboundHandler<RpcResponse> implements Client {

    private ChannelFuture future;

    private RpcResponse response;

    @Override
    public void connect() {
        String host = "127.0.0.1";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast(new RpcEncoder(RpcRequest.class))
                            .addLast(new RpcDecoder(RpcResponse.class))
                            .addLast(NettyClient.this);
                }
            });

            // Start the client.
            ChannelFuture future = b.connect(host, port).sync(); // (5)
            System.out.println("Connected to server...");

            // Wait until the connection is closed.
            future.channel().closeFuture().sync();
            this.future = future;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void send(RpcRequest request) {
        System.out.println("Prepare invoking...");
        try {
            future.channel().writeAndFlush(request).sync();

            if (response != null) {
                future.channel().closeFuture().sync();
            }
            System.out.println(JSON.toJSONString(response));
            //return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) throws Exception {
        this.response = response;
        System.out.println("Response: "+response);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server connected...");
        RpcRequest request = new RpcRequest();
        request.setRequestId("1000000001");
        request.setServiceName("org.yannis.rpc.test.TestService");
        ctx.write(request);
        ctx.flush();
    }
}
