package org.com.database;

import org.com.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {

    private static Map<String, User> userData = new HashMap<>();

    public static void initUserData() {
        userData.clear();
    }

    public static void insertUser(User user) {
        userData.put(user.getUserName(), user);
    }

    public static void deleteUser(String userName) {
        userData.remove(userName);
    }

    public static User findUser(String userName) {
        if (userData.containsKey(userName))
            return userData.get(userName);
        throw new IllegalArgumentException("User " + userName + " not found");
    }

    public static void updateUser(User user) {
        insertUser(user);
    }


}
