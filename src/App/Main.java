package App;

import Connection.Server;
import Database.Database;

public class Main {

    public static void main(String[] args) {
        //Test database
//        Database.getInstance();
//        System.out.println(Database.getUserList());
//        System.out.println(Database.getUser("quantrancse"));
//        System.out.println(Database.getUser("quantran"));
        Server server = new Server();
        server.run();
    }
}
