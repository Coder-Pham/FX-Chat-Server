package Controller;

import Database.Database;
import Model.User;


public class AuthenticationController {
    public static User loginAPI(User user)
    {
        User dbUser = Database.getUser(user.getUsername());
        if(user.getPassword().equals(dbUser.getPassword()))
        {
            dbUser.setPassword("");
            return dbUser;
        }
        return new User(-1,"","","");
    }

    public static boolean registerAPI(User user)
    {
        return Database.addUser(user);
    }
}