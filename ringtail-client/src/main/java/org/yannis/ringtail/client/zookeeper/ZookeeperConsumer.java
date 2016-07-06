package org.yannis.ringtail.client.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yannis.ringtail.client.AbstractConsumer;
import org.yannis.ringtail.client.config.ReferenceConfig;
import org.yannis.ringtail.common.zookeeper.ZookeeperClient;
import org.yannis.ringtail.common.zookeeper.curator.CuratorZookeeperClient;
import org.yannis.ringtail.common.zookeeper.listeners.NodeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 2016/6/30.
 */
public class ZookeeperConsumer extends AbstractConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperConsumer.class);

    private List<ZookeeperClient> clients = new ArrayList<>();

    private Map<String, List<String>> subscribedServices = new ConcurrentHashMap<>();

    public ZookeeperConsumer(String[] registryAddresses){
        for(String registryAddress : registryAddresses) {
            ZookeeperClient client = new CuratorZookeeperClient(registryAddress);
            clients.add(client);
        }
    }

    @Override
    protected void doSubscribe(ReferenceConfig config) throws Exception {
        for(ZookeeperClient client : clients) {
            _doSubscribe(client, config);
        }
    }

    private void _doSubscribe(ZookeeperClient client, ReferenceConfig config) throws Exception {
        List<String> children = client.getChildren("/ringtail");
        NodeListener listener = new NodeListener() {
            @Override
            public void onCreateed(ZookeeperClient client) {
                LOGGER.info("node created.");
            }

            @Override
            public void onChildrenChanged(ZookeeperClient client) {

            }

            @Override
            public void onChanged(ZookeeperClient client) {
                LOGGER.info("node data changed.");
            }

            @Override
            public void onDeleted(ZookeeperClient client) {
                LOGGER.info("node deleted.");
            }
        };
        for (String node : children) {
            System.out.println("Found service: " + node);
            List<String> providers = client.getChildren("/ringtail/" + node + "/providers");
            for (final String provider : providers) {
                System.out.println("Service provider address: " + provider);
                if(subscribedServices.get(node) != null){
                    subscribedServices.get(node).add(provider);
                }else {
                    List<String> address = new ArrayList<String>();
                    address.add(provider);
                    subscribedServices.put(node, address);
                }
            }
        }
    }

    @Override
    protected void doUnsubscribe(ReferenceConfig config) {

    }

    public List<ZookeeperClient> getClients() {
        return clients;
    }

    public Map<String, List<String>> getSubscribedServices() {
        return subscribedServices;
    }
}
