package org.yannis.ringtail.common.zookeeper.listeners;

import org.yannis.ringtail.common.zookeeper.ZookeeperClient;

/**
 * Created by dell on 2016/6/30.
 */
public interface NodeListener {

    void onCreateed(ZookeeperClient client);
    void onChildrenChanged(ZookeeperClient client);
    void onChanged(ZookeeperClient client);
    void onDeleted(ZookeeperClient client);
}
