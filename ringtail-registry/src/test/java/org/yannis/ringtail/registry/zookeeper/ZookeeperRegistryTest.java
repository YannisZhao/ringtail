package org.yannis.ringtail.registry.zookeeper;

import static org.junit.Assert.*;

/**
 * Created by dell on 2016/6/30.
 */
public class ZookeeperRegistryTest {
    @org.junit.Test
    public void doRegister() throws Exception {
        ZookeeperRegistry registry = new ZookeeperRegistry();
        registry.register();
    }

    @org.junit.Test
    public void doUnregister() throws Exception {

    }

}