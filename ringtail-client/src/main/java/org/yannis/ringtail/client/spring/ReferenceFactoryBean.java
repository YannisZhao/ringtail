package org.yannis.ringtail.client.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.yannis.ringtail.client.config.ReferenceConfig;
import org.yannis.ringtail.client.zookeeper.ZookeeperConsumer;
import org.yannis.ringtail.rpc.proxy.ProxyBeanFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/6.
 */
public class ReferenceFactoryBean implements FactoryBean, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceFactoryBean.class);

    private String appName;
    private String interfaceName;
    private String version;

    private ProxyBeanFactory factory;

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

    public ReferenceFactoryBean(){

    }

    private String toServicePath() {
        return appName + "_" + interfaceName + "_" + version;
    }

    @Override
    public Object getObject() throws Exception {
        try {
            Class clazz = Class.forName(interfaceName);
            factory.setInterfaces(clazz);
        } catch (ClassNotFoundException e) {
            LOGGER.info("No class found that matched the specified interface {} configured in the xml file", interfaceName);
            e.printStackTrace();
        }

        return factory.newInstance();
    }

    @Override
    public Class<?> getObjectType() {
        Class clazz = null;
        try {
            clazz = Class.forName(interfaceName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String[] registryAddresses = {"192.168.71.129:2181"};
        ZookeeperConsumer consumer = new ZookeeperConsumer(registryAddresses);
        ReferenceConfig config = new ReferenceConfig(appName, interfaceName, version);
        try {
            consumer.subscribe(config);
            List<String> providerAddress = null;

            Map<String, List<String>> subscribedServices = consumer.getSubscribedServices();
            for(String serviceKey : subscribedServices.keySet()){
                if(serviceKey.equals(toServicePath())){
                    providerAddress = subscribedServices.get(serviceKey);
                }
            }
            factory = new ProxyBeanFactory(providerAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
