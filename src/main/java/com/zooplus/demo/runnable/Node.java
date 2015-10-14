package com.zooplus.demo.runnable;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Created by @author Igor Ivaniuk on 12.10.2015.
 */
public class Node {

    public static void main(String[] args) throws InterruptedException {
        Config cfg = new ClasspathXmlConfig("hazelcast.xml");
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(cfg);
        System.out.println("*** Node started");
        Thread.sleep(600000L);
        Hazelcast.shutdownAll();
    }
}
