package com.jcg;

import java.awt.event.ActionEvent;	
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;		
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddTransacController implements ActionListener {
	
	private DefaultTableModel model;
	private JTabbedPane jtp;
	private JTextField amount;
	private JTextField date;
	private JTextField type;
	private JTextField cat;
	
	public AddTransacController(DefaultTableModel model, JTabbedPane jtp, JTextField amount, JTextField date,
			JTextField type, JTextField cat) {
		super();	
	
		this.model=model;
		this.jtp = jtp;
		this.amount=amount;
		this.date=date;
		this.type=type;
		this.cat=cat;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		
		//prepare transaction ID
		int transacID = -1;
		try {
			transacID = getLastIdTrans()+1;
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		//prepare amount
		double addAmount=Double.parseDouble(amount.getText());
		
		//prepare date
		java.sql.Date myDate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date tempDate = format.parse(date.getText());
			myDate = new java.sql.Date(tempDate.getTime());
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		//prepare type ID
		int typeId= getTypeID(type.getText());
		if(typeId==2)
			addAmount=-addAmount;
		
		//prepare category ID
		int catId = -1;
		try {
			catId = catExists(cat.getText());
		} catch (ClassNotFoundException | SQLException e2) {
			e2.printStackTrace();
		}
		
		//now  create TransactionModel object with these variables
		TransactionModel tm = new TransactionModel(transacID, addAmount, myDate, typeId, catId);
		
		//insert that TransactionModel object's data into the DB
		try {
			insertInDb(tm);
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		//update the Model so that ViewTransactions is updated
		try {
			model.setDataVector(Model.retrieveDATA(), Model.TABLE_HEADER);
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		//Switch to the View Balance tab
		jtp.setSelectedIndex(2);
	}
		
	private int getTypeID(String StringType){
		if(StringType.equals("income")){
			return 1;
		}
		else if(StringType.equals("expense")){
			return 2;
		}
		else if(StringType.equals("loan")){
			return 3;
		}
		else return -1;
	}
	
	private int catExists (String newcat)throws SQLException, ClassNotFoundException{
		String url ="jdbc:mysql://localhost:20002/mymoney?useSSL=false";
		Connection con = DriverManager.getConnection(url, "root", "password");
		System.out.println("Connection established Successfully");
		Statement statement=con.createStatement();
		
		String sqlQuery = "SELECT transaction_cat_id FROM transaction_category WHERE transaction_cat = \""+ newcat + "\"";
		ResultSet rs= statement.executeQuery(sqlQuery);
		rs.next();
		
		if(!rs.next()){
			int newId= getLastIdTransCat()+1;
			String sqlQuery2 = "INSERT INTO transaction_category VALUES("+newId+",\""+newcat+"\")";
			statement.executeUpdate(sqlQuery2);
			con.close();
			return newId;
		}
		else{
			int val=rs.getInt(1);
			con.close();
			return val;
		}
	}

	private void insertInDb(TransactionModel tm) throws SQLException, ClassNotFoundException{
		
		String url ="jdbc:mysql://localhost:20002/mymoney?useSSL=false";
		Connection con = DriverManager.getConnection(url, "root", "password");
		System.out.println("Connection established Successfully");
		PreparedStatement ps = con.prepareStatement("INSERT INTO transaction VALUES"
				+ "(?,?,?,?,?)");
		ps.setInt(1, tm.getTransactionId());
		ps.setDouble(2,tm.getAmount());
		ps.setDate(3, tm.getTransactionDate());
		ps.setInt(4, tm.getTransactionTypeId());
		ps.setInt(5, tm.getTransactionCategoryId());
		ps.execute();
		
		con.close();
	}
	
public int getLastIdTrans() throws SQLException, ClassNotFoundException{
		
		String url ="jdbc:mysql://localhost:20002/mymoney?useSSL=false";
		Connection con = DriverManager.getConnection(url, "root", "password");
		System.out.println("Connection established Successfully");
		Statement statement=con.createStatement();
		
		String sqlQuery = "SELECT MAX(TRANSACTION_ID) FROM transaction";
		ResultSet rs= statement.executeQuery(sqlQuery);
		rs.next();
		int val=rs.getInt(1);
		con.close();
		return val;

	}
	public int getLastIdTransCat() throws SQLException, ClassNotFoundException{
		
		String url ="jdbc:mysql://localhost:20002/mymoney?useSSL=false";
		Connection con = DriverManager.getConnection(url, "root", "password");
		System.out.println("Connection established Successfully");
		Statement statement=con.createStatement();
		
		String sqlQuery = "SELECT MAX(transaction_cat_id) FROM transaction_category";
		ResultSet rs= statement.executeQuery(sqlQuery);
		rs.next();
		int val=rs.getInt(1);
		con.close();
		return val;

	}
}
