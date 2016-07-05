package org.yannis.ringtail.rpc.proxy;

import org.junit.Test;
import org.yannis.ringtail.rpc.Client;
import org.yannis.ringtail.rpc.RpcException;
import org.yannis.ringtail.rpc.client.netty.NettyClient;
import org.yannis.ringtail.rpc.proxy.data.Animal;

/**
 * Created by dell on 2016/7/1.
 */
public class ProxyBeanFactoryTest {
    @Test
    public void newInstance() throws RpcException {
        Client client = new NettyClient();
        client.connect();
        ProxyBeanFactory factory = new ProxyBeanFactory(client);
        factory.setInterfaces(Animal.class);
        Animal animal = (Animal) factory.newInstance();
        System.out.println(animal.speek(2, "wauwaauooo"));
    }

}