package org.yannis.ringtail.rpc.proxy;

import org.junit.Test;
import org.yannis.ringtail.rpc.RpcException;
import org.yannis.ringtail.rpc.proxy.data.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/7/1.
 */
public class ProxyBeanFactoryTest {
    @Test
    public void newInstance() throws RpcException {
        List<String> providerAddress = new ArrayList<>();
        providerAddress.add("127.0.0.1:8080");
        JdkProxyBeanFactory factory = new JdkProxyBeanFactory(providerAddress);
        factory.setInterfaces(Animal.class);
        Animal animal = (Animal) factory.newInstance();
        System.out.println(animal.speek(2, "wauwaauooo"));
    }

}