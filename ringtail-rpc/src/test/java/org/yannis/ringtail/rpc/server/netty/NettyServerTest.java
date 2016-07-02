package org.yannis.ringtail.rpc.server.netty;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dell on 2016/7/2.
 */
public class NettyServerTest {
    @Test
    public void doServie() throws Exception {
        int port = 8080;
        new NettyServer(port).doServie();
    }

}