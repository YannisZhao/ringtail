package org.yannis.ringtail.rpc.invoker.netty;

import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;
import org.yannis.ringtail.rpc.invoker.Invoker;

/**
 * Created by dell on 2016/7/1.
 */
public class NettyInvoker implements Invoker {
    @Override
    public RpcResponse invoke(RpcRequest request) {
        System.out.println("Service invoked.");
        return null;
    }
}
