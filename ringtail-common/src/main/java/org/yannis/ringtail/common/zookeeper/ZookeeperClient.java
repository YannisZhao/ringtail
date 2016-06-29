package org.yannis.ringtail.common.zookeeper;

import java.util.List;

/**
 * Created by yannis on 6/29/16.
 */
public interface ZookeeperClient {

    void create(String path, boolean ephemeral);

    void delete(String path);

    List<String> getChildren(String path);

    /*List<String> addChildListener(String path, ChildListener listener);

    void removeChildListener(String path, ChildListener listener);

    void addStateListener(StateListener listener);

    void removeStateListener(StateListener listener);*/

    boolean isAlive();

    void close();

    //URL getUrl();
}
