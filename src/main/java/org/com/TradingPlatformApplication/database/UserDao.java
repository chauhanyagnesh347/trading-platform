package org.com.TradingPlatformApplication.database;

import org.com.TradingPlatformApplication.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    private final Map<String, User> userData = new HashMap<>();

    public void initUserData() {
        userData.clear();
    }

    public void insertUser(User user) {
        userData.put(user.getUserName(), user);
    }

    public void deleteUser(String userName) {
        userData.remove(userName);
    }

    public User findUser(String userName) {
        if (userData.containsKey(userName))
            return userData.get(userName);
        throw new IllegalArgumentException("User " + userName + " not found");
    }

    public void updateUser(User user) {
        insertUser(user);
    }


}
