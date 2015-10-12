package com.zooplus.demo.service;

/**
 * Created by @author Igor Ivaniuk on 12.10.2015.
 */

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.zooplus.demo.model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceUnitTest {

    private static HazelcastInstance hazelcastInstance1 = null;
    private static HazelcastInstance hazelcastInstance2 = null;
    private static HazelcastInstance hazelcastInstance3 = null;
    private static Service<User> userService = null;

    @BeforeClass
    public static void setUpForAllTests() throws Exception {
        // Create 3 Hazelcast instances for these tests
        hazelcastInstance1 = Hazelcast.newHazelcastInstance();
        hazelcastInstance2 = Hazelcast.newHazelcastInstance();
        hazelcastInstance3 = Hazelcast.newHazelcastInstance();

        userService = UserService.getInstance();
    }

    @AfterClass
    public static void destroyAfterAllTests() throws Exception {
        Hazelcast.shutdownAll();
    }

    @After
    public void clearData() {
        userService.removeAll();
    }

    @Test
    public void userServiceAddGetTest() {
        User user = new User("Igor", "Ivaniuk", "spam@test.com");
        assertNull(userService.get(user));
        userService.add(user);
        assertNotNull(userService.get(user));
    }

    @Test
    public void userServiceGetByMailTest() {
        User user = new User("Igor", "Ivaniuk", "spam@test.com");
        assertNull(userService.get(user));
        userService.add(user);
        assertNotNull(userService.get("spam@test.com"));
    }

    @Test
    public void userServiceGetAllTest() {
        assertEquals(0, userService.getAll().size());
        User user = new User("Igor", "Ivaniuk", "spam@test.com");
        userService.add(user);
        assertNotNull(userService.getAll());
        assertEquals(1, userService.getAll().size());
    }

    @Test
    public void userServiceRemoveTest() {
        User user = new User("Igor", "Ivaniuk", "spam@test.com");
        assertNull(userService.get(user));
        userService.add(user);
        assertNotNull(userService.get(user));
        userService.remove(user);
        assertNull(userService.get(user));
        assertEquals(0, userService.getAll().size());
    }

    @Test
    public void userServiceRemoveByMailTest() {
        User user = new User("Igor", "Ivaniuk", "spam@test.com");
        assertNull(userService.get(user));
        userService.add(user);
        assertNotNull(userService.get(user));
        userService.remove("spam@test.com");
        assertNull(userService.get(user));
        assertEquals(0, userService.getAll().size());
    }

    @Test
    public void userServiceRemoveAllTest() {
        User user = new User("Igor", "Ivaniuk", "spam@test.com");
        assertNull(userService.get(user));
        userService.add(user);
        assertNotNull(userService.get(user));
        userService.removeAll();
        assertNull(userService.get(user));
        assertEquals(0, userService.getAll().size());
    }
}
