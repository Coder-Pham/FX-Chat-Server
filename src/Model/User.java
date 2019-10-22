package Model;

public class User {
    private int id;
    private String user_name;
    private String password;
    private String nick_name;

    public User(int id, String user_name, String password, String nick_name) {
        this.id = id;
        this.user_name = user_name;
        this.password = password;
        this.nick_name = nick_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "ID: " + id + ", Username: " + user_name + ", Password: " + password + ", Nickname: " + nick_name;
    }
}