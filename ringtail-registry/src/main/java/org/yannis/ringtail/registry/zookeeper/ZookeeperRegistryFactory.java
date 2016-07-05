package org.yannis.ringtail.registry.zookeeper;

import org.yannis.ringtail.registry.Registry;
import org.yannis.ringtail.registry.config.ServiceConfig;
import org.yannis.ringtail.registry.core.AbstractRegistryFactory;

/**
 * Created by dell on 2016/7/5.
 */
public class ZookeeperRegistryFactory extends AbstractRegistryFactory {

    private ZookeeperRegistry registry;

    public ZookeeperRegistryFactory(String[] registryAddress, ServiceConfig serviceConfig) {
        super(registryAddress, serviceConfig);
    }

    @Override
    public Registry getRegistry() {
        if(registry == null){
            synchronized (ZookeeperRegistry.class) {
                if(registry == null) {
                    registry = new ZookeeperRegistry(registryAddress, serviceConfig);
                }
            }
        }
        return registry;
    }
}
