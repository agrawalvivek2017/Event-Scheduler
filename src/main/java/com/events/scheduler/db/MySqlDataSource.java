package com.events.scheduler.db;

import java.sql.*;

public class MySqlDataSource implements  DataRepository<java.sql.ResultSet> {

    private Connection con = null;

    @Override
    public synchronized void openConnection(String userName, String password, String databaseName) {
        try {
            if (con == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost", userName, password);
            }
            if (!databaseName.isEmpty()) {
                System.out.println("Performing use " + databaseName);
                performQuery("use " + databaseName);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
            closeConnection();
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (con != null)
                    con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        con = null;
    }


    private boolean checkAndPrintConnectionError() {
        if (con == null) {
            System.out.println("Connection not open");
            return false;
        }
        return true;
    }

    @Override
    public void performQuery(String query) {
       if (!checkAndPrintConnectionError()) return;
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("query = " + query);
        } catch (SQLException ex) {
            System.out.println("error in doing query = " + query);
            System.out.println(ex.toString());
        }
    }

    @Override
    public ResultSet performQueryWithResult(String query) {
        if (!checkAndPrintConnectionError()) return null;
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            System.out.println("query = " + query);
            return rs;
        } catch (SQLException ex) {
            System.out.println("error in doing query = " + query);
            System.out.println(ex.toString());
        }
        return null;
    }

    @Override
    public ResultSet requestData(String query) {
        if (!checkAndPrintConnectionError()) return null;
        try {
            Statement stmt = con.createStatement();
            System.out.println("Executing query =" + query);
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return null;
    }
}
