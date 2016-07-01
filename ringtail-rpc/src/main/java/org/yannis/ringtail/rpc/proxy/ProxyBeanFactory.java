package org.yannis.ringtail.rpc.proxy;

import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.invoker.Invoker;
import org.yannis.ringtail.rpc.invoker.netty.NettyInvoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by dell on 2016/7/1.
 */
public class ProxyBeanFactory implements InvocationHandler {

    private Class<?> interfaces;

    public Object newInstance(){
        return Proxy.newProxyInstance(ProxyBeanFactory.class.getClassLoader(), new Class[]{interfaces}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(">>>Remoting process invoking starting...");
        Invoker invoker = new NettyInvoker();
        invoker.invoke(new RpcRequest());
        System.out.print(">>>Remoting process invoked successfully...");
        return null;
    }

    public void setInterfaces(Class<?> interfaces) {
        this.interfaces = interfaces;
    }
}
