package org.yannis.ringtail.registry.core;

import org.yannis.ringtail.registry.Registry;
import org.yannis.ringtail.registry.RegistryFactory;
import org.yannis.ringtail.registry.config.ServiceConfig;

/**
 * Created by dell on 2016/7/5.
 */
public abstract class AbstractRegistryFactory implements RegistryFactory {

    protected String[] registryAddress;

    protected ServiceConfig serviceConfig;

    public AbstractRegistryFactory(String[] registryAddress, ServiceConfig serviceConfig) {
        this.registryAddress = registryAddress;
        this.serviceConfig = serviceConfig;
    }

    @Override
    public abstract Registry getRegistry();
}
