package com.zooplus.demo.runnable;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.zooplus.demo.common.Util;

import java.io.IOException;
import java.util.Map;

/**
 * Created by @author Igor Ivaniuk on 12.10.2015.
 */
public class Writer {

    public static final String START_KEY = "START";

    public static void main(String[] args) throws InterruptedException, IOException {
        Boolean useReplicatedMap = false;
        Integer packetSize = null;
        if (args.length > 0) {
            if ("R".equals(args[0]) || "r".equals(args[0])) {
                useReplicatedMap = true;
                System.out.println("Using Replicated Map");
            }
            try {
                packetSize = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[1] + " must be an integer.");
                System.exit(1);
            }
        }
        if (packetSize == null) {
            packetSize = 10;
        }
        System.out.println("Packet size: " + packetSize + " MB");

        ClientConfig clientConfig = new XmlClientConfigBuilder("hazelcast-client.xml").build();
        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);
        Map<String, String> bigMap = null;
        if (useReplicatedMap) {
            bigMap = hazelcastClient.getReplicatedMap("bigReplicated");
        } else {
            bigMap = hazelcastClient.getMap("big");
        }

        System.out.println("Sending start signal");
        bigMap.put(START_KEY, START_KEY);

        for (int i = 1; i <= 10; i++) {
            System.out.println(Util.getTimestamp() + " | Object " + i + " : Started");
            String bigString = Util.createRandomDataSize(packetSize);
            System.out.println(Util.getTimestamp() + " | Object " + i + " : String generated");
            bigMap.put(Integer.toString(i), bigString);
            System.out.println(Util.getTimestamp() + " | Object " + i + " : Sent to " + (useReplicatedMap ? "replicated " : "") + "map");
        }
        hazelcastClient.shutdown();
    }

}
