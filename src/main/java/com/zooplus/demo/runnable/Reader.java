package com.zooplus.demo.runnable;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.zooplus.demo.common.Util;

import java.util.Map;

/**
 * Created by @author Igor Ivaniuk on 12.10.2015.
 */
public class Reader {

    public static void main(String[] args) {

        ClientConfig clientConfig = new ClientConfig();
        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);
        Map<Integer, String> bigMap = hazelcastClient.getMap("big");

        for (int i = 1; i <= 10; i++) {
            System.out.println(Util.getTimestamp() + " | Retrieving " + i + " : Started");
            String bigString = null;
            while (bigString == null) {
                bigString = bigMap.get(i);
            }
            System.out.println(Util.getTimestamp() + " | Retrieving " + i + " : Retrieved. Length: " + bigString.length() / 1024 * 2 + " KB");
        }

        hazelcastClient.shutdown();
    }
}
