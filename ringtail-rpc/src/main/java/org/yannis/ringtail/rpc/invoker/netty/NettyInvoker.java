package org.yannis.ringtail.rpc.invoker.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.rpc.Client;
import org.yannis.ringtail.rpc.RpcException;
import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.client.netty.NettyClient;
import org.yannis.ringtail.rpc.invoker.Invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/1.
 */
public class NettyInvoker implements Invoker {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyInvoker.class);

    private List<Client> clients;

    public NettyInvoker(List<String> providerAddresses) {
        if(providerAddresses == null || 0 == providerAddresses.size()){
            throw new IllegalArgumentException("provider server address cannot be empty");
        }
        clients = new ArrayList<>();
        for (String address : providerAddresses) {
            String[] addr = address.split(":");
            Client client = new NettyClient(addr[0], Integer.parseInt(addr[1]));
            client.connect();
            clients.add(client);
        }
    }

    @Override
    public RpcResponse invoke(RpcRequest request) throws RpcException {
        System.out.println("Service invoked.");
        // Load balance
        Client client = clients.get(0);
        RpcResponse response = client.send(request);
        if(!response.isSuccess()){
            LOGGER.error(response.getMessage());
            throw new RpcException(response.getMessage());
        }
        return response;
    }
}
