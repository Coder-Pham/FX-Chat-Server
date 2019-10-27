package Helper;

import Model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.io.*;

public class UserManager {
    private static HashSet<User> userOnlineList = new HashSet<>();
    private static HashSet<ObjectOutputStream> userOOSList = new HashSet<>();

    private static UserManager instance;

    public static void getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
    }

    public static void addUserOnline(User newUser, ObjectOutputStream oos) {
        userOnlineList.add(newUser);
        userOOSList.add(oos);
    }

    public static void removeUserOnline(User user, ObjectOutputStream oos) {
        userOnlineList.remove(user);
        userOOSList.remove(oos);
    }

    public static int getNumUserOnline() {
        return userOnlineList.size();
    }

    public static ArrayList<User> getUserOnlineList() {
        return new ArrayList<>(userOnlineList);
    }

    public static ArrayList<ObjectOutputStream> getUserOOSList() {
        return new ArrayList<>(userOOSList);
    }
}
