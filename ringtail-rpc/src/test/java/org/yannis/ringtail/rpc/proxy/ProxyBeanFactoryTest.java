package org.yannis.ringtail.rpc.proxy;

import org.junit.Test;

/**
 * Created by dell on 2016/7/1.
 */
public class ProxyBeanFactoryTest {
    @Test
    public void newInstance() throws Exception {
        ProxyBeanFactory factory = new ProxyBeanFactory();
        factory.setInterfaces(Animal.class);
        Animal animal = (Animal) factory.newInstance();
        animal.run();
    }

    public interface Animal{
        void run();
    }

    public class Dog implements Animal{

        @Override
        public void run() {
            System.out.print("Dog run fast");
        }
    }
}