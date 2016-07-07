package org.yannis.ringtail.registry.zookeeper;

import org.yannis.ringtail.registry.config.ServiceConfig;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by dell on 2016/6/30.
 */
public class ZookeeperRegistryTest {
    @org.junit.Test
    public void doRegister() throws Exception {
        ServiceConfig config = new ServiceConfig();
        config.setHost("127.0.0.1");
        config.setPort(8080);
        Map<String, Object> services = new HashMap<>();
        services.put("test_org.yannis.ringtail.client.spring.data.Animal_1.0.0", null);
        config.setServices(services);
        ZookeeperRegistry registry = new ZookeeperRegistry(new String[]{"192.168.71.129:2181"}, config);
        registry.register();
        while (true);
    }

    @org.junit.Test
    public void doUnregister() throws Exception {

    }

}