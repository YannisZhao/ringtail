package org.yannis.ringtail.rpc.handler.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.handler.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 2016/7/1.
 */
public class NettyHandler extends SimpleChannelInboundHandler<RpcRequest> implements Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyHandler.class);

    private Map<String, Object> services = new ConcurrentHashMap<String, Object>(){
        {
            try {
                put("org.yannis.ringtail.rpc.proxy.data.Animal", Class.forName("org.yannis.ringtail.rpc.proxy.data.Dog").newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        LOGGER.info("request arrive");
        LOGGER.info("Request data: {}", JSON.toJSONString(request));
        RpcResponse response = new RpcResponse();
        try {
            response = handle(request);
        } catch (Throwable t) {
            response.setError(t);
        }
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public RpcResponse handle(RpcRequest request) {
        String className = request.getInterfaceName();

        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        RpcResponse response = null;
        try {
            Class<?> service = Class.forName(className);
            Object serviceBean = services.get(className);
            Method method = service.getMethod(methodName, parameterTypes);
            Object obj = method.invoke(serviceBean, parameters);
            response = RpcResponse.SUCCESS;
            response.setRequestId(request.getRequestId());
            response.setData(obj);
        } catch (ClassNotFoundException e) {
            response = RpcResponse.SERVICE_NOT_FOUND;
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            response = RpcResponse.METHOD_NOT_FOUND;
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            //If access private method, then return method not found
            response = RpcResponse.METHOD_NOT_FOUND;
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            response = RpcResponse.SERVER_ERROR;
            e.printStackTrace();
        }

        LOGGER.info("Response from server: {}", JSON.toJSONString(response));
        return response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("server caught exception", cause);
        ctx.close();
    }

}
