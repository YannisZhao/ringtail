package org.yannis.ringtail.rpc;

/**
 * Created by dell on 2016/7/2.
 */
public interface Client {
    void connect();
    void send(RpcRequest request);
}
