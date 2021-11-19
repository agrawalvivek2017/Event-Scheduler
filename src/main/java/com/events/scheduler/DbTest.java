package com.events.scheduler;

import java.sql.*;

public class DbTest {

    public void testDb(String testValue) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost", "root", "Mysql@99");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("create database IF NOT EXISTS ooad_test");
            stmt.executeUpdate("use ooad_test");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS dummy (value VARCHAR(100))");
            stmt.executeUpdate("INSERT into dummy values('" + testValue + "')");
            ResultSet rs = stmt.executeQuery("select * from dummy");
            while (rs.next()) {
                System.out.println(rs.getString("value"));
            }
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }

    }
}
