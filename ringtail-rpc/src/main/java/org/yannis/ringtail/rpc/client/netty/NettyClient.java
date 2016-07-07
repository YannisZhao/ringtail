package org.yannis.ringtail.rpc.client.netty;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.rpc.Client;
import org.yannis.ringtail.rpc.RpcException;
import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.codec.NettyDecoder;
import org.yannis.ringtail.rpc.codec.NettyEncoder;

/**
 * Created by dell on 2016/7/2.
 */
public class NettyClient extends SimpleChannelInboundHandler<RpcResponse> implements Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private String host;

    private int port;

    private Channel channel;

    private RpcResponse response;

    private EventLoopGroup workerGroup;

    public NettyClient(){
        workerGroup = new NioEventLoopGroup();
    }

    public NettyClient(String host, int port) {
        workerGroup = new NioEventLoopGroup();
        this.host = host;
        this.port = port;
    }

    @Override
    public void connect() {

        try {
            Bootstrap bootstrap = new Bootstrap(); // (1)
            bootstrap.group(workerGroup); // (2)
            bootstrap.channel(NioSocketChannel.class); // (3)
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.option(ChannelOption.SO_TIMEOUT, 5000);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast(new NettyEncoder(RpcRequest.class))
                            .addLast(new NettyDecoder(RpcResponse.class))
                            .addLast(NettyClient.this);
                }
            });

            // Start the client.
            ChannelFuture future = bootstrap.connect(host, port).sync(); // (5)

            if(future.isSuccess()) {
                System.out.println("Connected to server..."+host+":"+port);
                Channel channel = future.channel();
                this.channel = channel;
                System.out.println("Channel saved..."+channel);
                // Wait until the connection is closed.
                //channel.closeFuture().sync();
            }else {
                future.cause().printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void disconnect() {
        //workerGroup.shutdownGracefully();
    }

    @Override
    public RpcResponse send(RpcRequest request) throws RpcException {
        System.out.println("Prepare invoking...");
        boolean success = true;
        int timeout = 0;
        ChannelFuture future = null;
        try {

            future = channel.writeAndFlush(request).sync();

            /*if(future!=null){
                future.sync();
            }*/

            /*if (response != null) {
                channel.closeFuture().sync();
            }*/
            //Thread.sleep(5000);
            boolean sent = true;
                if (sent) {
                    timeout = 30000;
                    success = future.await(timeout);
                }
                Throwable cause = future.cause();
                if (cause != null) {
                    throw cause;
                }
            } catch (Throwable e) {
                throw new RpcException("Faild to invoking remote service", e);
            }

            /*synchronized (NettyClient.this) {
                try {
                    NettyClient.this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
        // Here is a big bug, hahaha^^^
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(! success) {
                throw new RpcException("Failed to send message  in timeout(" + timeout + "ms) limit");
            }
            if(future.isSuccess()){
                LOGGER.info("Response data: {}", JSON.toJSONString(response));
            }
            return response;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) throws Exception {
        this.response = response;
        System.out.println("Response: "+ JSON.toJSONString(response));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }


}
