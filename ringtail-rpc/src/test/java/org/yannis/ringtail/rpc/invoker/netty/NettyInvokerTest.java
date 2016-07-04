package org.yannis.ringtail.rpc.invoker.netty;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.client.netty.NettyClient;
import org.yannis.ringtail.rpc.invoker.Invoker;

import static org.junit.Assert.*;

/**
 * Created by dell on 2016/7/4.
 */
public class NettyInvokerTest {
    @Test
    public void invoke() throws Exception {
        NettyClient client = new NettyClient();
        client.connect();
        RpcRequest request = new RpcRequest();
        request.setRequestId("E1000000001");
        request.setClassName("org.yannis.rpc.test.TestService");
        Invoker invoker = new NettyInvoker(client);
        RpcResponse response = invoker.invoke(request);
        System.out.print("Invoking response: " + JSON.toJSONString(response));
    }

}