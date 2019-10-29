package Helper;

import Model.User;
import Model.UserOnlineList;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class UserManager {
    private static final HashMap<User, ObjectOutputStream> userOnlineList = new HashMap<>();

    private static UserManager instance;

    public static void getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
    }

    public synchronized static void addUserOnline(User newUser, ObjectOutputStream oos) {
        userOnlineList.put(newUser, oos);
    }

    public synchronized static void removeUserOnline(User user) {
        userOnlineList.remove(user);
    }

    public static int getNumUserOnline() {
        return userOnlineList.size();
    }

    public static UserOnlineList getUserOnlineList() {
        UserOnlineList result = new UserOnlineList();
        result.setUsers(new ArrayList<>(userOnlineList.keySet()));
        return result;
    }

    public static Collection<ObjectOutputStream> getUserOOSList() {
        return userOnlineList.values();
    }

    public static ObjectOutputStream getUserOOS(User user) {
        return userOnlineList.get(user);
    }
}
