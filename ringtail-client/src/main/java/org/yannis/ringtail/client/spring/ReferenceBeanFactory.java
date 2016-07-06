package org.yannis.ringtail.client.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.client.config.ReferenceConfig;
import org.yannis.ringtail.client.zookeeper.ZookeeperConsumer;
import org.yannis.ringtail.rpc.Client;
import org.yannis.ringtail.rpc.client.netty.NettyClient;
import org.yannis.ringtail.rpc.proxy.ProxyBeanFactory;

/**
 * Created by dell on 2016/7/6.
 */
public class ReferenceBeanFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceBeanFactory.class);

    private String appName;
    private String interfaceName;
    private String version;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void init(){
        ZookeeperConsumer consumer = new ZookeeperConsumer();
        ReferenceConfig config = new ReferenceConfig(appName, interfaceName, version);
        try {
            consumer.subscribe(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object createBean(){
        Client client = new NettyClient();
        client.connect();
        ProxyBeanFactory factory = new ProxyBeanFactory(client);
        try {
            Class clazz = Class.forName(interfaceName);
            factory.setInterfaces(clazz);
        } catch (ClassNotFoundException e) {
            LOGGER.info("No class found that matched the specified interface {} configured in the xml file", interfaceName);
            e.printStackTrace();
        }

        return factory.newInstance();
    }
}
