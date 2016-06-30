package org.yannis.ringtail.common.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.common.zookeeper.enums.NodeType;
import org.yannis.ringtail.common.zookeeper.listeners.ConnectionListener;

/**
 * Created by dell on 2016/6/29.
 */
public abstract class AbstractZookeeperClient implements ZookeeperClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractZookeeperClient.class);

    protected String[] urls;

    private int timeout = 5000;

    //private final String root;


    public AbstractZookeeperClient(String[] urls){
        this.urls = urls;
    }

    @Override
    public void create(String path, NodeType nodeType) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Try to create node with path: {}, node type: {}", path, nodeType);
        }
        switch (nodeType) {
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

    protected abstract void connect();

    protected abstract void connect(ConnectionListener listener);

    protected abstract void createPersist(String path, boolean sequencial) throws Exception;

    protected abstract void createEphemeral(String path, boolean sequencial) throws Exception;

}
