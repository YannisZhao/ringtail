package org.yannis.ringtail.rpc.proxy;

import com.alibaba.fastjson.JSON;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.rpc.RpcException;
import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.invoker.Invoker;
import org.yannis.ringtail.rpc.invoker.netty.NettyInvoker;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 * Created by dell on 2016/7/7.
 */
public class CglibProxyBeanFactory implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdkProxyBeanFactory.class);

    private Invoker invoker;

    private Class<?> interfaces;

    public CglibProxyBeanFactory(List<String> providerAddress) {
        invoker = new NettyInvoker(providerAddress);
    }

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(){
        enhancer.setSuperclass(interfaces);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        //if()
        System.out.println("---proxy method invokinng---");
        System.out.println(String.format("proxy obj class: %s---method: %s",obj.getClass(), method));
        //Object result = proxy.invokeSuper(obj, args);
        Object result = doInvoke(method, args);
        System.out.println("---proxy method invoked---");
        return result;
    }

    private Object doInvoke(Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setInterfaceName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        try{
            System.out.println(">>>Starting remote invoking...");
            System.out.println(">>>Request..."+ JSON.toJSONString(request));
            RpcResponse response = invoker.invoke(request);
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