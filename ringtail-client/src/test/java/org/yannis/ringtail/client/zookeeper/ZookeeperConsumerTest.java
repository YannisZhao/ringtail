package org.yannis.ringtail.client.zookeeper;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dell on 2016/6/30.
 */
public class ZookeeperConsumerTest {
    @Test
    public void doSubscribe() throws Exception {
        ZookeeperConsumer consumer = new ZookeeperConsumer();
        consumer.doSubscribe();
    }

    @Test
    public void doUnsubscribe() throws Exception {

    }

}