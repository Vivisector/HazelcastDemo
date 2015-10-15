package com.zooplus.demo.runnable;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.zooplus.demo.common.Util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by @author Igor Ivaniuk on 12.10.2015.
 */
public class Reader {


    public static void main(String[] args) throws IOException {

        Boolean useReplicatedMap = false;
        if (args.length > 0) {
            if ("R".equals(args[0]) || "r".equals(args[0])) {
                useReplicatedMap = true;
                System.out.println("Using Replicated Map");
            }
        }

        ClientConfig clientConfig = new XmlClientConfigBuilder("hazelcast-client.xml").build();
        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);
        Map<String, String> bigMap = null;
        if (useReplicatedMap) {
            bigMap = hazelcastClient.getReplicatedMap("bigReplicated");
        } else {
            bigMap = hazelcastClient.getMap("big");
        }

        System.out.println("Waiting for START signal...");
        while (bigMap.get(Writer.START_KEY) == null) {
            // Do nothing
        }
        Long startTime = Calendar.getInstance().getTimeInMillis();
        for (int i = 1; i <= 10; i++) {
            System.out.println(Util.getTimestamp() + " | Retrieving " + i + " : Started");
            String bigString = null;
            while (bigString == null) {
                bigString = bigMap.get(Integer.toString(i));
            }
            System.out.println(Util.getTimestamp() + " | Retrieving " + i + " : Retrieved. Length: " + bigString.length() / 1024 * 2 + " KB");
        }
        Long endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("Took " + ((endTime - startTime) / 1000) + " seconds");

        hazelcastClient.shutdown();
    }
}
