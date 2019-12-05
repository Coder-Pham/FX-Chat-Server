package Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://database-1.ctk7womyxmbz.us-east-1.rds.amazonaws.com:3306/fxchatdb?autoReconnect=true&useUnicode=yes&serverTimezone=UTC&useSSL=true&requireSSL=false");
        config.setUsername("admin");
        config.setPassword("fxchatpassword12345");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DataSource() {
    }

    static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}