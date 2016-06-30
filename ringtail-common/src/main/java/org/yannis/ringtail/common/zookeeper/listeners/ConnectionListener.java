package org.yannis.ringtail.common.zookeeper.listeners;

import org.yannis.ringtail.common.zookeeper.ZookeeperClient;

/**
 * Created by dell on 2016/6/30.
 */
public interface ConnectionListener {
    void onConnected(ZookeeperClient client);
    void onReconnected(ZookeeperClient client);
    void onDisconnected(ZookeeperClient client);
}
