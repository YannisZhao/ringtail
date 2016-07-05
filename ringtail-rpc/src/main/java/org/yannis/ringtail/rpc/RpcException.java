package org.yannis.ringtail.rpc;

/**
 * Created by yannis on 6/29/16.
 */
public class RpcException extends Throwable {
    public RpcException(String s, Throwable e) {
        super(s,e);
    }

    public RpcException(String s) {
        super(s);
    }
}
