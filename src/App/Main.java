package App;

import Connection.Server;
import Database.Database;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        server.turnOn();
    }
}
