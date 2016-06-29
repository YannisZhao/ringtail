package org.yannis.ringtail.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by yannis on 6/28/16.
 */
public class ZookeeperTest {
    private static final int TIMEOUT = 30000;

    private ZooKeeper zooKeeper;

    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println(watchedEvent.getType());
        }
    };

    @Before
    public void connect() throws IOException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", TIMEOUT, watcher);
    }

    @After
    public void close(){
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreate(){
        String result = null;
        try {
            result = zooKeeper.create("/zk001","zk001Data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.fail();
        }
        System.out.println("create result: "+result);
    }


    @Test
    public void testGetData(){
        String result = null;
        try {
            byte[] bytes = zooKeeper.getData("/zk001", null, null);
            result = new String(bytes);
        } catch (KeeperException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.fail();
        }
        System.out.println("getData result: "+result);
    }

    @Test
    public void testGetDataWithWatcher(){
        String result = null;
        try {
            byte[] bytes = zooKeeper.getData("/zk001", new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("testGetDataWithWatcher watch: " + watchedEvent.getType());
                }
            }, null);
            result = new String(bytes);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("getData result: "+result);

        try {
            zooKeeper.setData("/zk001", "testSetData".getBytes(), -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testExists(){
        Stat stat = null;
        try {
            stat = zooKeeper.exists("/zk001", false);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(stat);
        System.out.println("exists result: "+stat.getCzxid());
    }

    @Test
    public void testSetData(){
        Stat stat = null;
        try {
            stat = zooKeeper.setData("/zk001", "testSetData".getBytes(), -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(stat);
        System.out.println("setData result: "+stat.getVersion());
    }

    @Test
    public void testExistsWatch(){
        Stat stat = null;
        try{
            stat = zooKeeper.exists("/zk001", true);
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertNotNull(stat);

        try{
            zooKeeper.delete("/zk001", -1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testExistsWatch2(){
        Stat stat = null;
        try{
            stat = zooKeeper.exists("/zk001", new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("testExistsWatch2 watch: "+watchedEvent.getType());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertNotNull(stat);

        // 触发watcher 中的process方法   NodeDataChanged
        try {
            zooKeeper.setData("/zk001", "testExistsWatch2".getBytes(), -1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 不会触发watcher 只会触发一次
        try {
            zooKeeper.delete("/zk001", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetChild(){
        try{
            zooKeeper.create("/zk", "zk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zooKeeper.create("/zk/001", "d001".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zooKeeper.create("/zk/002", "d002".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            List<String> list = zooKeeper.getChildren("/zk", true);
            for(String node : list){
                System.out.println("node "+ node);
            }
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testDelete(){
        try {
            zooKeeper.delete("/zk001", -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (KeeperException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}
