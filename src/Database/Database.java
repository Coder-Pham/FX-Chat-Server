package Database;

import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Database {
    private static Database instance;

    private Database() {
        // test connection to database
        try {
            Connection connection = DataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }
    }

    public static ArrayList<User> getUserList() {
        String sql = "SELECT * FROM fxchatdb.user";
        ArrayList<User> user_list = null;

        try {
            Connection connection = DataSource.getConnection();
            //Execute a query
            System.out.println("Creating getUserList statement...");
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            user_list = new ArrayList<>();
            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                String user_name = rs.getString("user_name");
                String password = rs.getString("password");
                String nick_name = rs.getString("nick_name");

                User new_user = new User(id, user_name, password, nick_name);
                user_list.add(new_user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user_list;
    }

    public static User getUser(String username) {
        String sql = String.format("SELECT * FROM fxchatdb.user WHERE user_name=\"%s\"", username);
        User user = null;

        try {
            Connection connection = DataSource.getConnection();
            //Execute a query
            System.out.println("Creating getUserList statement...");
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            user = new User(-1, "", "", "");
            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                String user_name = rs.getString("user_name");
                String password = rs.getString("password");
                String nick_name = rs.getString("nick_name");

                user.setId(id);
                user.setUsername(user_name);
                user.setPassword(password);
                user.setNickname(nick_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static boolean addUser(User newUser) {
        String sql = String.format("INSERT INTO fxchatdb.user (user_name, password, nick_name) VALUES (\"%s\",\"%s\",\"%s\")", newUser.getUsername(), newUser.getPassword(), newUser.getNickname());
        AtomicBoolean status = new AtomicBoolean(false);

        if (getUser(newUser.getUsername()).getUsername().equals("")) {
            try {
                Connection connection = DataSource.getConnection();
                //Execute a query
                System.out.println("Creating getUserList statement...");
                PreparedStatement pst = connection.prepareStatement(sql);
                int rs = pst.executeUpdate();

                status.set(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status.get();
    }

}

