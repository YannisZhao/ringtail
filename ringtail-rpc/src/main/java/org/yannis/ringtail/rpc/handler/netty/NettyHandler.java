package org.yannis.ringtail.rpc.handler.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.handler.Handler;

import java.util.Date;

/**
 * Created by dell on 2016/7/1.
 */
public class NettyHandler extends SimpleChannelInboundHandler<RpcRequest> implements Handler {
    @Override
    public Object handle(RpcRequest request) {
        return null;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        System.out.println("Request: "+JSON.toJSONString(request));
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        response.setContent("Success");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        RpcResponse response = new RpcResponse();
        response.setRequestId("E3243223232332");
        response.setContent(new Date());

        ChannelFuture f = ctx.writeAndFlush(response);
        f.addListener(ChannelFutureListener.CLOSE);

        System.out.println("Channel active");
        while (true) {
            ctx.writeAndFlush(response); // (3)
            Thread.sleep(2000);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
