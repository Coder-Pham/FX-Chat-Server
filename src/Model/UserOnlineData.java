package Model;

import java.io.ObjectOutputStream;

public class UserOnlineData {
    private User user;
    private ObjectOutputStream objectOutputStream;
    private String address;

    public UserOnlineData(User user, ObjectOutputStream objectOutputStream, String address) {
        this.user = user;
        this.objectOutputStream = objectOutputStream;
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
