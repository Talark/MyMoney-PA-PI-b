package com.jcg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TabbedView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Model model;
	private JTable table;
	private JTabbedPane jtp;
	private JLabel balLabel;

	public TabbedView() {
        
        setTitle("My Money application");
        jtp = new JTabbedPane();
        getContentPane().add(jtp);
        
        JPanel jp1 = createTab1();
        
        JPanel jp3 = createTab3();
        
        JPanel jp2 = createTab2();
        
        JPanel jp4 = createTab4();
        
        jtp.addTab("View Transactions", jp1);
        jtp.addTab("Add Transaction", jp2);
        jtp.addTab("View Balance", jp3);
        jtp.addTab("Selected Transaction", jp4);
	}

	private JPanel createTab1() {
				// Create swing UI components 
				JLabel typeLabel = new JLabel("Type");
				JTextField typeTextField = new JTextField(15);
				JLabel catLabel = new JLabel("Category");
				JTextField catTextField = new JTextField(15);
				JLabel fromDateLabel = new JLabel("From date");
				JTextField fromDateTextField = new JTextField(15);
				JLabel toDateLabel = new JLabel("To date");
				JTextField toDateTextField = new JTextField(15);
				JButton filterButton = new JButton("Filter");
				table = new JTable();
				
				// Create table model
				try {
					model = new Model();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e){
					e.printStackTrace();
				}
				table.setModel(model);

				// Make it possible to select a transaction from the table
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			        public void valueChanged(ListSelectionEvent event) {
			            if(table.getSelectedRow()>=0){
			            	String id = table.getValueAt(table.getSelectedRow(), 0).toString();
			            	String amt = table.getValueAt(table.getSelectedRow(), 1).toString();
			            	String date = table.getValueAt(table.getSelectedRow(), 2).toString();
			            	String type = table.getValueAt(table.getSelectedRow(), 3).toString();
			            	String cat = table.getValueAt(table.getSelectedRow(), 4).toString();
			            	showTransaction(id, amt, date, type, cat);
			            	jtp.setSelectedIndex(3);
			            }
			        }
			    });
				
				// Create filter controller
				FilterController controller = new FilterController(typeTextField, catTextField, fromDateTextField, toDateTextField, model);
				filterButton.addActionListener(controller);

				// Set the view layout
				JPanel ctrlPane = new JPanel(new GridLayout(5,2));
				ctrlPane.add(typeLabel);
				ctrlPane.add(typeTextField);
				ctrlPane.add(catLabel);
				ctrlPane.add(catTextField);
				ctrlPane.add(fromDateLabel);
				ctrlPane.add(fromDateTextField);
				ctrlPane.add(toDateLabel);
				ctrlPane.add(toDateTextField);
				ctrlPane.add(filterButton);

				JScrollPane tableScrollPane = new JScrollPane(table);
				tableScrollPane.setPreferredSize(new Dimension(700, 182));
				tableScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Market Movers",
						TitledBorder.CENTER, TitledBorder.TOP));

				JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, ctrlPane, tableScrollPane);
				splitPane.setEnabled(false);

				// Display it all in a scrolling window and make the window appear
				JPanel containerPanel = new JPanel();
				containerPanel.add(splitPane);
				containerPanel.setVisible(true);
		return containerPanel;
	}

	private JPanel createTab2() {
		
		 JLabel label1 = new JLabel("Amount");
	     JLabel label2 = new JLabel("Date");
	     JLabel label3 = new JLabel("Type");
	     JLabel label4 = new JLabel("Category");
	 
	        
		JTextField amount = new JTextField(10);
		JTextField date = new JTextField(10);
		JTextField type = new JTextField(10);
		JTextField cat = new JTextField(10);
		
		JButton addtrbutton = new JButton("Add Transaction");
		TransacController cont= new TransacController(model, jtp, amount,date,type,cat, balLabel);
		addtrbutton.addActionListener(cont);
		addtrbutton.setActionCommand("addBtn");

		
		JPanel containerPanel = new JPanel(new GridLayout(5,2));
		containerPanel.add(label1);
		containerPanel.add(amount);
		containerPanel.add(label2);
		containerPanel.add(date);
		containerPanel.add(label3);
		containerPanel.add(type);
		containerPanel.add(label4);
		containerPanel.add(cat);		
		containerPanel.add(addtrbutton);
		containerPanel.setVisible(true);
		return containerPanel;
	}
	
	private JPanel createTab3() {
		JPanel containerPanel = new JPanel();
		
		JLabel label = new JLabel("Your current balance is: ");
        label.setFont(new Font("Myriad Pro",Font.BOLD,28));
        containerPanel.add(label);
        this.balLabel = new JLabel();
        balLabel.setFont(new Font("Myriad Pro",Font.BOLD,28));
        containerPanel.add(balLabel);
        AccountModel accModel = new AccountModel();
        try {
			accModel.updateBalance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        balLabel.setText(Math.abs(accModel.getBalance()) + " $");
        if(accModel.getBalance() < 0)
        	balLabel.setForeground(Color.RED);	
			else
				balLabel.setForeground(Color.GREEN);

		containerPanel.setVisible(true);
		return containerPanel;
	}
	
	private JPanel createTab4() {
		 JLabel label1 = new JLabel("Transaction ID: ");
		 JLabel label1value = new JLabel();
		 JLabel label2 = new JLabel("Amount: ");
		 JLabel label2value = new JLabel();
	     JLabel label3 = new JLabel("Date: ");
		 JLabel label3value = new JLabel();
		 JLabel label4 = new JLabel("Type: ");
		 JLabel label4value = new JLabel();
	     JLabel label5 = new JLabel("Category: "); 
		 JLabel label5value = new JLabel();
		 
		 JButton del = new JButton("Delete Transaction");
		 TransacController control= new TransacController(model, jtp, balLabel);
		 del.addActionListener(control);
		 del.setActionCommand("deleteBtn");
	     
	     JPanel jpan = new JPanel();
	     jpan.setLayout(new GridLayout(6,2));
	     jpan.add(label1);
	     jpan.add(label1value);
	     jpan.add(label2);
	     jpan.add(label2value);
	     jpan.add(label3);
	     jpan.add(label3value);
	     jpan.add(label4);
	     jpan.add(label4value);
	     jpan.add(label5);
	     jpan.add(label5value);
	     jpan.add(del);

		return jpan;
	}
	
	//adds content to tab 4
	private void showTransaction(String id, String amt, String date, String type, String cat) {
		 JPanel jp = (JPanel) jtp.getComponentAt(3);
		 JLabel label1value = (JLabel) jp.getComponent(1);
		 label1value.setText(id);
		 JLabel label2value = (JLabel) jp.getComponent(3);
		 label2value.setText(amt);
		 JLabel label3value = (JLabel) jp.getComponent(5);
		 label3value.setText(date);
		 JLabel label4value = (JLabel) jp.getComponent(7);
		 label4value.setText(type);
		 JLabel label5value = (JLabel) jp.getComponent(9);
		 label5value.setText(cat);		 
	}
}
