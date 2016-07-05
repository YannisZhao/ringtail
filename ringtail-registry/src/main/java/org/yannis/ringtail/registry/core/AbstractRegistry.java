package org.yannis.ringtail.registry.core;

import org.yannis.ringtail.registry.Registry;
import org.yannis.ringtail.registry.config.ServiceConfig;

/**
 * Created by dell on 2016/6/30.
 */
public abstract class AbstractRegistry implements Registry {

    /**
     * Registry center bind address
     */
    protected String[] registryAddress;

    protected ServiceConfig serviceConfig;

    public AbstractRegistry(String[] registryAddress, ServiceConfig serviceConfig){
        this.registryAddress = registryAddress;
        this.serviceConfig = serviceConfig;
    }

    @Override
    public void register() {
        doRegister();
    }

    @Override
    public void unregister() {
        doUnregister();
    }

    protected abstract void doRegister();
    protected abstract void doUnregister();
}
