package Connection;

import Controller.AuthenticationController;
import Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket client;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ClientHandler(Socket client)
    {
        this.client = client;
        try
        {
            this.objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            this.objectInputStream = new ObjectInputStream(client.getInputStream());
        }
        catch (IOException exception)
        {
            System.out.println(exception);
        }
    }

    @Override
    public void run() {
        while(true)
        {
            try
            {
                //Read request object from client
                Request request = (Request) this.objectInputStream.readObject();

                if(request.getAction().equals("login"))
                {
                    // Call call loginAPI in authentication controller

                }
            }
            catch (IOException | ClassNotFoundException exception)
            {
                System.out.println(exception);
            }
        }
    }
}
