package com.jcg;

import java.awt.event.ActionEvent;	
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Controller implements ActionListener {
	
	private JTextField searchTermTextField = new JTextField(26);
	private DefaultTableModel model;

	public Controller(JTextField searchTermTextField, DefaultTableModel model) {
		super();
		this.searchTermTextField = searchTermTextField;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String searchTerm = searchTermTextField.getText();
		if (searchTerm != null && !"".equals(searchTerm)) {
			Object[][] newData = new Object[Model.DATArows][];
			int idx = 0;
			for (Object[] o: Model.DATA) {
				if ("*".equals(searchTerm.trim())) {
					newData[idx++] = o;
				} else {											
					if((String.valueOf(o[4]).toUpperCase()).startsWith(searchTerm.toUpperCase().trim())){
						newData[idx++] = o;
					}	
				}	
			}
			model.setDataVector(newData, Model.TABLE_HEADER);
		} else {
			model.setDataVector(Model.DATA, Model.TABLE_HEADER);
		}
	}

}
