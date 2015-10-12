package com.zooplus.demo.runnable;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.zooplus.demo.common.Util;

import java.util.Map;

/**
 * Created by @author Igor Ivaniuk on 12.10.2015.
 */
public class Writer {
    public static void main(String[] args) throws InterruptedException {
        Integer packetSize = null;
        if (args.length > 0) {
            try {
                packetSize = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        }
        if (packetSize == 0) {
            packetSize = 10;
        }
        System.out.println("Packet size: " + packetSize + " MB");

        ClientConfig clientConfig = new ClientConfig();
        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);
        Map<Integer, String> bigMap = hazelcastClient.getMap("big");


        for (int i = 1; i <= 10; i++) {
            System.out.println(Util.getTimestamp() + " | Object " + i + " : Started");
            String bigString = Util.createRandomDataSize(packetSize);
            System.out.println(Util.getTimestamp() + " | Object " + i + " : String generated");
            bigMap.put(i, bigString);
            System.out.println(Util.getTimestamp() + " | Object " + i + " : Sent to map");
        }
        hazelcastClient.shutdown();
    }

}
