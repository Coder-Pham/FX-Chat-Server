package Helper;

import Database.Database;
import Model.User;
import Model.UserOnlineList;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class UserManager {
    private static final HashSet<User> userOnlineList = new HashSet<>();
    private static final HashMap<String, ObjectOutputStream> userOnlineOOSList = new HashMap<>();

    private static UserManager instance;

    public static void getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
    }

    public synchronized static void addUserOnline(User newUser, ObjectOutputStream oos) {
        userOnlineList.add(newUser);
        userOnlineOOSList.put(newUser.getUsername(), oos);
    }

    public synchronized static void removeUserOnline(User user) {
        userOnlineList.remove(user);
        userOnlineOOSList.remove(user.getUsername());
    }

    public static int getNumUserOnline() {
        return userOnlineList.size();
    }

    public static UserOnlineList getUserOnlineList() {
        UserOnlineList result = new UserOnlineList();
        result.setUsers(new ArrayList<>(userOnlineList));
        return result;
    }

    public static Collection<ObjectOutputStream> getUserOOSList() {
        return userOnlineOOSList.values();
    }

    public static ObjectOutputStream getUserOOS(String username) {
        return userOnlineOOSList.get(username);
    }
}
