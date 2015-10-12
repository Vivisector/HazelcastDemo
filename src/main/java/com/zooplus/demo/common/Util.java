package com.zooplus.demo.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by @author Igor Ivaniuk on 12.10.2015.
 */
public class Util {

    private static final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

    public static String createRandomDataSize(int msgSize) {
        Random random = new Random();
        // Java chars are 2 bytes
        msgSize = msgSize / 2;
        msgSize = msgSize * 1024;
        msgSize = msgSize * 1024;
        StringBuilder sb = new StringBuilder(msgSize);
        for (int i = 0; i < msgSize; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static String getTimestamp() {
        return format.format(Calendar.getInstance().getTime());
    }
}
