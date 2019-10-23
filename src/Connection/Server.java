package Connection;

import Config.ConfigVariable;
import Helper.UserManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private UserManager userManager;

    public Server()
    {
        try
        {
            this.serverSocket = new ServerSocket(ConfigVariable.port);
            this.userManager = new UserManager();
        }
        catch (IOException exception)
        {
            System.out.println(exception);
        }
    }

    public void turnOn()
    {
        while (true)
        {
            try
            {
                //Waiting for client socket connect to serversocket
                Socket client = this.serverSocket.accept();

                System.out.print("A client just connect to our server");

                //Create new thread for handling a specific client
                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
            catch (IOException exception)
            {
                System.out.println(exception);
            }
        }
    }
}
