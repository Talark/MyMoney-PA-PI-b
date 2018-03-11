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
	public static int DATArows;  
	public static final int DATAcols = 5;
	public static Object[][] DATA;
	
	public Model() throws SQLException, ClassNotFoundException {
		super(retrieveDATA(), TABLE_HEADER);
	}
	
	protected static Object[][] retrieveDATA() throws SQLException, ClassNotFoundException{
		String url ="jdbc:mysql://localhost:20002/mymoney";
		Connection con = DriverManager.getConnection(url, "root", "password");
		System.out.println("Connection established Successfully");

		Statement statement=con.createStatement();
		
		String sqlQuery = "SELECT COUNT(*) FROM transaction";
		ResultSet rs= statement.executeQuery(sqlQuery);
		rs.next();
		DATArows = rs.getInt(1);
		
		DATA = new Object[DATArows][DATAcols];
		
		String sqlQuery2 = "SELECT TRANSACTION_ID, amount, transaction_date, transaction_type, transaction_cat FROM transaction, transaction_type, transaction_category WHERE transaction.transaction_type_id = transaction_type.transaction_type_id AND transaction.transaction_cat_id = transaction_category.transaction_cat_id";
		ResultSet rs2 = statement.executeQuery(sqlQuery2);
		
		for(int i=0; rs2.next(); i++) {
				DATA[i][0] = rs2.getString(1);
				DATA[i][1] = rs2.getString(2);
				DATA[i][2] = rs2.getString(3);
				DATA[i][3] = rs2.getString(4);
				DATA[i][4] = rs2.getString(5);
		}
		
		con.close();
		return DATA;
	}

}
