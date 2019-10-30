package Connection;

import Controller.AuthenticationController;
import Model.MessageModel;
import Model.User;
import Model.FileInfo;
import Helper.UserManager;

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
        while (status && this.client.isConnected()) {
            //Read request object from client
            Signal request = Signal.getRequest(this.objectInputStream);
            if (request == null) {
                break;
            }

            // Classify request actions
            switch (request.getAction()) {
                case LOGIN:
                    status = this.callLoginAPI((User) request.getData());
                    break;
                case REGISTER:
                    status = this.callRegisterAPI((User) request.getData());
                    break;
                case UOL:
                    status = this.callGetUserOnlineList();
                    break;
                case MESSAGE:
                    status = this.callSendMessage((MessageModel) request.getData());
                    break;
                case FILE:
                    status = this.callSendFile((FileInfo) request.getData());
                    break;
                case LOGOUT:
                    status = false;
                    break;
                default:
                    System.out.println("A client call to unknown function !!");
                    status = false;
            }
        }
        this.closeConnection();
        this.updateUserOnlineList();
    }

    private boolean callLoginAPI(User user) {
        // Call call loginAPI in authentication controller
        User userData = AuthenticationController.loginAPI(user);
        if (userData.getId() > -1) {
            // add user to online list
            currentUser = userData;
            UserManager.addUserOnline(currentUser, objectOutputStream);
            this.updateUserOnlineList();


            Signal response = new Signal(Action.LOGIN, true, userData, "");

            // After call loginAPI transfer response to the client
            return Signal.sendResponse(response, this.objectOutputStream);
        } else {
            Signal response = new Signal(Action.LOGIN, false, userData, "Your account is not valid");

            // After call loginAPI transfer response to the client
            return Signal.sendResponse(response, this.objectOutputStream);
        }
    }

    private boolean callRegisterAPI(User user) {
        if (AuthenticationController.registerAPI(user)) {
            Signal response = new Signal(Action.REGISTER, true, user, "");

            // After call loginAPI transfer response to the client
            return Signal.sendResponse(response, this.objectOutputStream);
        } else {
            Signal response = new Signal(Action.REGISTER, false, null, "Your username is already in use");

            // After call loginAPI transfer response to the client
            return Signal.sendResponse(response, this.objectOutputStream);
        }
    }

    private boolean callGetUserOnlineList() {
        Signal response = new Signal(Action.UOL, true, UserManager.getUserOnlineList(), "");

        return Signal.sendResponse(response, this.objectOutputStream);
    }

    private boolean callSendMessage(MessageModel message) {
        User receiver = message.getReceiver();
        ObjectOutputStream receiverOOS = UserManager.getUserOOS(receiver.getUsername());
        if (receiverOOS != null) {
            Signal response = new Signal(Action.MESSAGE, true, message, "");

            if (Signal.sendResponse(response, receiverOOS)) {
                System.out.println("Send message successfully");
            } else {
                System.out.println("Could not send message");
            }
        }
        return true;
    }

    private boolean callSendFile(FileInfo fileInfo) {
        User receiver = fileInfo.getReceiver();
        ObjectOutputStream receiverOOS = UserManager.getUserOOS(receiver.getUsername());
        if (receiverOOS != null) {
            Signal response = new Signal(Action.FILE, true, fileInfo, "");

            if (Signal.sendResponse(response, receiverOOS)) {
                System.out.println("Send file successfully");
            } else {
                System.out.println("Could not send file");
            }
        }
        return true;
    }

    private void updateUserOnlineList() {
        for (ObjectOutputStream outputStream : UserManager.getUserOOSList()) {
            if (outputStream != this.objectOutputStream) {
                Signal response = new Signal(Action.UOL, true, UserManager.getUserOnlineList(), "");
                Signal.sendResponse(response, outputStream);
            }
        }
    }

    private void closeConnection() {
        // remove user from online list
        if (currentUser != null) {
            UserManager.removeUserOnline(currentUser);
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
