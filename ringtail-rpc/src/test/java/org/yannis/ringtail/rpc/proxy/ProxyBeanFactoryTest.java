package org.yannis.ringtail.rpc.proxy;

import org.junit.Test;
import org.yannis.ringtail.rpc.Client;
import org.yannis.ringtail.rpc.RpcException;
import org.yannis.ringtail.rpc.client.netty.NettyClient;
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
        providerAddress.add("192.168.71.129:8080");
        ProxyBeanFactory factory = new ProxyBeanFactory(providerAddress);
        factory.setInterfaces(Animal.class);
        Animal animal = (Animal) factory.newInstance();
        System.out.println(animal.speek(2, "wauwaauooo"));
    }

}