package Database;

import Helper.ReadPropertyHelper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl(ReadPropertyHelper.getProperty("db.jdbc_url"));
        config.setUsername(ReadPropertyHelper.getProperty("db.username"));
        config.setPassword(ReadPropertyHelper.getProperty("db.password"));
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