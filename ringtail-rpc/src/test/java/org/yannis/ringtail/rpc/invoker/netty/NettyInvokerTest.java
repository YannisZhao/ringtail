package org.yannis.ringtail.rpc.invoker.netty;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.yannis.ringtail.rpc.RpcException;
import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.client.netty.NettyClient;
import org.yannis.ringtail.rpc.invoker.Invoker;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by dell on 2016/7/4.
 */
public class NettyInvokerTest {
    @Test
    public void invoke() throws Exception, RpcException {
        NettyClient client = new NettyClient();
        client.connect();
        RpcRequest request = new RpcRequest();
        request.setRequestId("E1000000001");
        request.setInterfaceName("org.yannis.rpc.test.TestService");
        List<String> providerAddress = new ArrayList<>();
        providerAddress.add("192.168.71.129:2181");
        Invoker invoker = new NettyInvoker(providerAddress);
        RpcResponse response = invoker.invoke(request);
        System.out.print("Invoking response: " + JSON.toJSONString(response));
    }

}