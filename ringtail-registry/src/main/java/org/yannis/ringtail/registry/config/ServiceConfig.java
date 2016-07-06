package org.yannis.ringtail.registry.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/7/5.
 */
public class ServiceConfig {

    private String host;

    private int port;

    private Map<String, Object> services;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Map<String, Object> getServices() {
        return services;
    }

    public void setServices(Map<String, Object> services) {
        this.services = services;
    }
}
