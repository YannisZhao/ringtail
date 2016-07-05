package org.yannis.ringtail.registry.zookeeper;

import org.yannis.ringtail.registry.config.ServiceConfig;

import static org.junit.Assert.*;

/**
 * Created by dell on 2016/6/30.
 */
public class ZookeeperRegistryTest {
    @org.junit.Test
    public void doRegister() throws Exception {
        ZookeeperRegistry registry = new ZookeeperRegistry(new String[]{"127.0.0.1"}, new ServiceConfig());
        registry.register();
    }

    @org.junit.Test
    public void doUnregister() throws Exception {

    }

}