package org.yannis.ringtail.common.zookeeper;

import org.yannis.ringtail.common.zookeeper.enums.NodeType;

import java.util.List;

/**
 * Created by yannis on 6/29/16.
 */
public interface ZookeeperClient {

    void create(String path, NodeType nodeType) throws Exception;

    void delete(String path) throws Exception;

    void setData(String path, String data) throws Exception;

    String getData(String path) throws Exception;

    boolean isExist(String path) throws Exception;

    List<String> getChildren(String path) throws Exception;

    /*List<String> addChildListener(String path, ChildListener listener);

    void removeChildListener(String path, ChildListener listener);

    void addStateListener(StateListener listener);

    void removeStateListener(StateListener listener);*/

    boolean isAlive();

    void close();

    //URL getUrl();
}
