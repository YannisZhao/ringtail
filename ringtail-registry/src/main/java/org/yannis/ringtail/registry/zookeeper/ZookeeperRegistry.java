package org.yannis.ringtail.registry.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.common.zookeeper.ZookeeperClient;
import org.yannis.ringtail.common.zookeeper.curator.CuratorZookeeperClient;
import org.yannis.ringtail.common.zookeeper.enums.NodeType;
import org.yannis.ringtail.registry.config.ServiceConfig;
import org.yannis.ringtail.registry.core.AbstractRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/30.
 */
public class ZookeeperRegistry extends AbstractRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperRegistry.class);

    private static final String BATHPATH = "/ringtail";

    private List<ZookeeperClient> clients = new ArrayList<>();

    public ZookeeperRegistry(String[] addressArray, ServiceConfig serviceConfig){
        super(addressArray, serviceConfig);
        for(String registryAddress : addressArray) {
            ZookeeperClient client = new CuratorZookeeperClient(registryAddress);
            clients.add(client);
        }
    }

    @Override
    protected void doRegister() {
        try {
            if (serviceConfig != null && serviceConfig.getServices() != null){
                for(ZookeeperClient client : clients) {
                    for (String serviceURI : serviceConfig.getServices().keySet()) {
                        _doRegister(client, serviceURI);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void _doRegister(ZookeeperClient client, String serviceURI) throws Exception {
        String path = BATHPATH + "/" + serviceURI;
        client.create(path, NodeType.PERSISTENT);
        String providers = path + "/providers";
        if(!client.isExist(providers)){
            client.create(providers, NodeType.PERSISTENT);
        }
        // record provider information
        String pNode = providers + "/"+serviceConfig.getHost() + ":" + serviceConfig.getPort();
        if(client.isExist(pNode)){
            LOGGER.info("Service {} already be registered to {}, register skipped.");
            return;
        }
        // create provider node as ephemeral
        client.create(pNode, NodeType.EPHEMERAL);
        LOGGER.info("Service {} register to the registry {} successfully", serviceURI, registryAddress);
    }

    @Override
    protected void doUnregister() {

    }
}
