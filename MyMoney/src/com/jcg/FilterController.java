package com.jcg;

import java.awt.event.ActionEvent;		
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

//This is a Controller class that manages the filtering of transactions by their transaction type, category and date range.
public class FilterController implements ActionListener {
	
	private JTextField typeTextField; 
	private JTextField catTextField; 
	private JTextField fromDateTextField;
	private JTextField toDateTextField;
	private DefaultTableModel model;

	public FilterController(JTextField typeTextField, JTextField catTextField, JTextField fromDateTextField, JTextField toDateTextField, DefaultTableModel model) {
		super();
		this.typeTextField = typeTextField;
		this.catTextField = catTextField;
		this.fromDateTextField = fromDateTextField;
		this.toDateTextField = toDateTextField;
		this.model = model;
	}

	//This method is called when the user clicks the "Filter" button in the View Transactions tab after choosing to filter by date,
	//type and/or category of transaction. It updates the Model to show the relevant transactions.
	public void actionPerformed(ActionEvent e) {
		try{
			actionPerformed2();
		} catch(SQLException e2){
			e2.printStackTrace();
		}
	}

	private void actionPerformed2() throws SQLException {
		String url ="jdbc:mysql://localhost:20002/mymoney?useSSL=false";
		Connection con = DriverManager.getConnection(url, "root", "password");
		System.out.println("Connection established Successfully");
		Statement statement=con.createStatement();
		
		String sqlQuery = "SELECT transaction_id, amount, transaction_date, transaction_type, transaction_cat "
				+ "FROM transaction, transaction_type, transaction_category "
				+ "WHERE transaction.transaction_type_id = transaction_type.transaction_type_id AND transaction.transaction_cat_id = transaction_category.transaction_cat_id";
		if(!typeTextField.getText().equals(""))
			sqlQuery=sqlQuery + " AND transaction_type='" +  typeTextField.getText() + "'";
		if(!catTextField.getText().equals(""))
			sqlQuery=sqlQuery + " AND transaction_cat='" +  catTextField.getText() + "'";
		if(!fromDateTextField.getText().equals(""))
			sqlQuery=sqlQuery + " AND transaction_date>='" +  fromDateTextField.getText() + "'";
		if(!toDateTextField.getText().equals(""))
			sqlQuery=sqlQuery + " AND transaction_date<='" +  toDateTextField.getText() + "'";
		
		ResultSet rs= statement.executeQuery(sqlQuery);
		Object [][] newData = new Object[Model.DATArows][Model.DATAcols];
		
		for(int i=0; rs.next(); i++) {
			newData[i][0] = rs.getString(1);
			newData[i][1] = Math.abs(Integer.valueOf(rs.getString(2)));
			newData[i][2] = rs.getString(3);
			newData[i][3] = rs.getString(4);
			newData[i][4] = rs.getString(5);
		}	
		model.setDataVector(newData, Model.TABLE_HEADER);	
	}

}
