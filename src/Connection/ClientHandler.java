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
        while(this.client.isConnected())
        {
            try
            {
                //Read request object from client
                Signal request = (Signal) this.objectInputStream.readObject();

                if(request.getAction().equals(Action.LOGIN))
                {
                    // Call call loginAPI in authentication controller
                    User userData = AuthenticationController.loginAPI((User) request.getData());
                    if(userData.getId() > -1)
                    {
                        Signal response = new Signal(Action.LOGIN,true,userData,"");

                        // After call loginAPI transfer response to the client
                        this.objectOutputStream.writeObject(response);
                    }
                    else
                    {
                        Signal response = new Signal(Action.LOGIN,false,userData,"Your account is not valid");

                        // After call loginAPI transfer response to the client
                        this.objectOutputStream.writeObject(response);
                    }


                }
            }
            catch (IOException | ClassNotFoundException exception)
            {
                System.out.println(exception);
                break;
            }
        }
    }
}
