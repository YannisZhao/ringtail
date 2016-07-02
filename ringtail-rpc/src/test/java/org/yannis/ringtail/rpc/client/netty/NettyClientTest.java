package org.yannis.ringtail.rpc.client.netty;

import org.junit.Test;
import org.junit.runner.Request;
import org.yannis.ringtail.rpc.RpcRequest;

import static org.junit.Assert.*;

/**
 * Created by dell on 2016/7/2.
 */
public class NettyClientTest {
    @Test
    public void connect() throws Exception {
        NettyClient client = new NettyClient();
        client.connect();
        RpcRequest request = new RpcRequest();
        request.setRequestId("1000000001");
        request.setServiceName("org.yannis.rpc.test.TestService");
        client.send(request);
    }

    @Test
    public void send() throws Exception {

    }

}