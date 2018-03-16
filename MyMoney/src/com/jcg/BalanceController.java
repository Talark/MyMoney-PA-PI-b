package com.jcg;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;

public class BalanceController{
	private JLabel balanceLabel = new JLabel();

	public BalanceController(JLabel balanceLabel) {
		super();
		this.balanceLabel = balanceLabel;
	}
	
	public void displayBalance() throws SQLException{
		String url ="jdbc:mysql://localhost:20002/mymoney?useSSL=false";
		Connection con = DriverManager.getConnection(url, "root", "password");
		System.out.println("Connection established Successfully");
		Statement statement=con.createStatement();
		
		String sqlQuery = "SELECT SUM(AMOUNT) FROM transaction";
		ResultSet rs= statement.executeQuery(sqlQuery);
		rs.next();
		double balance = rs.getDouble(1);
		this.balanceLabel.setText(Math.abs(balance)+ " $");
		if(balance < 0)
			this.balanceLabel.setForeground(Color.RED);	
			else
			this.balanceLabel.setForeground(Color.GREEN);
	}
}
