package com.jcg;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;		
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class Model extends DefaultTableModel {
	
	public static final Object[] TABLE_HEADER = { "Transaction id", "Amount",
			"Transaction Date", "Transaction Type", "Transaction Category"};
	public static final int DATArows = 50;  
	public static final int DATAcols = 5;
	public static Object[][] DATA = new Object[DATArows][DATAcols];
	
	public Model() throws SQLException, ClassNotFoundException {
		super(retrieveFromDB(), TABLE_HEADER);
	}
	
	private static Object[][] retrieveFromDB() throws SQLException, ClassNotFoundException{
		String url ="jdbc:mysql://localhost:20002/mymoney";
		Connection con = DriverManager.getConnection(url, "root", "password");
		System.out.println("Connection established Successfully");

		Statement statement=con.createStatement();
		
		String sqlQuery = "SELECT TRANSACTION_ID, amount, transaction_date, transaction_type, transaction_cat FROM transaction, transaction_type, transaction_category WHERE transaction.transaction_type_id = transaction_type.transaction_type_id AND transaction.transaction_cat_id = transaction_category.transaction_cat_id";
		ResultSet rs = statement.executeQuery(sqlQuery);
		
		for(int i=0; rs.next(); i++) {
			try {
				DATA[i][0] = rs.getString(1);
				DATA[i][1] = rs.getString(2);
				DATA[i][2] = rs.getString(3);
				DATA[i][3] = rs.getString(4);
				DATA[i][4] = rs.getString(5);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("The database contains more than the allowable number of transactions (" + DATArows + ")");
				e.printStackTrace();
			}
		}	
		con.close();
		return DATA;
	}

}
