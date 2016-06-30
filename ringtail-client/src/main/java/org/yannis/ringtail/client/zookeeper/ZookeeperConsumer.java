package org.yannis.ringtail.client.zookeeper;

import org.yannis.ringtail.client.AbstractConsumer;
import org.yannis.ringtail.common.zookeeper.ZookeeperClient;
import org.yannis.ringtail.common.zookeeper.curator.CuratorZookeeperClient;

import java.util.List;

/**
 * Created by dell on 2016/6/30.
 */
public class ZookeeperConsumer extends AbstractConsumer {

    private ZookeeperClient client;

    public ZookeeperConsumer(){
        client = new CuratorZookeeperClient(new String[]{"192.168.71.129:2181"});
    }

    @Override
    protected void doSubscribe() throws Exception {
        List<String> children = client.getChildren("/ringtail");
        for (String node : children) {
            System.out.println("Found service: "+node);
            System.out.println("Data: "+client.getData("/ringtail/"+node));
        }
    }

    @Override
    protected void doUnsubscribe() {

    }

}
