package lk.disaster.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBConnection {
    private static HikariDataSource dataSource;
    private static final Logger logger = LogManager.getLogger(DBConnection.class);
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/disaster_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("maximumPoolSize", "20");
        config.addDataSourceProperty("connectionTimeout", "30000");

        try {
            dataSource = new HikariDataSource(config);
            logger.info("Database connection pool initialized successfully.");
        } catch (Exception e) {
            logger.error("Error initializing database connection pool.", e);
            throw new ExceptionInInitializerError("Failed to initialize database connection pool");
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Error getting database connection.", e);
            throw e;
        }

    }
    // Close the datasource
    public static void shutdown() {
        if (dataSource != null) {
            dataSource.close();
            logger.info("Database connection pool shut down successfully.");
        }
    }

}