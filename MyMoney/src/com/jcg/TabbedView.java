package com.jcg;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class TabbedView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Model model;
	private JTable table;

	public TabbedView() {
        
        setTitle("My Money application");
        JTabbedPane jtp = new JTabbedPane();
        getContentPane().add(jtp);
        
        JPanel jp1 = createTab1();
       
        JPanel jp2 = createTab2();
        
        JPanel jp3 = new JPanel();
        JLabel label3 = new JLabel();
        label3.setText("You are in area of Tab3");
        jp3.add(label3);
        
        jtp.addTab("View Transactions", jp1);
        jtp.addTab("Add Transaction", jp2);
        jtp.addTab("Tab3", jp3);
	}

	private JPanel createTab1() {
				// Create swing UI components 
				JTextField searchTermTextField = new JTextField(26);
				JButton filterButton = new JButton("Filter by category");
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

				// Create controller
				Controller controller = new Controller(searchTermTextField, model);
				filterButton.addActionListener(controller);

				// Set the view layout
				JPanel ctrlPane = new JPanel();
				ctrlPane.add(searchTermTextField);
				ctrlPane.add(filterButton);

				JScrollPane tableScrollPane = new JScrollPane(table);
				tableScrollPane.setPreferredSize(new Dimension(700, 182));
				tableScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Market Movers",
						TitledBorder.CENTER, TitledBorder.TOP));

				JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, ctrlPane, tableScrollPane);
				splitPane.setDividerLocation(35);
				splitPane.setEnabled(false);

				// Display it all in a scrolling window and make the window appear
				JPanel containerPanel = new JPanel();
				containerPanel.add(splitPane);
				containerPanel.setVisible(true);
		return containerPanel;
	}
	
	private JPanel createTab2() {
		
		 JLabel label1 = new JLabel();
	     label1.setText("Amount");
	     JLabel label2 = new JLabel();
	     label2.setText("Date");
	     JLabel label3 = new JLabel();
	     label3.setText("Type");
	     JLabel label4 = new JLabel();
	     label4.setText("Category");    
	 
	        
		JTextField amount = new JTextField(10);
		JTextField date = new JTextField(10);
		JTextField type = new JTextField(10);
		JTextField cat = new JTextField(10);
		
		JButton addtrbutton = new JButton("Add Transaction");
		AddTransacController cont= new AddTransacController(model, amount,date,type,cat);
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
}
