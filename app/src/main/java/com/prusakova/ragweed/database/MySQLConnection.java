package com.prusakova.ragweed.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {

    private static final String url = "jdbc:mysql://192.168.0.192:3306/ragweed";
    private static final String user = "root";
    private static final String password = "091040tardis";
    String res = "";


    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(url, user, password);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    private static String AddUser(String email, String pass) throws SQLException {
        String result="false";
        int x = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            PreparedStatement ps = con.prepareStatement("insert into login(email, password) values(?,?)");
            ps.setString(1, email);
            ps.setString(2, pass);

            x = ps.executeUpdate();

            if(x==1){
                result = "true";
            }

            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }



}
