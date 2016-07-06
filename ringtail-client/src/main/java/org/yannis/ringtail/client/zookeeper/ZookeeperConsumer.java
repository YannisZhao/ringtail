package org.yannis.ringtail.client.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.client.AbstractConsumer;
import org.yannis.ringtail.client.config.ReferenceConfig;
import org.yannis.ringtail.common.zookeeper.ZookeeperClient;
import org.yannis.ringtail.common.zookeeper.curator.CuratorZookeeperClient;
import org.yannis.ringtail.common.zookeeper.listeners.NodeListener;

import java.util.List;

/**
 * Created by dell on 2016/6/30.
 */
public class ZookeeperConsumer extends AbstractConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperConsumer.class);

    private ZookeeperClient client;

    public ZookeeperConsumer(){
        client = new CuratorZookeeperClient("192.168.71.129:2181");
    }

    @Override
    protected void doSubscribe(ReferenceConfig config) throws Exception {
        List<String> children = client.getChildren("/ringtail");
        NodeListener listener = new NodeListener() {
            @Override
            public void onCreateed(ZookeeperClient client) {
                LOGGER.info("node created.");
            }

            @Override
            public void onChildrenChanged(ZookeeperClient client) {

            }

            @Override
            public void onChanged(ZookeeperClient client) {
                LOGGER.info("node data changed.");
            }

            @Override
            public void onDeleted(ZookeeperClient client) {
                LOGGER.info("node deleted.");
            }
        };
        for (String node : children) {
            System.out.println("Found service: "+node);
            List<String> providers = client.getChildren("/ringtail/" + node+"/providers");
            for(String provider : providers){
                System.out.println("Service provider address: " + provider);
            }
        }
    }

    @Override
    protected void doUnsubscribe(ReferenceConfig config) {

    }

}
