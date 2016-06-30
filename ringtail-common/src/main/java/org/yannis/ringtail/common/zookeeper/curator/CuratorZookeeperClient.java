package org.yannis.ringtail.common.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.common.zookeeper.AbstractZookeeperClient;
import org.yannis.ringtail.common.zookeeper.ZookeeperClient;
import org.yannis.ringtail.common.zookeeper.enums.NodeType;
import org.yannis.ringtail.common.zookeeper.listeners.ConnectionListener;

import java.util.List;

/**
 * Created by yannis on 6/29/16.
 */
public class CuratorZookeeperClient extends AbstractZookeeperClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CuratorZookeeperClient.class);

    private CuratorFramework client;

    public CuratorZookeeperClient(String[] urls) {
        // RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //String url = "192.168.71.129:2181";
        //client = CuratorFrameworkFactory.newClient(url, retryPolicy);
        super(urls);
        connect(new ConnectionListener() {
            @Override
            public void onConnected(ZookeeperClient client) {
                LOGGER.info("onConnected");
            }

            @Override
            public void onReconnected(ZookeeperClient client) {
                LOGGER.info("onReconnected");
            }

            @Override
            public void onDisconnected(ZookeeperClient client) {
                LOGGER.info("onDisconnected");
            }
        });

        try {
            if (!isExist("/ringtail")) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("Try to create ringtail base node: /ringtail");
                }
                create("/ringtail", NodeType.PERSISTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String path) throws Exception {
        client.delete().forPath(path);
    }

    @Override
    public void setData(String path, String data) throws Exception {
        client.setData().forPath(path, data.getBytes());
    }

    @Override
    public String getData(String path) throws Exception {
        return new String(client.getData().forPath(path));
    }

    @Override
    public boolean isExist(String path) throws Exception {
        return client.checkExists().forPath(path) != null ? true : false;
    }

    @Override
    public List<String> getChildren(String path) throws Exception {
        return client.getChildren().forPath(path);
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void close() {
        client.close();
    }

    protected void createPersist(String path, boolean sequencial) throws Exception {
        if (sequencial) {
            client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path);
        } else {
            client.create().withMode(CreateMode.PERSISTENT).forPath(path);
        }
    }

    protected void createEphemeral(String path, boolean sequencial) throws Exception {
        if (sequencial) {
            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path);
        } else {
            client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
        }
    }

    @Override
    protected void connect() {
        client = CuratorFrameworkFactory.builder()
                .connectString(urls[0])
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
                .connectionTimeoutMs(5000)
                .build();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Try to connect to zookeeper server " + urls[0]);
        }
        client.start();
    }

    @Override
    protected void connect(final ConnectionListener listener) {
        client = CuratorFrameworkFactory.builder()
                .connectString(urls[0])
                //.namespace( "/ringtail" )
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
                .connectionTimeoutMs(5000)
                .build();
        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState state) {
                LOGGER.info("zookeeper state changed to " + state);
                onConnectionStateChanged(state, listener);
            }
        });
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Try to connect to zookeeper server " + urls[0]);
        }
        client.start();
    }

    private void onConnectionStateChanged(ConnectionState state, ConnectionListener listener) {
        switch (state) {
            case CONNECTED:
                LOGGER.info("Connected to server.");
                listener.onConnected(this);
                break;
            case SUSPENDED:
                LOGGER.info("Connection suspended.");
                break;
            case RECONNECTED:
                LOGGER.info("Reconnected to server.");
                listener.onReconnected(this);
                break;
            case LOST:
                LOGGER.info("Connection lost.");
                listener.onDisconnected(this);
                break;
            case READ_ONLY:
                LOGGER.info("Connected to server.[READ-ONLY]");
                break;
        }
    }
}
