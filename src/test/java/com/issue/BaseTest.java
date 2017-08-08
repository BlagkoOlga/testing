package com.issue;

import org.apache.log4j.Logger;
import org.testng.annotations.*;

import javax.swing.*;
import java.sql.*;

import static com.issue.IssueConstants.PATH;

/**
 * Created by olgaliutsko on 8/5/17.
 */
public class BaseTest {
    protected int clientId;
    protected double balancesValue;
    private Connection conn;
    private Statement stat;
    private static final Logger logger = Logger.getLogger(BaseTest.class);

    @BeforeTest
    public void connectToDB() {
        logger.info("Step 1 - Connect to DB");
        this.conn = getConnection();
        try {
            this.stat = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterTest(alwaysRun = true)
    public void close() {
        logger.info("After test - clear db");
        executeUpdateCommand("delete from CLIENTS where CLIENTS.CLIENT_ID=5;");
        executeUpdateCommand("delete from BALANCES where BALANCES.CLIENTS_CLIENT_ID=5;");
        if (conn != null) {
            try {
                conn.close();
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet executeCommand(final String sql) {
        try {
            return conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int executeUpdateCommand(final String sql) {
        try {
            return conn.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:" + PATH + "clients.db");
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Problem with connection of database");
            return null;
        }
    }
}
