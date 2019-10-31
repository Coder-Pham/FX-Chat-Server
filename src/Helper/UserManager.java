package Helper;

import Database.Database;
import Model.*;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class UserManager {
    private static final HashMap<User, UserOnlineData> userOnlineList = new HashMap<>();

    private static UserManager instance;

    public static void getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
    }

    public synchronized static void addUserOnline(User newUser, ObjectOutputStream oos, String address) {
        UserOnlineData userOnlineData = new UserOnlineData(newUser,oos,address);
        userOnlineList.put(newUser, userOnlineData);
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

    public static UserAddressList getUserAddressList()
    {
        ArrayList<UserAddress> userAddresses = new ArrayList<UserAddress>();
        for (UserOnlineData userOnlineData : UserManager.getUserOOSList()) {
            UserAddress userAddress = new UserAddress(userOnlineData.getUser(),userOnlineData.getAddress());
            userAddresses.add(userAddress);
        }
        UserAddressList userAddressList = new UserAddressList();
        userAddressList.setUserAddresses(userAddresses);
        return userAddressList;
    }

    public static Collection<UserOnlineData> getUserOOSList() {
        return userOnlineList.values();
    }

    public static ObjectOutputStream getUserOOS(String username) {
        UserOnlineData userOnlineData = userOnlineList.get(Database.getUser(username));
        return userOnlineData.getObjectOutputStream();
    }
}
