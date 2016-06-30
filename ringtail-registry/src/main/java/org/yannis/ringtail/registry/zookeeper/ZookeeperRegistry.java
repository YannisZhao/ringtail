package org.yannis.ringtail.registry.zookeeper;

import org.yannis.ringtail.common.zookeeper.ZookeeperClient;
import org.yannis.ringtail.common.zookeeper.curator.CuratorZookeeperClient;
import org.yannis.ringtail.common.zookeeper.enums.NodeType;
import org.yannis.ringtail.registry.AbstractRegistry;

/**
 * Created by dell on 2016/6/30.
 */
public class ZookeeperRegistry extends AbstractRegistry {

    private ZookeeperClient client;

    public ZookeeperRegistry(){
        client = new CuratorZookeeperClient(new String[]{"192.168.71.129:2181"});
    }

    @Override
    protected void doRegister() {
        String basePath = "/ringtail/service:org.yannis.test";
        try {
            client.create(basePath+".DemoService1-1.0.0", NodeType.PERSISTENT);
            client.create(basePath+".DemoService1-1.0.1", NodeType.PERSISTENT_SEQUENTIAL);
            client.create(basePath+".DemoService2-1.0.0", NodeType.EPHEMERAL);
            client.create(basePath+".DemoService2-1.0.1", NodeType.EPHEMERAL_SEQUENTIAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doUnregister() {

    }
}
