package org.yannis.ringtail.registry.zookeeper;

import org.junit.Test;
import org.yannis.ringtail.registry.Registry;
import org.yannis.ringtail.registry.RegistryFactory;
import org.yannis.ringtail.registry.config.ServiceConfig;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by dell on 2016/7/5.
 */
public class ZookeeperRegistryFactoryTest {
    @Test
    public void getRegistry() throws Exception {
        String[] address = new String[]{"192.168.71.129:2181"};
        ServiceConfig config = new ServiceConfig();
        config.setServices(new HashMap<String, Object>(){
            {
                put("org.yannis.test.TestService",null);
                put("org.yannis.ringtail.rpc.proxy.data.Animal", null);
            }
        });
        RegistryFactory factory = new ZookeeperRegistryFactory(address, config);
        Registry registry = factory.getRegistry();
        registry.register();
    }

}