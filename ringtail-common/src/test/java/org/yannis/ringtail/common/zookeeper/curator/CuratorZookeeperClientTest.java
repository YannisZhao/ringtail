package org.yannis.ringtail.common.zookeeper.curator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yannis.ringtail.common.zookeeper.enums.NodeType;

import static org.junit.Assert.*;

/**
 * Created by dell on 2016/6/29.
 */
public class CuratorZookeeperClientTest {

    CuratorZookeeperClient client;

    @Before
    public void init(){
        client = new CuratorZookeeperClient();
    }

    @After
    public void destroy(){
        client = null;
    }

    @Test
    public void create() throws Exception {
        String basePath = "/org.yannis.test";
        client.create(basePath+".DemoService1-1.0.0", NodeType.PERSISTENT);
        client.create(basePath+".DemoService1-1.0.1", NodeType.PERSISTENT_SEQUENTIAL);
        client.create(basePath+".DemoService2-1.0.0", NodeType.EPHEMERAL);
        client.create(basePath+".DemoService2-1.0.1", NodeType.EPHEMERAL_SEQUENTIAL);
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void setData() throws Exception {

    }

    @Test
    public void getData() throws Exception {

    }

    @Test
    public void isExist() throws Exception {

    }

    @Test
    public void getChildren() throws Exception {

    }

    @Test
    public void isAlive() throws Exception {

    }

    @Test
    public void close() throws Exception {

    }

}