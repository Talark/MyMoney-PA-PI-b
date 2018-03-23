package com.jcg;

import java.sql.*;
import javax.swing.*;


public class MysqlConnect {
   
   Connection conn = null;
   
   public static Connection ConnectDB(){
       
       try {
           
           Class.forName("com.mysql.jdbc.Driver");
           
           Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:20002/mymoney?useSSL=false","root","password");
           JOptionPane.showMessageDialog(null, "Connection Successful");
           return conn;
             
       } catch (Exception e) {
           
           JOptionPane.showMessageDialog(null, e);
           return null;
       }
   }
   
}