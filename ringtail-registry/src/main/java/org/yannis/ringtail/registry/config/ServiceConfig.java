package org.yannis.ringtail.registry.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/7/5.
 */
public class ServiceConfig {
    private Map<String, Object> services;

    public Map<String, Object> getServices() {
        return services;
    }

    public void setServices(Map<String, Object> services) {
        this.services = services;
    }
}
