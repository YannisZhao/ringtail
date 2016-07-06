package org.yannis.ringtail.rpc.proxy;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.rpc.Client;
import org.yannis.ringtail.rpc.RpcException;
import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.client.netty.NettyClient;
import org.yannis.ringtail.rpc.invoker.Invoker;
import org.yannis.ringtail.rpc.invoker.netty.NettyInvoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dell on 2016/7/1.
 */
public class ProxyBeanFactory implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyBeanFactory.class);

    private Invoker invoker;

    private Class<?> interfaces;

    public ProxyBeanFactory(List<String> providerAddress) {
        invoker = new NettyInvoker(providerAddress);
    }

    public Object newInstance(){
        return Proxy.newProxyInstance(ProxyBeanFactory.class.getClassLoader(), new Class[]{interfaces}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(">>>Remoting process invoking starting...");
        return doInvoke(method, args);
    }

    private Object doInvoke(Method method, Object[] args) throws Throwable {
        //Invoker invoker = new NettyInvoker(client);
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setInterfaceName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        try{
            RpcResponse response = invoker.invoke(request);
            System.out.println(">>>Remoting process invoked successfully...");
            System.out.println(">>>Response..."+ JSON.toJSONString(response));
            if(response != null) {
                if(response.isSuccess()) {
                    return response.getData();
                }
            }
        }catch (RpcException e){
            throw new Throwable(e);
        }
        return null;
    }

    public void setInterfaces(Class<?> interfaces) {
        this.interfaces = interfaces;
    }
}
