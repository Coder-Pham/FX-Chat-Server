package Controller;

import Database.Database;
import Model.User;

import java.util.ArrayList;

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
}
