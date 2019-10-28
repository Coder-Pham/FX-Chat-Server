package Connection;

import Controller.AuthenticationController;
import Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket client;
    private User currentUser;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    ClientHandler(Socket client) {
        this.client = client;
        try {
            this.objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            this.objectInputStream = new ObjectInputStream(client.getInputStream());
        } catch (IOException exception) {
            System.out.println("ClientHandler-Constructor: " + exception);
        }
    }

    @Override
    public void run() {
        boolean status = true;
        while(status && this.client.isConnected())
        {
            //Read request object from client
            Signal request = Signal.getRequest(this.objectInputStream);
            if(request == null)
            {
                break;
            }

            // Classify request actions
            switch (request.getAction())
            {
                case LOGIN:
                    status = this.callLoginAPI((User) request.getData());
                    break;
                case REGISTER:
                    status = this.callRegisterAPI((User) request.getData());
                    break;
                default:
                    System.out.println("A client call to unknown function !!");
                    status = false;
            }
        }
    }

    private boolean callLoginAPI(User user)
    {
        // Call call loginAPI in authentication controller
        User userData = AuthenticationController.loginAPI(user);
        if(userData.getId() > -1)
        {
            Signal response = new Signal(Action.LOGIN,true,userData,"");

            // After call loginAPI transfer response to the client
            return Signal.sendResponse(response,this.objectOutputStream);
        }
        else
        {
            Signal response = new Signal(Action.LOGIN,false,userData,"Your account is not valid");

            // After call loginAPI transfer response to the client
            return Signal.sendResponse(response,this.objectOutputStream);
        }
    }

    private boolean callRegisterAPI(User user)
    {
        if(AuthenticationController.registerAPI(user))
        {
            Signal response = new Signal(Action.REGISTER,true,"Your account is created","");

            // After call loginAPI transfer response to the client
            return Signal.sendResponse(response,this.objectOutputStream);
        }
        else
        {
            Signal response = new Signal(Action.REGISTER,false,null,"Your username is already in use");

            // After call loginAPI transfer response to the client
            return Signal.sendResponse(response,this.objectOutputStream);
        }
    }

    private void closeConnection() {
        // remove user from online list
        if (currentUser != null) {
            UserManager.removeUserOnline(currentUser, objectOutputStream);
        }

        // close resources
        if (objectInputStream != null) {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
