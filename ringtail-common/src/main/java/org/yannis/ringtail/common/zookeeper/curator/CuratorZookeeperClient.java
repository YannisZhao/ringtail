package org.yannis.ringtail.common.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.yannis.ringtail.common.zookeeper.ZookeeperClient;
import org.yannis.ringtail.common.zookeeper.enums.NodeType;

import java.util.List;

/**
 * Created by yannis on 6/29/16.
 */
public class CuratorZookeeperClient implements ZookeeperClient {

    private CuratorFramework client;

    public CuratorZookeeperClient(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        String url = "192.168.71.129:2181";
        client = CuratorFrameworkFactory.newClient(url, retryPolicy);
        /*client = builder.connectString("192.168.11.56:2180")
                .sessionTimeoutMs(30000)
                .connectionTimeoutMs(30000)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .namespace(namespace)
                .defaultData(null)
                .build();*/
        System.out.println("Try to connect to zookeeper server " + url);
        client.start();
    }

    @Override
    public void create(String path, NodeType nodeType) throws Exception {
        System.out.println("Try to create node with path: " + path + ", node type: "+ nodeType);
        switch (nodeType){
            case PERSISTENT:
                createPersist(path, false);
                break;
            case PERSISTENT_SEQUENTIAL:
                createPersist(path, true);
                break;
            case EPHEMERAL:
                createEphemeral(path, false);
                break;
            case EPHEMERAL_SEQUENTIAL:
                createEphemeral(path, true);
                break;
        }
    }

    @Override
    public void delete(String path) {

    }

    @Override
    public void setData(String path, String data) {

    }

    @Override
    public void getData(String path) {

    }

    @Override
    public boolean isExist(String path) {
        return false;
    }

    @Override
    public List<String> getChildren(String path) {
        return null;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void close() {

    }

    private void createPersist(String path, boolean sequencial) throws Exception {
        if(sequencial) {
            client.create().withMode(CreateMode.PERSISTENT).forPath(path);
        }else{
            client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path);
        }
    }

    private void createEphemeral(String path, boolean sequencial) throws Exception {
        if(sequencial) {
            client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
        }else{
            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path);
        }
    }

}
