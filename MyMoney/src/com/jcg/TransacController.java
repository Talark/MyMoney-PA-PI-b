package com.jcg;

import java.awt.Color;	
import java.awt.event.ActionEvent;	
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import java.sql.Connection;		
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//This is a Controller class that takes care of adding and deleting transactions and updating the model accordingly.
public class TransacController implements ActionListener {
	
	private DefaultTableModel model;
	private JTabbedPane jtp;
	private JTextField amount;
	private JXDatePicker date;
	private JComboBox type;
	private JTextField cat;
	private JLabel balanceLabel;
	
	public TransacController(DefaultTableModel model, JTabbedPane jtp, JTextField amount, JXDatePicker date,
			JComboBox type, JTextField cat, JLabel balanceLabel) {
		super();	
		this.model=model;
		this.jtp = jtp;
		this.amount=amount;
		this.date=date;
		this.type=type;
		this.cat=cat;
		this.balanceLabel = balanceLabel;
	}

	public TransacController(Model model, JTabbedPane jtp, JLabel balLabel) {
		super();	
		this.model=model;
		this.jtp = jtp;	
		this.balanceLabel = balLabel;
	}

	//This method is called when the "Add Transaction" or "Delete Transaction" button is clicked. It makes the necessary changes in 
	//the database, updates the balance and the model
	public void actionPerformed(ActionEvent e){
		
		if(e.getActionCommand().equals("deleteBtn")){   //we are deleting a transaction
			JPanel tab4 = (JPanel) jtp.getComponentAt(3);
		 	JLabel transacIdLabel = (JLabel) tab4.getComponent(1);
		 	String transacId = transacIdLabel.getText();
		 	try {
				deleteFromDb(transacId);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
		else { //e.getActionCommand().equals("addBtn") meaning we are adding a transaction
			
			//prepare transaction ID
			int transacID = -1;
			try {
				transacID = getLastIdTrans() + 1;
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			
			//prepare amount
			double addAmount = Double.parseDouble(amount.getText());
			
			//prepare date
			java.sql.Date myDate = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date tempDate = date.getDate();
			myDate = new java.sql.Date(tempDate.getTime());
				
			//prepare type ID
			String typ = (String)type.getSelectedItem();
			int typeId = getTypeID(typ);
			if (typeId == 2)
				addAmount = -addAmount;
			
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
		}
		
		//update the Model so that ViewTransactions is updated
		try {
			model.setDataVector(Model.retrieveDATA(), Model.TABLE_HEADER);
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		//update the balance
		AccountModel accModel = new AccountModel();
		try {
			accModel.updateBalance();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		balanceLabel.setText(Math.abs(accModel.getBalance()) + " $");
        if(accModel.getBalance() < 0)
        	balanceLabel.setForeground(Color.RED);	
		else
			balanceLabel.setForeground(Color.GREEN);
				
		
		//Switch to the View Balance tab
		jtp.setSelectedIndex(2);
	}
		
	private void deleteFromDb(String transacId) throws SQLException {
		String url ="jdbc:mysql://localhost:20002/mymoney?useSSL=false";
		Connection con = DriverManager.getConnection(url, "root", "password");
		System.out.println("Connection established Successfully");
		Statement statement=con.createStatement();
		String sqlQuery = "DELETE FROM transaction WHERE transaction_id = '"+ transacId + "'";
		statement.executeUpdate(sqlQuery);
		con.close();
	}

	private int getTypeID(String StringType){
		if(StringType.equalsIgnoreCase("income")){
			return 1;
		}
		else if(StringType.equalsIgnoreCase("expense")){
			return 2;
		}
		else if(StringType.equalsIgnoreCase("loan")){
			return 3;
		}
		else return -1;
	}
	
	//checks if the category exists in the DB; and if not creates one
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
