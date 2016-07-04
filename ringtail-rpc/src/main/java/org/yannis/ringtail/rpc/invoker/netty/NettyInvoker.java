package org.yannis.ringtail.rpc.invoker.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.rpc.Client;
import org.yannis.ringtail.rpc.RpcException;
import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.client.netty.NettyClient;
import org.yannis.ringtail.rpc.invoker.Invoker;

/**
 * Created by dell on 2016/7/1.
 */
public class NettyInvoker implements Invoker {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyInvoker.class);

    private Client  client;

    public NettyInvoker(Client client){
        this.client = client;
    }

    @Override
    public RpcResponse invoke(RpcRequest request) throws RpcException {
        System.out.println("Service invoked.");
        return client.send(request);
    }
}