package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import Model.User;

public class Database {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://fxchatdb.mysql.database.azure.com:3306/fxchatdb?autoReconnect=true&useUnicode=yes&serverTimezone=UTC&useSSL=true&requireSSL=false";

    //  Database credentials
    private static final String USER = "fxchat@fxchatdb";
    private static final String PASS = "fxch@tp@ssw0rd";

    private static Database instance;
    private static Connection connection;

    private Database() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    private Connection getConnection() {
        return connection;
    }

    public static void getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        } else if (instance.getConnection().isClosed()) {
            instance = new Database();
        }

    }

    public static ArrayList<User> getUserList() {
        Statement stmt = null;
        ArrayList<User> user_list = new ArrayList<>();

        try {
            //Execute a query
            System.out.println("Creating getUserList statement...");
            stmt = connection.createStatement();
            String sql = "SELECT * FROM fxchatdb.user";
            ResultSet rs = stmt.executeQuery(sql);

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
            //Clean-up environment
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException ignored) {
            }// nothing we can do
        }//end try

        return user_list;
    }

    public static User getUser(String username) {
        Statement stmt = null;
        User user = new User(-1, "", "", "");

        try {
            //Execute a query
            System.out.println("Creating getUser statement...");
            stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM fxchatdb.user WHERE user_name=\"%s\"", username);
            ResultSet rs = stmt.executeQuery(sql);

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
            //Clean-up environment
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException ignored) {
            }// nothing we can do
        }//end try

        return user;
    }

    public static boolean addUser(User newUser) {
        AtomicBoolean status = new AtomicBoolean(false);

        if (getUser(newUser.getUsername()).getUsername() == "") {
            Statement stmt = null;
            try {
                //Execute a query
                System.out.println("Creating addUser statement...");
                stmt = connection.createStatement();
                String sql = String.format("INSERT INTO fxchatdb.user (user_name, password, nick_name) VALUES (\"%s\",\"%s\",\"%s\")", newUser.getUsername(), newUser.getPassword(), newUser.getNickname());
                stmt.executeUpdate(sql);

                status.set(true);
                //Clean-up environment
                stmt.close();
            } catch (SQLException se) {
                //Handle errors for JDBC
                se.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException ignored) {
                }// nothing we can do
            }//end try
        }

        return status.get();
    }

}

