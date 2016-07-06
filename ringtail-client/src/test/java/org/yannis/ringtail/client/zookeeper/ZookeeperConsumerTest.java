package org.yannis.ringtail.client.zookeeper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dell on 2016/6/30.
 */
public class ZookeeperConsumerTest {

    private ZookeeperConsumer consumer;

    @Before
    public void init(){
        consumer = new ZookeeperConsumer(new String[]{"182.168.71.129:2181"});
    }

    @After
    public void destroy(){

    }

    @Test
    public void doSubscribe() throws Exception {
        consumer.doSubscribe(null);
        while (true);
    }

    @Test
    public void doUnsubscribe() throws Exception {

    }

}