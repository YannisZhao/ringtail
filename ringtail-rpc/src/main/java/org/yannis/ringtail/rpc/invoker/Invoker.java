package org.yannis.ringtail.rpc.invoker;

import org.yannis.ringtail.rpc.RpcRequest;
import org.yannis.ringtail.rpc.RpcResponse;

/**
 * Created by dell on 2016/7/1.
 */
public interface Invoker {
    RpcResponse invoke(RpcRequest request);
}
