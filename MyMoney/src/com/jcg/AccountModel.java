package com.jcg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//This class represents the account entity and is used to keep track of the overall balance. 
public class AccountModel {
private static final int accountId = 1155;
private double balance = 0;

public double getBalance() {
	return balance;
}

//Aggregates the amounts of all the transactions stored in the database and updates the balance property with the result
public void updateBalance() throws SQLException{
	String url ="jdbc:mysql://localhost:20002/mymoney?useSSL=false";
	Connection con = DriverManager.getConnection(url, "root", "password");
	System.out.println("Connection established Successfully");
	Statement statement=con.createStatement();
	
	String sqlQuery = "SELECT SUM(AMOUNT) FROM transaction";
	ResultSet rs= statement.executeQuery(sqlQuery);
	rs.next();
	this.balance = rs.getDouble(1);
}

}
