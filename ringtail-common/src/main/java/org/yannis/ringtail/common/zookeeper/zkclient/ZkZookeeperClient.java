package org.yannis.ringtail.common.zookeeper.zkclient;

import org.yannis.ringtail.common.zookeeper.AbstractZookeeperClient;
import org.yannis.ringtail.common.zookeeper.enums.NodeType;
import org.yannis.ringtail.common.zookeeper.listeners.ConnectionListener;
import org.yannis.ringtail.common.zookeeper.listeners.NodeListener;

import java.util.List;

/**
 * Created by yannis on 6/29/16.
 */
public class ZkZookeeperClient extends AbstractZookeeperClient {
    public ZkZookeeperClient(String registryAddress) {
        super(registryAddress);
    }

    @Override
    public void create(String path, NodeType nodeType) throws Exception {

    }

    @Override
    public void delete(String path) throws Exception {

    }

    @Override
    public void setData(String path, String data) throws Exception {

    }

    @Override
    public String getData(String path) throws Exception {
        return null;
    }

    @Override
    public String getData(String path, NodeListener listener) throws Exception {
        return null;
    }

    @Override
    public boolean isExist(String path) throws Exception {
        return false;
    }

    @Override
    public List<String> getChildren(String path) throws Exception {
        return null;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    protected void connect() {

    }

    @Override
    protected void connect(ConnectionListener listener) {

    }

    @Override
    protected void createPersist(String path, boolean sequencial) throws Exception {

    }

    @Override
    protected void createEphemeral(String path, boolean sequencial) throws Exception {

    }
}
