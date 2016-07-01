package org.yannis.ringtail.rpc.handler;

import org.yannis.ringtail.rpc.RpcRequest;

/**
 * Created by dell on 2016/7/1.
 */
public interface Handler {
    Object handle(RpcRequest request);
}
