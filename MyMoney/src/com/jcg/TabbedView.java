package com.jcg;

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

	public TabbedView() {
        
        setTitle("My Money application");
        jtp = new JTabbedPane();
        getContentPane().add(jtp);
        
        JPanel jp1 = createTab1();
       
        JPanel jp2 = createTab2();
        
        JPanel jp3 = new JPanel();
        JLabel label3 = new JLabel();
        label3.setText("You're current balance is: ");
        label3.setFont(new Font("Myriad Pro",Font.BOLD,28));
        jp3.add(label3);
        JLabel label4 = new JLabel();
        label4.setText("...");
        label4.setFont(new Font("Myriad Pro",Font.BOLD,28));
        jp3.add(label4);
        BalanceController balanceCtrl = new BalanceController(label4);
        try {
			balanceCtrl.displayBalance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        JPanel jp4 = new JPanel();
        
        jtp.addTab("View Transactions", jp1);
        jtp.addTab("Add Transaction", jp2);
        jtp.addTab("View Balance", jp3);
        jtp.addTab("Selected Transaction", jp4);
	}

	private JPanel createTab1() {
				// Create swing UI components 
				JLabel catLabel = new JLabel("Category: ");
				JTextField catTextField = new JTextField(15);
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
			            String id = table.getValueAt(table.getSelectedRow(), 0).toString();
			            String amt = table.getValueAt(table.getSelectedRow(), 1).toString();
			            String date = table.getValueAt(table.getSelectedRow(), 2).toString();
			            String type = table.getValueAt(table.getSelectedRow(), 3).toString();
			            String cat = table.getValueAt(table.getSelectedRow(), 4).toString();
			            showTransaction(id, amt, date, type, cat);
			            jtp.setSelectedIndex(3);
			        }
			    });
				
				// Create filter controller
				FilterController controller = new FilterController(catTextField, model);
				filterButton.addActionListener(controller);

				// Set the view layout
				JPanel ctrlPane = new JPanel(new GridLayout(2,2));
				ctrlPane.add(catLabel);
				ctrlPane.add(catTextField);
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
		AddTransacController cont= new AddTransacController(model, jtp, amount,date,type,cat);
		addtrbutton.addActionListener(cont);
		
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
	
	//adds content to tab 4
	private void showTransaction(String id, String amt, String date, String type, String cat) {
		 
		 JLabel label1 = new JLabel("Transaction ID: ");
		 JLabel label1value = new JLabel(id);
		 JLabel label2 = new JLabel("Amount: ");
		 JLabel label2value = new JLabel(amt);
	     JLabel label3 = new JLabel("Date: ");
		 JLabel label3value = new JLabel(date);
		 JLabel label4 = new JLabel("Type: ");
		 JLabel label4value = new JLabel(type);
	     JLabel label5 = new JLabel("Category: "); 
		 JLabel label5value = new JLabel(cat);
		 
		 JButton del = new JButton("Delete Transaction");
	     
	     JPanel jpan = (JPanel) jtp.getComponentAt(3);
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

	}
}
